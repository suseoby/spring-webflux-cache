package com.suseoby.spring.reactive.cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MockReactiveNullCacheOperationInvoker implements ReactiveCacheOperationInvoker {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Mono<Object> invokeMono() throws ThrowableWrapper {
		logger.debug("invokeMono return null");
		return Mono.empty();
	}

	@Override
	public Flux<Object> invokeFlux() throws ThrowableWrapper {
		logger.debug("invokeFlux");
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		return Flux.fromIterable(list);
	}

}
