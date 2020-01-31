package com.javafee.elibrary.core.process;

import java.lang.reflect.InvocationTargetException;

public class ProcessFactory {
	public static Process create(Class c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		return (Process) c.getDeclaredConstructor().newInstance();
	}
}
