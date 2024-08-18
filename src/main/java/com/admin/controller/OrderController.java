package com.admin.controller;

import com.admin.vo.Result;
import com.admin.entity.Order;
import com.admin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
            @RequestParam(value = "endTime", required = false) Long endTimeStamp)
    {
        String startTime = (startTimeStamp == null) ? null : fmt.format(new Date(startTimeStamp));
        String endTime = (endTimeStamp == null) ? null : fmt.format(new Date(endTimeStamp));
        List<Order> list = orderService.getOrderList(startTime, endTime);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        Map<String, Object> map = new HashMap<>();
        map.put("orderList", list.subList(start, end));
        map.put("total", list.size());
        return Result.ok("查询成功", map);
    }

    @PostMapping("/add/old")
    public int addOrderWithOld(@RequestBody Order order) throws Exception {
        return orderService.addOrderWithOld(order);
    }

    @PostMapping("/add/new")
    public int addOrderWithNew(@RequestBody Order order) throws Exception {
        return orderService.addOrderWithNew(order);
    }

    @DeleteMapping("")
    public int deleteUserById(@RequestParam Integer id) throws Exception {
        return orderService.deleteOrderById(id);

    }
}
