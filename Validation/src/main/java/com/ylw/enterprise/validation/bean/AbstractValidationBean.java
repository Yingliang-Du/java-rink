/**
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
package com.ylw.enterprise.validation.bean;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ylw.enterprise.validation.binder.FieldBinder;
import com.ylw.enterprise.validation.error.BaseValidationError;
import com.ylw.enterprise.validation.error.FieldErrorCode;
import com.ylw.enterprise.validation.validator.FieldValidator;
import com.ylw.enterprise.validation.validator.ValidationRule;

public abstract class AbstractValidationBean {
	private static final Logger LOGGER = Logger.getLogger(AbstractValidationBean.class);

	// -------------------Validation---------------------
	// A set of error messages
	protected final Set<BaseValidationError> errors = Sets.newHashSet();
	private final Map<String, Set<BaseValidationError>> fieldErrorMap = Maps.newTreeMap();

	/* Deal with errors */
	public Set<BaseValidationError> getErrors() {
		return errors;
	}
	
	public void addError(BaseValidationError error) {
		errors.add(error);
	}
	
	/** See if there are errors in this object */
	public boolean hasError() {
		return !errors.isEmpty() || !fieldErrorMap.isEmpty();
	}

	/**
	 * Clear both object and field errors - normally do this before bind and validate
	 * @return this instance
	 */
	public AbstractValidationBean clearErrors() {
		// Clear object errors
		errors.clear();
		// Clear field errors
		fieldErrorMap.clear();
		// return this bean
		return this;
	}
		
	/* Deal with field errors */
	public Map<String, Set<BaseValidationError>> getFieldErrorMap() {
		return fieldErrorMap;
	}
		
	/**
	 * @return Validation Rule Builder
	 */
	protected ValidationRule.Builder onRule() {
		return ValidationRule.Builder.defaultValues();
	}
	
	/**
	 * Override this method to specify validation rule for each field in the object and validate.
	 */
	public abstract void validate();
	
	
	/**
	 * Validate the field base on the validation rule
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule) {
		// Validate
		FieldValidator validator = new FieldValidator(validationRule);
		FieldErrorCode fieldErrorCode = validator.validate(fieldValue);

		// Deal with violated rules and add field error to errors
		if (fieldErrorCode == null) { 
			// There are no errors
			return;
		}

		// Build error and add to the set
		BaseValidationError error = new BaseValidationError(fieldName, fieldErrorCode);
		// add to the object error set
		errors.add(error);
		// add to the field error map
		Set<BaseValidationError> fieldErrors = fieldErrorMap.get(fieldName);
		if (fieldErrors == null) {
			fieldErrors = Sets.newHashSet();
		}
		fieldErrors.add(error);
		fieldErrorMap.put(fieldName, fieldErrors);
	}
	
	/**
	 * Bind this bean from form parameters
	 * @param parameterMap - parameter map from web request
	 * @return this instance
	 */
	public AbstractValidationBean bind(@Nonnull Map<String, String[]> parameterMap) {
		BaseValidationError error;
		// get all fields of this bean
		Field[] fields = this.getClass().getDeclaredFields();
		// bind parameter to each matching field
		for (int i=0; i<fields.length; i++) {
			Field field = fields[i];
			String[] stringValues = parameterMap.get(field.getName());
			if (stringValues != null) {
				// found a match - bind to the field
				error = FieldBinder.bindToField(this, field, stringValues);
				// add error
				if (error != null) {
					errors.add(error);
				}
			}
		}
		
		// return this bean
		return this;
	}

}
