package com.javafee.elibrary.micro.infrastructure.apiversion.handler;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.javafee.elibrary.micro.infrastructure.apiversion.ApiVersion;

public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	private final String prefix;

	public ApiVersionRequestMappingHandlerMapping(String prefix) {
		this.prefix = prefix;
	}

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
		if (info == null) return null;

		ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
		if (methodAnnotation != null) {
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			// Concatenate our ApiVersion with the usual request mapping
			info = createApiVersionInfo(methodAnnotation, methodCondition).combine(info);
		} else {
			ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				// Concatenate our ApiVersion with the usual request mapping
				info = createApiVersionInfo(typeAnnotation, typeCondition).combine(info);
			}
		}
		return info; //super.getMappingForMethod(method, handlerType);
	}

	private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition) {
		int[] values = annotation.value();
		String[] patterns = new String[values.length];
		for (int i = 0; i < values.length; i++)
			// Build the URL prefix
			patterns[i] = prefix + values[i];

		return new RequestMappingInfo(
				new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
				new RequestMethodsRequestCondition(),
				new ParamsRequestCondition(),
				new HeadersRequestCondition(),
				new ConsumesRequestCondition(),
				new ProducesRequestCondition(),
				customCondition);
	}

}