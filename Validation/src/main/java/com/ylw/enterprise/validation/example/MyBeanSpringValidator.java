package com.ylw.enterprise.validation.example;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.ylw.enterprise.validation.error.FieldErrorCode;
import com.ylw.spring.validation.AbstractBeanValidator;
import com.ylw.spring.validation.SpringValidationUtils;

public class MyBeanSpringValidator extends AbstractBeanValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// This validator only work for MyBean class
		return MyBean.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors err) {
		MyBean myBean = (MyBean) obj;
		// Validate using spring ValidationUtils
		ValidationUtils.rejectIfEmpty(err, "stringField", FieldErrorCode.MyBean_stringField_null.getId(), null,
				FieldErrorCode.MyBean_stringField_null.getMessage());
		SpringValidationUtils.rejectIfDateBefore(err, "startDate", new Date(),
				FieldErrorCode.MyBean_startDate_beforeToday.getId(),
				FieldErrorCode.MyBean_startDate_beforeToday.getMessage());
		SpringValidationUtils.rejectIfDateBefore(err, "expirDate", myBean.getStartDate(),
				FieldErrorCode.MyBean_expirDate_beforeStartDate.getId(),
				FieldErrorCode.MyBean_expirDate_beforeStartDate.getMessage());
	}
}
