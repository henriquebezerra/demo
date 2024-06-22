package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private int errorCode;
	
	public BusinessException (String message, int errorCode) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}

}
