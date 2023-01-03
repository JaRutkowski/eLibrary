package com.javafee.elibrary.micro.infrastructure.aspect;

import java.text.MessageFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.javafee.elibrary.micro.config.SystemProperties;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AutoMeasuringTimeAspect {
	//TODO: idempotence
	//TODO: query technology - not engine dependent
	//TODO: trace-id
	//TODO: gRPC
	//TODO: GraphQL
	//TODO: flyway/liquibase
	//TODO: paging and sorting

	@Around("com.javafee.elibrary.micro.infrastructure.aspect.joinpoint.AutoMeasuringTimeJoinPoint.execPointcut()")
	public Object exec(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long begin = System.currentTimeMillis();
		Object result;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		}
		log.info((MessageFormat.format(SystemProperties.getResourceBundle().getString("aspects.logTemplate.autoMeasuringTimeAspect"),
				System.currentTimeMillis() - begin, proceedingJoinPoint.getSignature().getDeclaringTypeName(),
				proceedingJoinPoint.getSignature().getName())));
		return result;
	}
}
