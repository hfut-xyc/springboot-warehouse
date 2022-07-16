package org.server.controller;

import org.server.entity.User;
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
    public Map<String, Object> findUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<User> list = userService.getUserList(keyword);
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());

        Map<String, Object> map = new HashMap<>();
        map.put("userList", list.subList(start, end));
        map.put("total", list.size());
        return map;
    }

    @PostMapping("/add")
    public Integer addUser(@RequestBody User user) throws Exception {
        return userService.addUser(user);
    }

    @PostMapping("/{id}/enabled")
    public Integer updateUserEnabled(
            @PathVariable int id,
            @RequestParam("enabled") boolean enabled) throws Exception {
        return userService.updateEnabledById(enabled, id);
    }

    @PostMapping("/{id}/role")
    public int updateUserRole(
            @PathVariable int id,
            @RequestParam("isAdmin") boolean isAdmin) throws Exception {
        return userService.updateRoleByUid(isAdmin, id);
    }

    @DeleteMapping("/{id}/delete")
    public int deleteUserById(@PathVariable int id) throws Exception {
        return userService.deleteUserById(id);
    }
}
