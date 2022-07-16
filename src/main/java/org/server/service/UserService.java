package org.server.service;

import org.server.entity.Role;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userMapper.findByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		Role role = userMapper.findRole(user.getId());
		user.setRole(role);
		return user;
	}

	public List<User> getUserList(String keyword) {
		return userMapper.findUsers(keyword);
	}

	@Transactional
	public int addUser(User user) throws Exception {
		User temp = userMapper.findByUsername(user.getUsername());
		if (temp != null) {
			throw new Exception("username has existed");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		int res1 = userMapper.save(user);
		// 表单传递的user还未分配, 添加到tb_user之后才有一个新id
		user = userMapper.findByUsername(user.getUsername());
		int res2 = userMapper.addRoleById(user.getId(), 2);
		if (res1 != 1 || res2 != 1) {
			throw new Exception("添加用户失败");
		}
		return 1;
	}

	@Transactional
	public int updateEnabledById(boolean enabled, int id) throws Exception {
		int res = userMapper.updateEnabledById(enabled, id);
		if (res != 1) {
			throw new Exception("用户" + id + "状态修改失败");
		}
		return res;
	}

	@Transactional
	public int updateRoleByUid(boolean isAdmin, int id) throws Exception {
		int res = isAdmin ? userMapper.addRoleById(id, 1) : userMapper.deleteRoleById(id);
		if (res != 1) {
			throw new Exception("修改用户" + id + "角色失败");
		}
		return res;
	}

	@Transactional
	public int deleteUserById(int id) throws Exception {
		int res1 = userMapper.deleteUserById(id);
		int res2 = userMapper.deleteRoleById(id);
		if (res1 != 1 || res2 < 1) {
			throw new Exception("用户删除失败");
		}
		return 1;
	}
}
