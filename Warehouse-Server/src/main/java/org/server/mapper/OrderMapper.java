package org.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.server.entity.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface OrderMapper {

	// 根据订单编号模糊查询订单
	List<Order> getOrderList(int keyword);

	int addOrder(Order order);

	int deleteOrderById(int id);
}
