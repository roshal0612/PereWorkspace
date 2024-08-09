package com.mynt.exception;

public class RecordNotFoundException extends RuntimeException{

	public RecordNotFoundException (String message) {
		super(message);
	}
}
