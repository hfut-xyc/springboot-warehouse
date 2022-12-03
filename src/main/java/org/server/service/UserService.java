package org.server.service;

import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;


	public List<User> page(String keyword) {
		return userMapper.page(keyword);
	}

	@Transactional
	public Integer save(User user) throws Exception {
		User temp = userMapper.findByUsername(user.getUsername());
		if (temp != null) {
			throw new Exception("username has existed");
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
		int res1 = userMapper.deleteById(id);
		if (res1 != 1) {
			throw new Exception("用户删除失败");
		}
		return 1;
	}
}
