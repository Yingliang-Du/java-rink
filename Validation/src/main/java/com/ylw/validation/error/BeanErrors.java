package com.ylw.validation.error;

import java.util.ArrayList;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.ylw.validation.common.PojomaticBean;

@AutoProperty
public class BeanErrors extends PojomaticBean {

	private final List<ErrorMessage> errors = new ArrayList<ErrorMessage>();

	public BeanErrors() {
	}

	/**
	 * Populate bean errors from Spring Errors
	 */
	public BeanErrors(Errors errs) {
		// Extract code and message from Errors - build bean errors
		List<ObjectError> list = errs.getAllErrors();
		for (ObjectError err : list) {
			errors.add(new ErrorMessage(err.getCode(), err.getDefaultMessage()));
		}
	}
	
	public List<ErrorMessage> getErrors() {
		return errors;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
