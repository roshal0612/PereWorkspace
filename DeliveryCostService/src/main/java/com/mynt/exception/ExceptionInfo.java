package com.mynt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ExceptionInfo {
	
	private int code;
	
	private String message;
}
