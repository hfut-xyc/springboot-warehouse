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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "OrderController", description = "订单管理")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("按时间范围分页查询订单列表")
    @GetMapping("/orders")
    public Map<String, Object> getOrderList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "startTime", required = false) Long startTimeStamp,
            @RequestParam(value = "endTime", required = false) Long endTimeStamp)
    {
    	Date startTime = (startTimeStamp == null) ? null : new Date(startTimeStamp);
	    Date endTime = (endTimeStamp == null) ? null : new Date(endTimeStamp);
        List<Order> list = orderService.getOrderList(startTime, endTime);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("orderList", list.subList(start, end));
        map.put("total", list.size());
        return map;
    }

    @ApiOperation("创建旧产品的订单")
    @PostMapping("/order/add/old")
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

    @ApiOperation("创建新产品的订单")
    @PostMapping("/order/add/new")
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
