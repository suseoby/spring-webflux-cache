package com.suseoby.spring.reactive.cache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

public class MockReactiveCacheAspectSupport extends ReactiveCacheAspectSupport {
	private Collection<? extends Cache> caches;

	public void setCaches(Collection<? extends Cache> caches) {
		this.caches = caches;
	}

	@Override
	protected Collection<? extends Cache> getCaches(CacheOperationInvocationContext<CacheOperation> context, CacheResolver cacheResolver) {
		return this.caches;
	}

}
