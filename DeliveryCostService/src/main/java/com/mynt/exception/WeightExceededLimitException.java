package com.mynt.exception;

public class WeightExceededLimitException extends RuntimeException{

	public WeightExceededLimitException(String message) {
		super(message);
	}
}
