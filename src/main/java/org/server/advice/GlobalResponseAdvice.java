package org.server.advice;


import org.server.annotation.IgnoreAdvice;
import org.server.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        ResultVO<Object> result = new ResultVO<>(0, null);
        if (null == o) {
            return result;
        } else if (o instanceof ResultVO) {
            return o;
        } else {
            result.setData(o);
            return result;
        }
    }
}