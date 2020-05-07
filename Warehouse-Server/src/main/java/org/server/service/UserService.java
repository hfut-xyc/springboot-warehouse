package org.server.service;

import org.server.entity.Role;
import org.server.entity.User;
import org.server.exception.DeleteException;
import org.server.exception.InsertException;
import org.server.exception.RepeatException;
import org.server.exception.UpdateException;
import org.server.mapper.UserRoleMapper;
import org.server.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userMapper.getUserByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		List<Role> roles = userRoleMapper.getRolesByUid(user.getId());
		user.setRoles(roles);
		return user;
	}

	public User getUserById(int id) {
		return userMapper.getUserById(id);
	}

	public List<User> getUserList(String keyword) {
		return userMapper.getUserList(keyword);
	}

	public int getUserCount(String keyword) {
		return userMapper.getUserCount(keyword);
	}

	@Transactional
	public int addUser(User user) throws RepeatException, InsertException {
		User temp = userMapper.getUserByUsername(user.getUsername());
		if (temp != null) {
			throw new RepeatException("用户名重复");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		int res1 = userMapper.addUser(user);

		// 表单传递的user默认id为0, 添加到tb_user之后才有一个新id
		user = userMapper.getUserByUsername(user.getUsername());
		int res2 = userRoleMapper.addUserRole(user.getId(), 2);
		if (res1 + res2 != 2) {
			throw new InsertException("添加用户失败");
		}
		return 1;
	}

	@Transactional
	public int updateUserEnabled(boolean enabled, int id) throws UpdateException {
		int res = userMapper.updateUserEnabled(enabled, id);
		if (res != 1) {
			throw new UpdateException("用户" + id + "状态修改失败");
		}
		return res;
	}

	@Transactional
	public int updateUserRole(boolean isAdmin, int id) throws UpdateException {
		int res;
		if (isAdmin) {
			res = userRoleMapper.addUserRole(id, 1);
			if (res != 1) {
				throw new UpdateException("用户" + id + "设置管理员权限失败");
			}
		} else {
			res = userRoleMapper.deleteUserRole(id, 1);
			if (res != 1) {
				throw new UpdateException("用户" + id + "取消管理员权限失败");
			}
		}
		return res;
	}

	@Transactional
	public int deleteUserById(int id) throws DeleteException {
		int res1 = userMapper.deleteUserById(id);
		int res2 = userRoleMapper.clearRolesByUid(id);
		if (res1 + res2 >= 2) {
			return 1;
		} else {
			throw new DeleteException("用户删除失败");
		}
	}
}
