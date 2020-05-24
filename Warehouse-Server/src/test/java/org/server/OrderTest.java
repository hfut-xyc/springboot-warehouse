package org.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.server.entity.Order;
import org.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
class OrderTest {

	@Resource
	private OrderService orderService;

	@Test
	void Test() {
		Order order = new Order();
		order.setEid(10000);
		order.setWid(1);
		order.setPid(20000);
		order.setStatus("正常");
		order.setAmount(-400);
		orderService.addOrderWithOld(order);
	}

}
