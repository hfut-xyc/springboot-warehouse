package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.server.service.EmployeeService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTest {

	@Resource
	private UserMapper userMapper;

	@Resource
	private EmployeeService employeeService;

	@Test
	void getUserList() {
		List<User> list = userMapper.findUsers(null);
	}

	@Test
	void addUser() {
		User user = new User();
		user.setUsername("xyc");
		user.setPassword("xyc");
		System.out.println(userMapper.save(user));
	}

	@Test
	void deleteUser() {
		System.out.println(userMapper.deleteUserById(1000));
	}


}
