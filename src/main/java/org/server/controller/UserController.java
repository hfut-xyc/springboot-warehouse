package org.server.controller;

import org.server.dto.Result;
import org.server.entity.User;
import org.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/user/list")
    public Result<Map<String, Object>> findUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<User> list = userService.page(keyword);
        int pageSize = 10;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("userList", list.subList(start, end));
        return Result.success("查询成功", map);
    }

    @PostMapping("/user")
    public Integer save(@RequestBody User user) throws Exception {
        return userService.save(user);
    }

    @PutMapping("/user")
    public Integer update(User user) throws Exception {
        return userService.update(user);
    }

    @DeleteMapping("/user/{id}")
    public int deleteById(@PathVariable int id) throws Exception {
        return userService.deleteById(id);
    }
}
