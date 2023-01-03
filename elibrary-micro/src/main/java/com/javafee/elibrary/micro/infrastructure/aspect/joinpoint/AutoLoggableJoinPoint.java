package com.javafee.elibrary.micro.infrastructure.aspect.joinpoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoLoggableJoinPoint {
	@Pointcut("@annotation(com.javafee.elibrary.micro.infrastructure.aspect.annotation.AutoLoggable)")
	private void execAll() {
	}

	@Pointcut("execAll()")
	public void execPointcut() {
	}
}
