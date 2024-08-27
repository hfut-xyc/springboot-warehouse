package com.admin.controller;

import com.admin.service.OrderService;
import com.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/list")
    public Result listOrder(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String date)
    {
        Map<String, Object> map = orderService.listOrder((page - 1) * pageSize, pageSize, date);
        return Result.ok("查询成功", map);
    }

    //@PutMapping("/add/old")
    //public int addOrderWithOld(@RequestBody Order order) throws Exception {
    //    return orderService.addOrderWithOld(order);
    //}
    //
    //@PutMapping("/add/new")
    //public int addOrderWithNew(@RequestBody Order order) throws Exception {
    //    return orderService.addOrderWithNew(order);
    //}

    @DeleteMapping("")
    public int deleteUserById(@RequestParam Integer id) throws Exception {
        return orderService.deleteOrderById(id);

    }
}
