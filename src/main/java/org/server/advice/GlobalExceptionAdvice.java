package org.server.advice;


import lombok.extern.slf4j.Slf4j;
import org.server.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResultVO<String> handleException(HttpServletRequest request, Exception e) {
        ResultVO<String> result = new ResultVO<>(-1, e.getMessage());
        log.error("{}: {}", request.getRequestURI(), e.getMessage());
        return result;
    }
}