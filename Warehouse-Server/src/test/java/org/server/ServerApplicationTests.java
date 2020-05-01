package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServerApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void getUserById() {
		System.out.println(userService.getUserById(1000));
	}

	@Test
	void getUserList() {
		System.out.println(userService.getUserList(1, 10, null));
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
