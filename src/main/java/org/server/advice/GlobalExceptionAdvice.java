package org.server.advice;


import lombok.extern.slf4j.Slf4j;
import org.server.dto.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(HttpServletRequest request, Exception e) {
        log.error("{}: {}", request.getRequestURI(), e.getMessage());
        return Result.error(e.getMessage());
    }
}