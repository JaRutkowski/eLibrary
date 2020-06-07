package com.javafee.elibrary.core.common.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class SingletonFactory {
	public static Map<String, Object> objectFactory = new HashMap<>();

	public static <T> T getInstance(Class<T> c) {
		String key = c.toString();
		Object instance = objectFactory.get(key);
		if (instance == null) {
			synchronized (c) {
				try {
					instance = c.getDeclaredConstructor().newInstance();
					objectFactory.put(key, instance);
				} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
					throw new RuntimeException("Exception while creating singleton instance for class : " + key + " - Exception Message : " + e);
				}
			}
		}
		return c.cast(instance);
	}
}
