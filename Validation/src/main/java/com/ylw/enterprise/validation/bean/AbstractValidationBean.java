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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.Property;

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

public abstract class AbstractValidationBean implements Comparable<AbstractValidationBean> {
	private static final Logger LOGGER = Logger.getLogger(AbstractValidationBean.class);

	/* ----------Constructors---------- */
	/**
	 * Default constructor
	 */
	public AbstractValidationBean() {
		// Set default value to beanName - which bean will be sorted by default
		this.beanName = this.getClass().getSimpleName();
	}

	/**
	 * Construct bean for web form - will build form key map to be used in web form and data binding
	 */
	public AbstractValidationBean(String beanName) {
		// Specify form bean name and build form key map
		this.beanName = beanName;
		// Find FormKey class
		this.formKeyClass = findFormKeyClass();
		// Instantiate FormKey class if defined
		this.formKeyInstance = null;
		if (formKeyClass != null) {
			try {
				Constructor<?> ctor = formKeyClass.getDeclaredConstructor(this.getClass());
				this.formKeyInstance = ctor.newInstance(this);
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				// Error happened when instantiate FormKey inner class
				LOGGER.warn("Error happened when instantiate FormKey inner class, " + e.getMessage());
				e.printStackTrace();
			}
		}
		else {
			LOGGER.warn("There is not FormKey inner class defined");
		}
		// Build form key map
		this.buildFormKeyMap();
	}

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
	@Property
	private final String beanName;
	public Class<?> formKeyClass;
	public Object formKeyInstance;

	public Map<String, String> getFormKeyMap() {
		return formKeyMap;
	}

	public String getBeanName() {
		return beanName;
	}

	/**
	 * The default form key map beanName.fieldName beanName can be set or use class name if not
	 */
	private void buildDefaultFormKeyMap() {
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
	private void buildFormKeyMap() {
		// Build default form key map
		buildDefaultFormKeyMap();
		// Customize base on form keys defined in FormKey class
		LOGGER.info("FormKey inner class defined -> " + this.formKeyClass + " in class -> " + this.getClass().getName());
		if (formKeyClass != null) {
			// If form key class defined
			Field[] fields = formKeyClass.getDeclaredFields();
			// Get value for form key of each field and put them in to form key map
			String key = null;
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				try {
					// Get the value of the key defined in FormKey inner class
					LOGGER.info("field -> " + field.getName());
					key = (String) field.get(this.formKeyInstance);
					LOGGER.info("key -> " + key);
				}
				catch (IllegalAccessException e) {
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
	 * Find FormKey class if it is defined in the sub class
	 */
	private Class<?> findFormKeyClass() {
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
	/**
	 * A set of error messages The default error type is BeanError or sub-type of BeanError; or any class that
	 * implemented getMessage() method (this is for legacy code not extend BeanError class); Error String can also be
	 * added to errors by convert it to BeanError.
	 */
	@SuppressWarnings("rawtypes")
	protected final Set errors = new HashSet(); // Sets.newHashSet();
	private final Map<String, Set<FieldError>> fieldErrorMap = Maps.newTreeMap();

	/* Methods deal with errors */
	@SuppressWarnings("rawtypes")
	public Set getErrors() {
		return errors;
	}

	/**
	 * Retrieve only errors with provided type and add them to provided error set
	 * @param tErrors provided error set
	 * @return errors with provided type
	 */
	public <T> Set<T> getErrors(Set<T> tErrors) {
		if (tErrors == null) {
			tErrors = Sets.newHashSet();
		}
	    for (Object o : errors) {
	    	try {
	    		tErrors.add((T) o);
	    	}
	    	catch (ClassCastException ex) {
	    		LOGGER.info("The object with type -" + o.getClass().getSimpleName() + "- is not the right type");
	    	}
	    }
	    return tErrors;
	}

	@SuppressWarnings("unchecked")
	public void addError(Object error) {
		if (error instanceof BeanError) {
			// Add BeanError type
			errors.add(error);
		}
		else if (error instanceof String) {
			// Add String error type
			// Create BeanError out of message
			errors.add(new BeanError((String) error));
		}
		else if (getMessageMethod(error) != null) {
			// Add Other error type
			// Make sure the class implement getMessage() method
			errors.add(error);
		}
		else {
			// The class has to implement getMessage method to be added to errors
			LOGGER.warn("The class -" + error.getClass().getSimpleName()
							+ "- has to implement getMessage method to be added to errors");
		}
	}

	/**
	 * Find out if the class implemented {@link getMessage()} method
	 * @return true when the class implemented getMessage method, false otherwise
	 */
	private Method getMessageMethod(Object error) {
		// See if the class implemented getMessage() method
		try {
			return error.getClass().getMethod("getMessage");
		}
		catch (NoSuchMethodException | SecurityException e) {
			// The class has to implement getMessage method to be a error instance
			LOGGER.warn("The class -" + error.getClass().getSimpleName()
					+ "- has to implement getMessage method to be a error instance");
			return null;
		}
	}

	/**
	 * Construct and add error from general error message
	 *
	 * @param message
	 */
	// public void addError(String message) {
	// addError(new BeanError(message));
	// }

	/* See if there are errors in this object */
	public boolean hasError() {
		return !errors.isEmpty() || !fieldErrorMap.isEmpty();
	}

	/**
	 * Validation will pass if there is no non-ignorable errors after validation
	 *
	 * @return true if passed the validation, false otherwise
	 */
	public boolean pass() {
		// if errors contains non-ignorable error - return false
		if (!errors.isEmpty()) {
			for (Object err : errors) {
				try {
					BeanError error = (BeanError) err;
					if (!error.isIgnorable()) {
						return false;
					}
				}
				catch (ClassCastException ex) {
					// Non BeanError type in errors
					LOGGER.warn("There is non BeanError type error -" + err.getClass().getSimpleName() + "- in errors!");
					return false;
				}
			}
		}
		// if field errors contains non-ignorable error - return false
		if (!fieldErrorMap.isEmpty()) {
			for (Set<FieldError> fieldErrors : fieldErrorMap.values()) {
				for (BeanError error : fieldErrors) {
					if (!error.isIgnorable()) {
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
	 * Validate the field base on the validation rule
	 *
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule) {
		validate(fieldName, fieldValue, validationRule, null);
	}

	/*
	 * Validate field value base on validation rule Create error base on error code or customized error
	 */
	/**
	 * Validate the field base on the validation rule with customized error message,
	 * error code or other error instance
	 *
	 * @param fieldName
	 * @param fieldValue
	 * @param validationRule
	 * @param customError - customized error message, error code or other error instance
	 */
	protected void validate(String fieldName, Object fieldValue, ValidationRule validationRule, Object customError) {
		// Validate
		FieldValidator validator = new FieldValidator(validationRule);
		FieldErrorCode fieldErrorCode = validator.validate(fieldValue);
		// Deal with violated rules and add field error to errors if any
		boolean ignorable = validationRule.isIgnorable();
		if (fieldErrorCode != null) {
			// There is error - build error objects base on error code
			// Default field error
			FieldError fieldError = new FieldError(fieldName, fieldErrorCode, ignorable);
			// Default bean error
			Object beanError = new BeanError(fieldErrorCode.getMessage(), ignorable);
			/*
			 * Customize error message and error instance - When string message passed in, use that as error message When
			 * instance with getMessage() implemented passed in, use that instance add to errors
			 */
			if (customError != null) {
				Method getMessage = getMessageMethod(customError);
				// build error object base on error code
				if (customError instanceof ErrorMessage) {
					fieldError = new FieldError(fieldName, ((ErrorMessage) customError).getMessage(), ignorable);
					beanError = customError;
				}
				else if (customError instanceof String) {
					fieldError = new FieldError(fieldName, (String) customError, ignorable);
					beanError = new BeanError((String) customError, ignorable);
				}
				else if (getMessage != null) {
					try {
						String message = (String) getMessage.invoke(customError, null);
						fieldError = new FieldError(fieldName, message, ignorable);
						// Use the customError instance if it implemented getMessage()
						beanError = customError;
					}
					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// Log error
						LOGGER.warn("Error happened when invoke getMessage method of error instance: ");
						e.printStackTrace();
					}
				}
				else {
					LOGGER.warn("Unricegnized customized message instance -> " + customError.getClass().getName());
				}
			}
			// Add field error
			addFieldError(fieldName, fieldError, beanError);
		}
	}

	/*
	 * Add field error to the field error map
	 * Add bean error to the bean error set
	 */
	private void addFieldError(String fieldName, FieldError fieldError, Object beanError) {

		// Add bean error to the bean error set
		errors.add(beanError);
		// Add field error to the field error map
		Set<FieldError> fieldErrors = fieldErrorMap.get(fieldName);
		if (fieldErrors == null) {
			fieldErrors = Sets.newHashSet();
		}
		fieldErrors.add(fieldError);
		fieldErrorMap.put(fieldName, fieldErrors);
	}

	/* Project Specific Validation */
	private AbstractProjectValidator projectValidator;

	/**
	 *
	 * Create project specific Validator extends {@link AbstractProjectValidator} Set the validator here so the
	 * validation framework can call the customized validation logic
	 *
	 * @param projectValidator
	 *           - project specific validator
	 */
	public void setProjectValidator(AbstractProjectValidator projectValidator) {
		this.projectValidator = projectValidator;
	}

	/**
	 * Call project validator to verify customized validation logic
	 *
	 * @param customized
	 *           validation method
	 * @return true/false based on verification result
	 */
	protected boolean verify(String methodName) {
		// Check the instance of project specific validator
		LOGGER.info("The instance of project validator -> " + projectValidator);
		Preconditions.checkNotNull(projectValidator, "Before doing the customized validation -" + methodName + "-, "
				+ "please instantiate the project specific validator and set it into this bean --"
				+ this.getClass().getSimpleName() + "--");
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

	// ---------------Convert to JSON----------------
	public String toJson() {
		// Place holder for converted JSON string
		String jsonString = null;
		// Instantiate Jackson object mapper
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Unformatted JSON string
			// jsonString = mapper.writeValueAsString(this);
			// Formatted JSON string
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		}
		catch (IOException e) {
			// Got exception during conversion
			LOGGER.error("Exception happened when convert " + this.getClass().getSimpleName() + " to JSON");
			e.printStackTrace();
		}
		LOGGER.info(this.getClass().getSimpleName() + ": " + jsonString);
		// Return the JSON string
		return jsonString;
	}

	// ---------------Override equals, toString and hashCode--------------
	@Override
	public boolean equals(Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	// -------------Default comparable method--------------
	/**
	 * Compare to beanName by default - for sort multiple items by beanName
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractValidationBean otherBean) {
		// Default by compare beanName
		return this.beanName.compareTo(otherBean.beanName);
	}
}
