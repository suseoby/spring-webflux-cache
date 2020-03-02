package com.suseoby.spring.reactive.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class ReactiveMonoCache implements Cache {

	private Map<Object, ValueWrapper> map = new HashMap<>();

	@Override
	public String getName() {
		return "def";
	}

	@Override
	public Object getNativeCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		return map.get(key);
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		ValueWrapper vw = map.get(key);
		if (vw != null) {
			return (T)vw.get();
		}

		return null;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		map.put(key, new SimpleValueWrapper(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
