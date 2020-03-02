package com.suseoby.spring.reactive.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @see org.springframework.cache.interceptor.CacheInterceptor
 * @author seoby
 *
 */
@SuppressWarnings("serial")
public class ReactiveCacheInterceptor extends ReactiveCacheAspectSupport implements MethodInterceptor, Serializable {

	@Override
	@Nullable
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();

		ReactiveCacheOperationInvoker invoker = new ReactiveCacheOperationInvoker() {

			@Override
			public Mono<Object> invokeMono() throws ThrowableWrapper {
				try {
					return (Mono<Object>)invocation.proceed();
				}
				catch (Throwable ex) {
					throw new ReactiveCacheOperationInvoker.ThrowableWrapper(ex);
				}
			}

			@Override
			public Flux<Object> invokeFlux() throws ThrowableWrapper {
				try {
					return (Flux<Object>)invocation.proceed();
				}
				catch (Throwable ex) {
					throw new ReactiveCacheOperationInvoker.ThrowableWrapper(ex);
				}
			}
		};

		try {
			return execute(invoker, invocation.getThis(), method, invocation.getArguments());
		}
		catch (CacheOperationInvoker.ThrowableWrapper th) {
			throw th.getOriginal();
		}
	}
}
