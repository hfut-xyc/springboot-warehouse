package com.admin.controller;

import com.admin.entity.User;
import com.admin.service.UserService;
import com.admin.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
        Integer total = userService.count(keyword);
        List<User> list = userService.listUser((page - 1) * pageSize, pageSize, keyword);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("userList", list);

        return Result.ok("查询成功", data);
    }

    @PostMapping("")
    public Integer insertUser(@RequestBody User user) throws Exception {
        return userService.insert(user);
    }

    @PutMapping("")
    public Integer updateUser(@RequestBody User user) throws Exception {
        return userService.update(user);
    }

    @DeleteMapping("")
    public Integer deleteById(@RequestParam Integer id) throws Exception {
        return userService.deleteById(id);
    }
}
