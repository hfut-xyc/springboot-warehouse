package org.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080", "http://127.0.0.1:8080")
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
