package org.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.server.pojo.dto.OrderChart;
import org.server.pojo.dto.Result;
import org.server.pojo.entity.Order;
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
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/list")
    public Result getOrderList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "startTime", required = false) Long startTimeStamp,
            @RequestParam(value = "endTime", required = false) Long endTimeStamp) {
        String startTime = (startTimeStamp == null) ? null : fmt.format(new Date(startTimeStamp));
        String endTime = (endTimeStamp == null) ? null : fmt.format(new Date(endTimeStamp));
        List<Order> list = orderService.getOrderList(startTime, endTime);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        Map<String, Object> map = new HashMap<>();
        map.put("orderList", list.subList(start, end));
        map.put("total", list.size());
        return Result.ok(map, "查询成功");
    }

    @GetMapping("/chart")
    public List<OrderChart> getOrderChart() {
        return orderService.getOrderChart();
    }

    @PostMapping("/add/old")
    public int addOrderWithOld(@RequestBody Order order) {
        try {
            return orderService.addOrderWithOld(order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @PostMapping("/add/new")
    public int addOrderWithNew(@RequestBody Order order) {
        try {
            return orderService.addOrderWithNew(order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    @DeleteMapping("")
    public int deleteUserById(@RequestParam Integer id) {
        try {
            return orderService.deleteOrderById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
