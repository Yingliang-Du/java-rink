package com.ylw.validation.error;

import org.pojomatic.annotations.AutoProperty;

import com.ylw.validation.common.PojomaticBean;

@AutoProperty
public class ErrorMessage extends PojomaticBean {
	
	private final String code;
	private final String message;
	
	public ErrorMessage() {
		// initiate code and message
		this.code = null;
		this.message = null;
	}
	
	public ErrorMessage(String code, String message) {
		// initiate code and message
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

}
