package org.server.config;

import org.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private UserService userService;

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

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//				.antMatchers("/user/**", "role/**").hasRole("admin")
//				.anyRequest().authenticated()
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
				.cors().and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint((request, response, exception) -> {
					response.setContentType("application/json;charset=utf-8");
					response.setStatus(401);
					PrintWriter out = response.getWriter();
					out.write("Unauthorized, please login");
					out.flush();
					out.close();
				});
	}


}
