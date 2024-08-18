package com.admin.service;

import com.admin.entity.User;
import com.admin.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	public User login(User userDTO) {
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new RuntimeException("用户名和密码不能为空");
		}

		User user = userMapper.selectByUsername(username);
		if (user == null || !password.equals(user.getPassword())) {
			throw new RuntimeException("登录失败");
		}
		return user;
	}

	public List<User> list(String keyword) {
		return userMapper.list(keyword);
	}

	@Transactional
	public Integer insert(User user) throws Exception {
		User temp = userMapper.selectByUsername(user.getUsername());
		if (temp != null) {
			throw new Exception("用户名已存在");
		}
		int res = userMapper.insert(user);
		if (res != 1) {
			throw new Exception("用户添加失败");
		}
		return res;
	}

	@Transactional
	public Integer update(User user) throws Exception {
		int res = userMapper.update(user);
		if (res != 1) {
			throw new Exception("用户修改失败");
		}
		return res;
	}


	@Transactional
	public Integer deleteById(Integer id) throws Exception {
		int res = userMapper.deleteById(id);
		if (res != 1) {
			throw new Exception("用户删除失败");
		}
		return res;
	}
}
