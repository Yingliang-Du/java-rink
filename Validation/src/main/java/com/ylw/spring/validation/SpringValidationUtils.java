package com.ylw.spring.validation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public abstract class SpringValidationUtils {
	
	private static final Logger LOGGER = Logger.getLogger(SpringValidationUtils.class);
	
	/**
	 * Reject the given field with the given error code, error arguments
	 * and default message if the date value earlier than the given minimum date.
	 * <p>The object whose field is being validated does not need to be passed
	 * in because the {@link Errors} instance can resolve field values by itself
	 * (it will usually hold an internal reference to the target object).
	 * @param errors the {@code Errors} instance to register errors on
	 * @param field the field name to check
	 * @param minDate the minimum date value
	 * @param errorCode the error code, interpretable as message key
	 * @param defaultMessage fallback default message
	 */
	public static void rejectIfDateBefore(
			Errors errors, String field, Date minDate, String errorCode, String defaultMessage) {
		
		rejectIfDateBefore(errors, field, minDate, errorCode, null, defaultMessage);
	}
	
	/**
	 * Reject the given field with the given error code, error arguments
	 * and default message if the date value earlier than the given minimum date.
	 * <p>The object whose field is being validated does not need to be passed
	 * in because the {@link Errors} instance can resolve field values by itself
	 * (it will usually hold an internal reference to the target object).
	 * @param errors the {@code Errors} instance to register errors on
	 * @param field the field name to check
	 * @param minDate the minimum date value
	 * @param errorCode the error code, interpretable as message key
	 * @param errorArgs the error arguments, for argument binding via MessageFormat
	 * (can be {@code null})
	 * @param defaultMessage fallback default message
	 */
	public static void rejectIfDateBefore(
			Errors errors, String field, Date minDate, String errorCode, Object[] errorArgs, String defaultMessage) {

		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (value == null || !StringUtils.hasLength(value.toString())) {
			errors.rejectValue(field, errorCode, errorArgs, "field value must not be null.");
		}
		else if (minDate == null || !StringUtils.hasLength(minDate.toString())) {
			errors.rejectValue(field, errorCode, errorArgs, "minDate param value must not be null.");
		}
		else if (!(value instanceof Date)) {
			errors.rejectValue(field, errorCode, errorArgs, "field must be instance of Date.");
		}
		else if (((Date)value).before(minDate)) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}

}
