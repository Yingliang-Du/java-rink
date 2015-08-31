package com.ylw.validation.error;

import java.util.ArrayList;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.ylw.validation.common.PojomaticBean;

@AutoProperty
public class BeanErrors extends PojomaticBean {

	private final List<BeanErrorMessage> errors = new ArrayList<BeanErrorMessage>();

	public List<BeanErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Populate bean errors from Spring Errors
	 */
	public BeanErrors(Errors errs) {
		// Extract code and message from Errors - build bean errors
		List<ObjectError> list = errs.getAllErrors();
		for (ObjectError err : list) {
			errors.add(new BeanErrorMessage(err.getCode(), err.getDefaultMessage()));
		}
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
