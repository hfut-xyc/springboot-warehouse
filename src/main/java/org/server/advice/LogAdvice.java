package org.server.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Aspect
@Component
public class LogAdvice {

    @Pointcut("execution(public * org.server.controller.*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("{} {} {}", ip, method, uri);
    }
}
