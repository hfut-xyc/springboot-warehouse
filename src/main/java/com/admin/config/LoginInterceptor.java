package com.admin.config;

import com.admin.entity.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new Exception("未登录");
        }
        Claims claims = JwtUtils.parseToken(token);

        // 只有超级管理员有权限增删改用户、仓库
        Integer role = (Integer) claims.get("role");
        String path = request.getServletPath();
        if ("/user".equals(path) || "/warehouse".equals(path)) {
            if (role == User.ROLE_USER) {
                throw new Exception("权限不足，请联系管理员");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
