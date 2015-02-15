/*
 * This file is part of
 *
 * Copyright (C) 2014-2015 The YLW-Java-Validation-Framework Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ylw.enterprise.validation.binder;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.FieldError;
import com.ylw.enterprise.validation.error.FieldErrorCode;

public class FieldBinder {
	private static final Logger LOGGER = Logger.getLogger(FieldBinder.class);
	
	static FieldError buildFieldError(String fieldName, String message) {
		return new FieldError(fieldName, FieldErrorCode.RUNTIME, message, false);
	}

	/**
	 * Bind string value to the field
	 * 
	 * @param field
	 * @param stringValues
	 * @return error if not succeed
	 */
	public static FieldError bindToField(AbstractValidationBean bean, Field field, String[] stringValues) {
		String message;
		String fieldName = field.getName();

		// Check string values
		if (stringValues.length == 1) {
			return bindToField(bean, field, stringValues[0]);
		}
		else if (stringValues.length > 1) {
			message = "Only deal with one value per field for now";
			return buildFieldError(fieldName, message);
		}
		else {
			message = "No value exist";
			return buildFieldError(fieldName, message);
		}
	}
	
	static void bindToField(AbstractValidationBean bean, String fieldName, String stringValue) {
		try {
			Field field = bean.getClass().getField(fieldName);
			bindToField(bean, field, stringValue);
		}
		catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static FieldError bindToField(AbstractValidationBean bean, Field field, String stringValue) {
		FieldError error = null;
		String message;
		// get field name
		String fieldName = field.getName();
		// get field type
		String fieldType = field.getType().getSimpleName();
		// make field accessible
		field.setAccessible(true);

		try {
			if (fieldType.equals("String")) {
				return bindString(bean, field, stringValue);
			}
			if (fieldType.equals("int")) {
				return bindPrimitiveInt(bean, field, stringValue);
			}
			if (fieldType.equals("Integer")) {
				return bindInteger(bean, field, stringValue);
			}
			if (fieldType.equals("Long") || fieldType.equals("long")) {
				return bindLong(bean, field, stringValue);
			}
			if (fieldType.equals("float")) {
				return bindPrimitiveFloat(bean, field, stringValue);
			}
			if (fieldType.equals("Date")) {
				return bindDate(bean, field, stringValue);
			}
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			// build error
			error = new FieldError(fieldName, FieldErrorCode.EXCEPTION, e.getMessage(), false);
			// log the exception
			e.printStackTrace();
		}

		// build and return error - field type had no been dealt with
		message = "Field type - " + fieldType + " need to be dealt with.";
		error = new FieldError(fieldName, FieldErrorCode.RUNTIME, message, false);
		return error;
	}

	private static FieldError bindString(AbstractValidationBean bean, Field field, String stringValue)
			throws IllegalArgumentException, IllegalAccessException {
		field.set(bean, stringValue);
		return null;
	}

	private static FieldError bindPrimitiveInt(AbstractValidationBean bean, Field field, String stringValue)
			throws IllegalArgumentException, IllegalAccessException {
		FieldError error = null;
		String fieldName = field.getName();

		try {
			field.setInt(bean, Integer.valueOf(stringValue));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER, false);
		}

		return error;
	}

	private static FieldError bindInteger(AbstractValidationBean bean, Field field, String stringValue)
			throws IllegalArgumentException, IllegalAccessException {
		FieldError error = null;
		String fieldName = field.getName();

		try {
			field.set(bean, Integer.valueOf(stringValue));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER, false);
		}

		return error;
	}
	
	private static FieldError bindLong(AbstractValidationBean bean, Field field, String stringValue)
			throws IllegalArgumentException, IllegalAccessException {
		FieldError error = null;
		String fieldName = field.getName();

		try {
			// Bind the field with the valid value
			// Primitive long should also work because of auto box of Java
			field.set(bean, Long.valueOf(stringValue));
		}
		catch (NumberFormatException e) {
			// Do not bind - use the default value defined in the bean
			e.printStackTrace();
			error = new FieldError(fieldName, FieldErrorCode.NON_LONG, false);
		}

		return error;
	}

	private static FieldError bindPrimitiveFloat(AbstractValidationBean bean, Field field, String stringValue)
			throws IllegalArgumentException, IllegalAccessException {
		FieldError error = null;
		String fieldName = field.getName();

		try {
			field.setFloat(bean, Float.valueOf(stringValue));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER, false);
		}

		return error;
	}
	
	private static FieldError bindDate(AbstractValidationBean bean, Field field, String stringValue) 
			throws IllegalArgumentException, IllegalAccessException {
		FieldError error = null;
		String fieldName = field.getName();
		
		SimpleDateFormat format = new SimpleDateFormat(bean.getDateFormat());

		try {
			field.set(bean, format.parse(stringValue));
		}
		catch (ParseException e) {
			// The string value can not be parsed to Date
			e.printStackTrace();
			error = new FieldError(fieldName, FieldErrorCode.NON_DATE, false);
		}
		
		return error;
	}

}
