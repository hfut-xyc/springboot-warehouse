package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.Product;
import org.server.entity.User;
import org.server.mapper.ProductMapper;
import org.server.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProductTests {

	@Resource
	private ProductMapper productMapper;

	@Test
	void getUserList() {
		List<Product> list = productMapper.getProductList();
		System.out.println(list);
	}

	@Test
	void addUser() {
		User user = new User();
		user.setUsername("xyc");
		user.setPassword("xyc");
		user.setPhone("11111");

	}

	@Test
	void deleteUser() {
	}

	@Test
	void test() {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.encode("admin"));
	}

}
