package org.server.controller;

import org.server.pojo.dto.Result;
import org.server.pojo.entity.User;
import org.server.service.UserService;
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
    public Result findUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<User> list = userService.list(keyword);
        int pageSize = 10;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("userList", list.subList(start, end));
        return Result.ok(map, "查询成功");
    }

    @PostMapping("")
    public Integer save(@RequestBody User user) throws Exception {
        return userService.save(user);
    }

    @PutMapping("")
    public Integer update(User user) throws Exception {
        return userService.update(user);
    }

    @DeleteMapping("")
    public int deleteById(@RequestParam Integer id) throws Exception {
        return userService.deleteById(id);
    }
}
