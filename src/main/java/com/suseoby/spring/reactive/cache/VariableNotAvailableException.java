package com.suseoby.spring.reactive.cache;

import org.springframework.expression.EvaluationException;

@SuppressWarnings("serial")
class VariableNotAvailableException extends EvaluationException {

	private final String name;


	public VariableNotAvailableException(String name) {
		super("Variable not available");
		this.name = name;
	}


	public final String getName() {
		return this.name;
	}

}