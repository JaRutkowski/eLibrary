package com.javafee.elibrary.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = "com.javafee.elibrary.micro")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MicroApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(MicroApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MicroApplication.class);
	}
}
