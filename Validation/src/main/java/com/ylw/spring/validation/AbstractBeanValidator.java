package com.ylw.spring.validation;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ylw.validation.error.BeanErrors;

public abstract class AbstractBeanValidator implements Validator {
	private Errors errors;
	
	private void initErrorsForBean(Object obj) {
		// Instantiate errors object
		this.errors = new BeanPropertyBindingResult(obj, obj.getClass().getName());
	}

	/**
	 * Initiate errors and Validate Java Bean
	 * @param obj
	 * @return errors object
	 */
	public BeanErrors validate(Object obj) {
		initErrorsForBean(obj);
		validate(obj, errors);
		return new BeanErrors(errors);
	}

}
