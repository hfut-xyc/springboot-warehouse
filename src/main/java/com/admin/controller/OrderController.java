package com.admin.controller;

import com.admin.entity.Order;
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
        Map<String, Object> map = orderService.selectListByDate((page - 1) * pageSize, pageSize, date);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Result insertOrder(@RequestBody Order order) throws Exception {
        Integer res = orderService.insert(order);
        return Result.ok("添加成功", res);
    }


    @DeleteMapping("")
    public Result deleteById(@RequestParam Integer id) throws Exception {
        Integer res = orderService.deleteById(id);
        return Result.ok("删除成功", res);
    }
}
