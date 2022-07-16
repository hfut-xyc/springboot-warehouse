package org.server.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if ("POST".equals(request.getMethod()) && "/login".equals(request.getRequestURI())) {
            String verifyCode = request.getParameter("verifyCode");
            String trueCode = (String) request.getSession().getAttribute("code");
            try {
                if (StringUtils.isEmpty(verifyCode)) {
                    throw new AuthenticationServiceException("验证码不能为空");
                }
                if (!verifyCode.equalsIgnoreCase(trueCode)) {
                    throw new AuthenticationServiceException("验证码错误");
                }
            } catch (AuthenticationServiceException e) {
                logger.error(e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
