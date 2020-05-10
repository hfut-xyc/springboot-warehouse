package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTests {

	@Resource
	private UserMapper userMapper;

	@Test
	void getUserList() {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
//		List<User> list1 = userMapper.getUserList(null);
		stopwatch.stop();
		System.out.println(stopwatch.getLastTaskTimeMillis());
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
