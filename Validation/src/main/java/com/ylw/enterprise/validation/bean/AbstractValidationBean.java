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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ylw.enterprise.validation.binder.AbstractBeanBinder;
import com.ylw.enterprise.validation.binder.FieldBinder;
import com.ylw.enterprise.validation.error.BeanError;
import com.ylw.enterprise.validation.error.ErrorMessage;
import com.ylw.enterprise.validation.error.FieldError;
import com.ylw.enterprise.validation.error.FieldErrorCode;
import com.ylw.enterprise.validation.validator.AbstractProjectValidator;
import com.ylw.enterprise.validation.validator.FieldValidator;
import com.ylw.enterprise.validation.validator.ValidationRule;

public abstract class AbstractValidationBean {
	private static final Logger LOGGER = Logger.getLogger(AbstractValidationBean.class);

	// --------------Field text format----------------
	protected String dateFormat = "MM/dd/yyyy";

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	// --------------------Binding----------------------
	private final Map<String, String> formKeyMap = Maps.newTreeMap();
	private String beanName;

	public Map<String, String> getFormKeyMap() {
		return formKeyMap;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * The default form key map beanName.fieldName
	 * beanName can be set or use class name if not
	 */
	private void buildDefaultFormKeyMap() {
		// beanName - use class name if not set
		if (beanName == null) {
			beanName = this.getClass().getSimpleName();
		}
		// Get all fields of this bean
		Field[] fields = this.getClass().getDeclaredFields();
		// Build default form key map for each field in this bean
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			// Add the field form key to form key map
			formKeyMap.put(fieldName, beanName + "." + fieldName);
		}
		LOGGER.info(this.getClass().getSimpleName() + " default form key map: " + formKeyMap);
	}

	/**
	 * Build Form Key Map
	 */
	public void buildFormKeyMap() {
		// Build default form key map
		buildDefaultFormKeyMap();
		// Customize base on form keys defined in FormKey class
		Class<?> formKeyClass = getFormKeyClass();
		if (formKeyClass != null) {
			// If form key class defined
			Field[] fields = formKeyClass.getDeclaredFields();
			// Get value for form key of each field and put them in to form key map
			String key = null;
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				try {
					key = (String) field.get(this);
				}
				catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
					// Error happened when retrieve key from FormKey
					LOGGER.info("Error happened when retrieve key from FormKey");
					e.printStackTrace();
				}
				// Put the form key into the map
				if (key != null) {
					formKeyMap.put(field.getName(), key);
				}
			}
		}
		LOGGER.info(this.getClass().getSimpleName() + " Form Key Map: " + formKeyMap);
	}

	/**
	 * Check if the FormKey nested class declared in the sub class. FormKey class is where the customized form key for
	 * each field defined. Form keys in this class will be used in the form and data binding logic.
	 * 
	 * @return true - if the class is declared; false - if not
	 */
	private boolean isFormKeyClassDefined() {
		Class<?> cl = getClass();
		// Loop through all declared class and see if the FormKey class defined
		for (Class<?> innerClass : cl.getDeclaredClasses()) {
			if (innerClass.getSimpleName().equals("FormKey")) {
				return true;
			}
			LOGGER.info(innerClass.getCanonicalName());
		}
		return false;
	}

	/**
	 * Get FormKey class if it is defined in the sub class
	 */
	public Class<?> getFormKeyClass() {
		// Get class of this bean
		Class<?> thisClass = this.getClass();
		// Loop through all declared class and get the FormKey class defined
		for (Class<?> innerClass : thisClass.getDeclaredClasses()) {
			if (innerClass.getSimpleName().equals("FormKey")) {
				// Return FormKey class if defined
				return innerClass;
			}
			LOGGER.info(innerClass.getCanonicalName());
		}
		// Return null if FormKey class not defined
		return null;
	}

	/**
	 * Bind this bean from HTTP request form parameters
	 * 
	 * @param parameterMap
	 *           - parameter map from web request
	 * @return this instance
	 */
	public AbstractValidationBean bind(@Nonnull Map<String, String[]> parameterMap) {
		FieldError error;
		// get all fields of this bean
		Field[] fields = this.getClass().getDeclaredFields();
		// bind parameter to each matching field
		for (int i = 0; i < fields.length; i++) {
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

	public AbstractValidationBean bind(@Nonnull AbstractBeanBinder binder) {
		// return the binded bean
		return binder.bind();
	}

	// -------------------Validation---------------------
	// A set of error messages
	protected final Set<BeanError> errors = Sets.newHashSet();
	private final Map<String, Set<FieldError>> fieldErrorMap = Maps.newTreeMap();

	/* Deal with errors */
	public Set<BeanError> getErrors() {
		return errors;
	}

	public void addError(BeanError error) {
		errors.add(error);
	}

	/**
	 * Construct and add error from general error message
	 * 
	 * @param message
	 */
//	public void addError(String message) {
//		addError(new BeanError(message));
//	}

	/* See if there are errors in this object */
	public boolean hasError() {
		return !errors.isEmpty() || !fieldErrorMap.isEmpty();
	}
	
	/**
	 * Validation will pass if there is no non-ignorable errors after validation
	 * @return true if passed the validation, false otherwise
	 */
	public boolean pass() {
		// if errors contains non-ignorable error - return false
		if (!errors.isEmpty()) {
			for(BeanError error : errors) {
				if(!error.isIgnorable()) {
					return false;
				}
			}
		}
		// if field errors contains non-ignorable error - return false
		if (!fieldErrorMap.isEmpty()) {
			for(Set<FieldError> fieldErrors : fieldErrorMap.values()) {
				for(BeanError error : fieldErrors) {
					if(!error.isIgnorable()) {
						return false;
					}
				}
			}
		}
		// otherwise - return true
		return true;
	}

	/**
	 * Clear both object and field errors - normally do this before bind and validate
	 * 
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
	public Map<String, Set<FieldError>> getFieldErrorMap() {
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
	public abstract AbstractValidationBean validate();

	/**
	 * Validate the field base on the validation rule with customized error message
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 * @param errorMessage - customized error message
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule, String errorMessage) {
		validate(validationRule, fieldName, fieldValue, errorMessage);
	}

	/**
	 * Validate the field base on the validation rule with customized error message code
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 * @param message - customized error code
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule, ErrorMessage message) {
		validate(validationRule, fieldName, fieldValue, message);
	}

	/**
	 * Validate the field base on the validation rule
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule) {
		validate(validationRule, fieldName, fieldValue, null);
	}

	/*
	 * Validate field value base on validation rule Create error base on error code or customized error
	 */
	private void validate(ValidationRule validationRule, String fieldName, Object fieldValue, Object customError) {
		// Validate
		FieldValidator validator = new FieldValidator(validationRule);
		FieldErrorCode fieldErrorCode = validator.validate(fieldValue);
		// Deal with violated rules and add field error to errors if any
		boolean ignorable = validationRule.isIgnorable();
		if (fieldErrorCode != null) {
			// There is error - build error object
			FieldError error = new FieldError(fieldName, fieldErrorCode, ignorable);
			if (customError == null) {
				// build error object base on error code
				error = new FieldError(fieldName, fieldErrorCode, ignorable);
			}
			else {
				if (customError instanceof ErrorMessage) {
					error = new FieldError(fieldName, ((ErrorMessage) customError).getMessage(), ignorable);
				}
				else
					if (customError instanceof String) {
						error = new FieldError(fieldName, (String) customError, ignorable);
					}
					else {
						LOGGER.warn("Unricegnized customized message -> " + customError);
					}
			}
			// Add field error
			addFieldError(fieldName, error);
		}
	}

	/*
	 * Add field error to error set and field error map
	 */
	private void addFieldError(String fieldName, FieldError error) {

		// Add error to the object error set
		errors.add(error);
		// add to the field error map
		Set<FieldError> fieldErrors = fieldErrorMap.get(fieldName);
		if (fieldErrors == null) {
			fieldErrors = Sets.newHashSet();
		}
		fieldErrors.add(error);
		fieldErrorMap.put(fieldName, fieldErrors);
	}
	
	/* Project Specific Validation */
	private AbstractProjectValidator projectValidator;

	/**
	 * 
	 * Create project specific Validator extends {@link AbstractProjectValidator} Set the validator here so the
	 * validation framework can call the customized validation logic
	 *
	 * @param projectValidator - project specific validator
	 */
	public void setProjectValidator(AbstractProjectValidator projectValidator) {
		this.projectValidator = projectValidator;
	}
	
	/**
	 * Call project validator to verify customized validation logic
	 * @param customized validation method 
	 * @return true/false based on verification result
	 */
	protected boolean verify(String methodName) {
		// Check the instance of project specific validator
		LOGGER.info("The instance of project validator -> " + projectValidator);
		Preconditions.checkNotNull(projectValidator, "Please instantiate the project specific validator and set it here");
		// call the customized validation method
		try {
			// Get the method
			Method method = projectValidator.getClass().getMethod(methodName);
			// call the method
			return (boolean) method.invoke(projectValidator);
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException | SecurityException e) {
			// Can not get the method
			LOGGER.info("Can not get method -" + methodName + "- in class -" + projectValidator.getClass().getName());
			e.printStackTrace();
		}
		// return false by default
		return false;
	}

}
