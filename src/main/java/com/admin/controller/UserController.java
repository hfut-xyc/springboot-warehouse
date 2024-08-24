package com.admin.controller;

import com.admin.entity.User;
import com.admin.service.UserService;
import com.admin.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result listUser(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(required = false) String keyword)
    {
        Map<String, Object> map = userService.listUser((page - 1) * pageSize, pageSize, keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Integer insertUser(@RequestBody User user) throws Exception {
        return userService.insertUser(user);
    }

    @PutMapping("")
    public Integer updateUser(@RequestBody User user) throws Exception {
        return userService.updateUser(user);
    }

    @DeleteMapping("")
    public Integer deleteById(@RequestParam Integer id) throws Exception {
        return userService.deleteById(id);
    }
}
