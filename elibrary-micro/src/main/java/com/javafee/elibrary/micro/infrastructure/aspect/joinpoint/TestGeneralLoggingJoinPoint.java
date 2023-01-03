package com.javafee.elibrary.micro.infrastructure.aspect.joinpoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestGeneralLoggingJoinPoint {
	@Pointcut("execution(* com.jfs.operations.controller.api.impl..*(..))")
	public void execAll() {
	}

	@Pointcut("execution(* com.jfs.operations.controller.api.impl.get*(..)) || " +
			"execution(* com.jfs.operations.controller.api.impl.set*(..))")
	private void execExclude() {
	}

	@Pointcut("execAll() && !execExclude()")
	public void execPointcut() {
	}
}
