package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.entity.Order;
import org.server.exception.InsertException;
import org.server.exception.OutOfStockException;
import org.server.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "OrderController", description = "订单管理")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation("处理旧产品的订单")
	@PostMapping("/order/old/add")
	public int addOrderWithOld(@RequestBody Order order) {
		try {
			return orderService.addOrderWithOld(order);
		} catch (OutOfStockException | InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@ApiOperation("处理新产品的订单")
	@PostMapping("/order/new/add")
	public int addOrderWithNew(@RequestBody Order order) {
		try {
			return orderService.addOrderWithNew(order);
		} catch (InsertException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

}
