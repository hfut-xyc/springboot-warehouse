package org.server.service;

import org.server.entity.Order;
import org.server.entity.Product;
import org.server.exception.InsertException;
import org.server.exception.NotFoundException;
import org.server.exception.OutOfStockException;
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

	public List<Order> getOrderList(int keyword) {
		return orderMapper.getOrderList(keyword);
	}

	@Transactional
	public int addOrderWithOld(Order order) throws NotFoundException, OutOfStockException, InsertException {
		// 0.查询剩余库存
		Integer stock = warehouseMapper.getProductStockByWid(order.getWid(), order.getPid());
		if (stock == null) {
			throw new NotFoundException("该仓库没有对应库存记录");
		}
		if (stock + order.getAmount() < 0) {
			throw new OutOfStockException("库存不足");
		}
		// 1.更新产品库存 2.添加订单记录
		int res1 = warehouseMapper.updateProductByWid(order.getWid(), order.getPid(), order.getAmount());
		int res2 = orderMapper.addOrder(order);
		if (res1 != 1 || res2 != 1) {
			throw new InsertException("添加订单失败");
		}
		return 1;
	}

	@Transactional
	public int addOrderWithNew(Order order) throws InsertException {
		// 新产品订单只能是入库，不需要查库存
		int res1 = warehouseMapper.addProductByWid(order.getWid(), order.getPid(), order.getAmount());
		int res2 = orderMapper.addOrder(order);
		if (res1 != 1 || res2 != 1) {
			throw new InsertException("添加订单失败");
		}
		return 1;
	}
}
