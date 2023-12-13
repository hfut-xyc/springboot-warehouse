package org.server.service;

import org.server.pojo.entity.User;
import org.server.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	public List<User> list(String keyword) {
		return userMapper.list(keyword);
	}

	@Transactional
	public Integer save(User user) throws Exception {
		User temp = userMapper.findByUsername(user.getUsername());
		if (temp != null) {
			throw new Exception("用户名已存在");
		}
		int res = userMapper.save(user);
		if (res != 1 ) {
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
