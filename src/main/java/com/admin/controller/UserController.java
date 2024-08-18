package com.admin.controller;

import com.admin.vo.Result;
import com.admin.entity.User;
import com.admin.service.UserService;
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
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        List<User> list = userService.list(keyword);
        int pageSize = 10;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("userList", list.subList(start, end));
        return Result.ok("查询成功", map);
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
