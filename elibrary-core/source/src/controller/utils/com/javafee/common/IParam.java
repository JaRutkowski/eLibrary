package com.javafee.common;

import java.util.Collection;
import java.util.Map;

public interface IParam {
	void add(String key, Object value);

	Object get(String key);

	void remove(String key);

	boolean contains(String key);

	Collection<String> keySet();

	Map<String, Object> getMap();
}
