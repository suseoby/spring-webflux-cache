package com.suseoby.spring.reactive.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import com.suseoby.spring.reactive.cache.ReactiveCacheAspectSupport.CacheOperationContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReativeCacheTest {

	protected final Logger logger = LoggerFactory.getLogger(ReativeCacheTest.class);

	@Test
	public void testMono() throws NoSuchMethodException, SecurityException {
		Collection<Cache> collection = new ArrayList<>();
		collection.add(new ReactiveMonoCache());
		MockReactiveCacheAspectSupport support = new MockReactiveCacheAspectSupport();
		CacheOperationContext context = cacheOperationContext(support, collection, "testMono", "");
		MockReactiveCacheOperationInvoker invoker = new MockReactiveCacheOperationInvoker();
		Mono<Integer> result = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result.block()).isEqualTo(1);
		Mono<Integer> result2 = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result2.block()).isEqualTo(1);
	}

	@Test
	public void testFlux() throws NoSuchMethodException, SecurityException {
		Collection<Cache> collection = new ArrayList<>();
		collection.add(new ReactiveFluxCache());
		MockReactiveCacheAspectSupport support = new MockReactiveCacheAspectSupport();
		CacheOperationContext context = cacheOperationContext(support, collection, "testFlux", "");
		MockReactiveCacheOperationInvoker invoker = new MockReactiveCacheOperationInvoker();
		Flux<Integer> result = (Flux<Integer>)support.lookupCachedItem(context, invoker, "user-id-list", Flux.class);
		assertThat(result.collectList().block().size()).isEqualTo(3);
		Flux<Integer> result2 = (Flux<Integer>)support.lookupCachedItem(context, invoker, "user-id-list", Flux.class);
		assertThat(result2.collectList().block().size()).isEqualTo(4);
	}

	@Test
	public void testUnlessMono() throws NoSuchMethodException, SecurityException {
		Collection<Cache> collection = new ArrayList<>();
		collection.add(new ReactiveMonoCache());
		MockReactiveCacheAspectSupport support = new MockReactiveCacheAspectSupport();
		CacheOperationContext context = cacheOperationContext(support, collection, "testUnlessMono", "#result == 1");
		MockReactiveCacheOperationInvoker invoker = new MockReactiveCacheOperationInvoker();
		Mono<Integer> result = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result.block()).isEqualTo(1);
		Mono<Integer> result2 = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result2.block()).isEqualTo(2);
		Mono<Integer> result3 = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result3.block()).isEqualTo(2);
	}

	@Test
	public void testNullMono() throws NoSuchMethodException, SecurityException {
		Collection<Cache> collection = new ArrayList<>();
		collection.add(new ReactiveMonoCache());
		MockReactiveCacheAspectSupport support = new MockReactiveCacheAspectSupport();
		CacheOperationContext context = cacheOperationContext(support, collection, "testNullMono", "");
		MockReactiveNullCacheOperationInvoker invoker = new MockReactiveNullCacheOperationInvoker();
		Mono<Integer> result = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result.block()).isEqualTo(null);
		Mono<Integer> result2 = (Mono<Integer>)support.lookupCachedItem(context, invoker, "user-id", Mono.class);
		assertThat(result2.block()).isEqualTo(null);
	}

	public CacheOperationContext cacheOperationContext(MockReactiveCacheAspectSupport support, Collection<Cache> collection, String methodName, String unless) throws NoSuchMethodException, SecurityException {
		support.setCaches(collection);
		support.setCacheResolver(new SimpleCacheResolver());
		CacheableOperation.Builder build = new CacheableOperation.Builder();
		build.setUnless(unless);
		CacheableOperation operation = build.build();
		return support.getOperationContext(operation, this.getClass().getMethod(methodName, null), null, this, this.getClass());
	}
}
