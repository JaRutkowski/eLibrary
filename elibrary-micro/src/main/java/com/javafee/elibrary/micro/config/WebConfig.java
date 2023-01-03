package com.javafee.elibrary.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.javafee.elibrary.micro.infrastructure.apiversion.handler.ApiVersionRequestMappingHandlerMapping;

//https://stackoverflow.com/questions/36744678/spring-boot-swagger-2-ui-custom-requestmappinghandlermapping-mapping-issue
//https://github.com/spring-projects/spring-boot/issues/5004
@Configuration
public class WebConfig extends DelegatingWebMvcConfiguration {
	@Value("${app.api.prefix}")
	private String apiPrefix;

	@Override
	public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		return new ApiVersionRequestMappingHandlerMapping(apiPrefix);
	}
}