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
        Map<String, Object> map = userService.selectListByName((page - 1) * pageSize, pageSize, keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Result insertUser(@RequestBody User user) throws Exception {
        Integer res = userService.insert(user);
        return Result.ok("添加成功", res);
    }

    @PutMapping("")
    public Result updateUser(@RequestBody User user) throws Exception {
        Integer res = userService.update(user);
        return Result.ok("修改成功", res);
    }

    @DeleteMapping("")
    public Result deleteUserById(@RequestParam Integer id) throws Exception {
        Integer res = userService.deleteById(id);
        return Result.ok("删除成功", res);
    }
}
