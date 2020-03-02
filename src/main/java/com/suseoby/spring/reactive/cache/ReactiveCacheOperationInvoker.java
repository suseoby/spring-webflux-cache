package com.suseoby.spring.reactive.cache;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCacheOperationInvoker {
	/**
	 * Invoke the cache operation defined by this instance. Wraps any exception
	 * that is thrown during the invocation in a {@link ThrowableWrapper}.
	 * @return the result of the operation
	 * @throws ThrowableWrapper if an error occurred while invoking the operation
	 */
	Mono<Object> invokeMono() throws ThrowableWrapper;

	Flux<Object> invokeFlux() throws ThrowableWrapper;


	/**
	 * Wrap any exception thrown while invoking {@link #invoke()}.
	 */
	@SuppressWarnings("serial")
	class ThrowableWrapper extends RuntimeException {

		private final Throwable original;

		public ThrowableWrapper(Throwable original) {
			super(original.getMessage(), original);
			this.original = original;
		}

		public Throwable getOriginal() {
			return this.original;
		}
	}
}
