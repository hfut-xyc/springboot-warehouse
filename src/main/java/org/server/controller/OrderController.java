package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.dto.OrderChart;
import org.server.entity.Order;
import org.server.service.OrderService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/orders")
    public Map<String, Object> getOrderList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "status", defaultValue = "正常") String status,
            @RequestParam(value = "startTime", required = false) Long startTimeStamp,
            @RequestParam(value = "endTime", required = false) Long endTimeStamp)
    {
        String startTime = (startTimeStamp == null) ? null : fmt.format(new Date(startTimeStamp));
        String endTime = (endTimeStamp == null) ? null : fmt.format(new Date(endTimeStamp));
        List<Order> list = orderService.getOrderList(status, startTime, endTime);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        Map<String, Object> map = new HashMap<>();
        map.put("orderList", list.subList(start, end));
        map.put("total", list.size());
        return map;
    }

    @GetMapping("/order/chart")
    public List<OrderChart> getOrderChart() {
        return orderService.getOrderChart();
    }

    @PostMapping("/order/add/old")
    public int addOrderWithOld(@RequestBody Order order) {
        try {
            return orderService.addOrderWithOld(order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @PostMapping("/order/add/new")
    public int addOrderWithNew(@RequestBody Order order) {
        try {
            return orderService.addOrderWithNew(order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @PostMapping("/order/{id}/update/status")
    public int updateOrderStatus(@PathVariable int id, @RequestParam(value = "status") String status) {
        try {
            return orderService.updateOrderStatus(id, status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @DeleteMapping("/order/{id}/delete")
    public int deleteUserById(@PathVariable int id) {
        try {
            return orderService.deleteOrderById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
