package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class CorsConfiguration implements WebMvcConfigurer {
	private Environment environment;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(environment.getProperty("app.cors.allowed.origin"))
				.allowedMethods("*");
	}
}
