package org.server.service;

import org.server.entity.Role;
import org.server.entity.User;
import org.server.exception.RegisterException;
import org.server.exception.UserInsertException;
import org.server.exception.UserRepeatException;
import org.server.mapper.RoleMapper;
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
	private RoleMapper roleMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userMapper.getUserByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		List<Role> roles = roleMapper.getRolesByUid(user.getId());
		user.setRoles(roles);
		return user;
	}

	public User getUserById(int id) {
		return userMapper.getUserById(id);
	}

	public List<User> getUserList(int offset, int count, String keyword) {
		return userMapper.getUserList(offset, count, keyword);
	}

	public int getUserCount(String keyword) {
		return userMapper.getUserCount(keyword);
	}

	@Transactional
	public int addUser(User user) throws RegisterException {
		User temp = userMapper.getUserByUsername(user.getUsername());
		if (temp != null) {
			throw new UserRepeatException("用户名重复");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		int res1 = userMapper.addUser(user);

		// 表单传递的user默认id为0, 添加到tb_user之后才有一个新id
		user = userMapper.getUserByUsername(user.getUsername());
		int res2 = roleMapper.addUserRole(user.getId());
		if (res1 + res2 != 2) {
			throw new UserInsertException("添加用户失败");
		}
		return 1;
	}

	@Transactional
	public int updateUserEnabled(boolean enabled, int id) {
		return userMapper.updateUserEnabled(enabled, id);
	}

	@Transactional
	public int deleteUserById(int id) {
		return userMapper.deleteUserById(id);
	}
}
