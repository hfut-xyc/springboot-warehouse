package org.server.config;

import org.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;

    @Resource
    private CaptchaFilter captchaFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/captcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler((request, response, auth) -> {
                    response.setContentType("application/json;charset=utf8");
                    PrintWriter out = response.getWriter();
                    out.write("success");
                    out.flush();
                    out.close();
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf8");
                    PrintWriter out = response.getWriter();
                    out.write("fail");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, auth) -> {
                    response.setContentType("application/json;charset=utf8");
                    PrintWriter out = response.getWriter();
                    out.write("success");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    out.write("Unauthorized, please login");
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable()
                // 设置会话最大并发量为1，重复登录会踢掉前面的用户
                .sessionManagement().maximumSessions(1);
    }
}
