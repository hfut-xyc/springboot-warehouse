package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.User;
import org.server.mapper.UserMapper;
import org.server.service.EmployeeService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Arrays;
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
		List<User> list = userMapper.getUserList(null);
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
		List<Integer> list = Arrays.asList(1, 2, 3);
		System.out.println(employeeService.updateWarehouseByEid(10000, list));
	}

}
