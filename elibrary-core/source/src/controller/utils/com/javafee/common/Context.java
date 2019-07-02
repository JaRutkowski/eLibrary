package com.javafee.common;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Context implements IParam {
	private static Context comContext = null;
	private Map<String, Object> context = new HashMap<String, Object>();

	private Context() {
	}

	public static Context getInstance() {
		if (comContext == null) {
			comContext = new Context();
		}
		return comContext;
	}

	@Override
	public void add(String key, Object value) {
		context.put(key, value);
	}

	@Override
	public Object get(String key) {
		return context.get(key);
	}

	@Override
	public boolean contains(String key) {
		return context.containsKey(key);
	}

	@Override
	public void remove(String key) {
		context.remove(key);
	}

	@Override
	public Collection<String> keySet() {
		return Collections.unmodifiableCollection(context.keySet());
	}

	@Override
	public Map<String, Object> getMap() {
		return Collections.unmodifiableMap(context);
	}
}
