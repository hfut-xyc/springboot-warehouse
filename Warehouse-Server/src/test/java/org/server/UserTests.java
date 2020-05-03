package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTests {

	@Autowired
	private UserService userService;

	@Resource
	private UserMapper userMapper;

	@Test
	void getUserById() {
		System.out.println(userService.getUserById(1000));
	}

	@Test
	void getUserList() {
		List<User> list = userMapper.getUserList(null);
		System.out.println(list.size());
	}

	@Test
	void addUser() {
		User user = new User();
		user.setUsername("xyc");
		user.setPassword("xyc");
		user.setPhone("11111");
		System.out.println(userService.addUser(user));
	}

	@Test
	void deleteUser() {
		System.out.println(userService.deleteUserById(1000));
	}

	@Test
	void test() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}

}
