package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseError {
	
	private String message;
	
	private int errorCode;
	
	public ResponseError (String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}

}
