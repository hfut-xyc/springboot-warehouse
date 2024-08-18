package com.admin.controller;

import com.admin.config.JwtUtils;
import com.admin.entity.User;
import com.admin.service.UserService;
import com.admin.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User loginDto) throws Exception {
        User user = userService.login(loginDto);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        String token = JwtUtils.createToken(claims);

        return Result.ok("登录成功", token);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) throws Exception {
        //request.getSession().removeAttribute("user");
        return Result.ok("注销成功");
    }
}
