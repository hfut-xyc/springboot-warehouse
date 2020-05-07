package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTests {

	@Resource
	private UserMapper userMapper;

	@Test
	void getUserById() {
		System.out.println(userMapper.getUserById(1000));
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
		System.out.println(userMapper.addUser(user));
	}

	@Test
	void deleteUser() {
		System.out.println(userMapper.deleteUserById(1000));
	}

	@Test
	void test() {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.encode("admin"));
	}

}
