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
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.FieldError;
import com.ylw.enterprise.validation.error.FieldErrorCode;

public class FieldBinder {
	private static final Logger LOGGER = Logger.getLogger(FieldBinder.class);

	/**
	 * Binding request parameters to the bean fields record errors if not succeed
	 * 
	 * @param bean
	 * @param parameterMap
	 */
	public void bindToBean(@Nonnull AbstractValidationBean bean, @Nonnull Map<String, String[]> parameterMap) {
		// bind when field name match the map key, record error if not succeed
		FieldError error;
		Field[] fields = bean.getClass().getFields();
		for (Field field : fields) {
			String[] stringValues = parameterMap.get(field.getName());
			if (stringValues != null) {
				// found a match - bind to the field
				error = bindToField(bean, field, stringValues);
				// add error
				if (error != null) {
					bean.getErrors().add(error);
				}
			}
		}
	}
	
	static FieldError buildFieldError(String fieldName, String message) {
		return new FieldError(fieldName, FieldErrorCode.RUNTIME, message);
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
			if (fieldType.equals("float")) {
				return bindPrimitiveFloat(bean, field, stringValue);
			}
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			// build error
			error = new FieldError(fieldName, FieldErrorCode.EXCEPTION, e.getMessage());
			// log the exception
			e.printStackTrace();
		}

		// build and return error - field type had no been dealt with
		message = "Field type - " + fieldType + " need to be dealt with.";
		error = new FieldError(fieldName, FieldErrorCode.RUNTIME, message);
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
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER);
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
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER);
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
			error = new FieldError(fieldName, FieldErrorCode.NON_INTEGER);
		}

		return error;
	}

}
