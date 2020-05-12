package org.server.service;

import org.server.entity.Order;
import org.server.entity.Product;
import org.server.mapper.OrderMapper;
import org.server.mapper.ProductMapper;
import org.server.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private WarehouseMapper warehouseMapper;

	@Resource
	private ProductMapper productMapper;

	public List<Order> getOrderList(int keyword) {
		return orderMapper.getOrderList(keyword);
	}

	@Transactional
	public int addOrderWithOldProduct(Order order) {
		int res1 = orderMapper.addOrder(order);
		int res2 = warehouseMapper.updateWarehouseProduct(order.getWid(), order.getPid(), order.getAmount());
		return 1;
	}

//	@Transactional
//	public int addOrderWithNewProduct(Order order) {
//
//	}
}
