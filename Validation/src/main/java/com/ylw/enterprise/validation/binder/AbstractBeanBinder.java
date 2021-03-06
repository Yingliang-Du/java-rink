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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.FieldError;

/**
 *
 * This class is designed for binding form domain object (POJO) from web form. The bind() method will do the automatic
 * binding base on parameters match to the bean. For each form domain object in your project, you need to create a
 * binder extends this class. You need to override the following methods to add customized binding logic:
 *
 * preBind() - return form domain object populated with default values required.
 *
 * buildParameterMap() - return parameter map conform HTTP request parameter map.
 *
 * postBind(@Nonnull AbstractValidationBean bean) - return the bean after post binding as needed.
 *
 */
public abstract class AbstractBeanBinder {
	private static final Logger LOGGER = Logger.getLogger(AbstractBeanBinder.class);

	private AbstractValidationBean bean;

	/**
	 * Override and put your binding logic
	 * @param bean
	 * @param parameterObject
	 */
//	public abstract void bindToBean(@Nonnull AbstractValidationBean bean, @Nonnull Object parameterObject);
//
//	protected void bindToField(AbstractValidationBean bean, String fieldName, String stringValue) {
//		FieldBinder.bindToField(bean, fieldName, stringValue);
//	}

	/**
	 * Binding HTTP request form parameters to the bean fields, record errors if not succeed.
	 *
	 * @param parameterMap - parameter map from web request
	 * @return this instance
	 */
	private void bind(@Nonnull Map<String, String[]> parameterMap) {
		// bean property must be populated at this point
		Preconditions.checkNotNull(bean, "bean property must be populated at this point");
		FieldError error;
		// Get all fields of this bean
		Class<?> clazz = bean.getClass();
		// Find FormKey class
		Class<?> formKeyClazz = null;
		for (Class c : clazz.getClasses()) {
			if (c.getSimpleName().equals("FormKey")) {
				formKeyClazz = c;
				break;
			}
		}
		/* Get declared fields from FormKey class - only bind those fields,
		 * this is to prevent malicious user to add something harmful.
		 * If FormKey class not declared - bind to all fields
		 */
		Field[] fields;
		if (formKeyClazz != null) {
			LOGGER.info("Found FormKey class -> " + formKeyClazz.getName());
			// Get declared fields from FormKey class
			fields = formKeyClazz.getDeclaredFields();
		}
		else {
			fields = clazz.getDeclaredFields();
			// Get all fields in the object hierarchy
			fields = getFields(clazz, fields);
			LOGGER.warn("The FormKey inner class is not defined in your bean, all fields will"
					+ " be binded from web form, please declare fields in FormKey to restrict!");

		}

		// bind parameter to each matching field
		for (int i=0; i<fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			// Get field by name b/c FormKey is not the bean class
			try {
				field = clazz.getDeclaredField(fieldName);
				String[] stringValues = parameterMap.get(fieldName);
				if (stringValues != null) {
					// found a match - bind to the field
					error = FieldBinder.bindToField(bean, field, stringValues);
					// add error
					if (error != null) {
						bean.addError(error);
					}
				}
			}
			catch (NoSuchFieldException | SecurityException e) {
				// Log warning message
				LOGGER.warn("The field -" + fieldName + "- does not exist or not accessable in bean -"
						+ clazz.getSimpleName() + "-");
			}
		}
	}

	/**
	 * Get all fields of object hierarchy - sub class of AbstractValidationBean
	 */
	private Field[] getFields(Class<?> clazz, Field[] fields) {
		clazz = clazz.getSuperclass();
		if (clazz.getSimpleName().equals("AbstractValidationBean")) {
			// return the array of fields
			return fields;
		}
		else {
			// Use Guava to concatenate two arrays
			fields = ObjectArrays.concat(fields, clazz.getDeclaredFields(), Field.class);
			// recursive call
			return getFields(clazz, fields);
		}
	}

	/**
	 * Call from bean to bind all fields
	 * This method will create and bind bean instance
	 * @return populated bean
	 */
	public AbstractValidationBean bind() {

		// Pre bind process - create bean instance and populate it with default value
		bean = preBind();
		// Build request parameters map
		Map<String, String[]> parameterMap = buildParameterMap();
		// Bind fields in the bean
		bind(parameterMap);
		// Return the binded bean after post bind process
		return postBind(bean);

	}

	/**
	 * Override this method to put pre bind logic like populate bean with
	 * default values from data source...
	 * Make sure to add form key map, if this bean will be used in the Web context
	 * @return Pre Populated bean
	 */
	protected abstract AbstractValidationBean preBind();

	/**
	 * Override this method to build parameter map, or get it from HTTP request
	 * @return parameter map
	 */
	protected abstract Map<String, String[]> buildParameterMap();

	/**
	 * Override this method to put customized post bind process logic
	 * @param bean need to be processed
	 * @return binded bean
	 */
	protected abstract AbstractValidationBean postBind(AbstractValidationBean bean);

	/**
	 * The format of parameter map keys is {BeanName}.{FieldName} by default.
	 * The keys also could be customized by FormKey class.
	 *
	 * This method build field/value pair for the given bean.
	 *
	 * @param parameterMap
	 * @param beanName
	 * @return converted parameter map
	 */
	protected Map<String, String[]> populateThisBeanToParameterMap(Map<String, String[]> parameterMap) {
		LOGGER.info("Parameter map before convert -> " + printParameterMap(parameterMap));
		// bean property must be populated at this point
		Preconditions.checkNotNull(bean, "bean property must be populated at this point");
		// MAP keys (something.propertyName) match bean's properties name (propertyName)
		// Make a copy of parameter map and modify - parameterMap is locked
		Map<String, String[]> parameterMap2 = Maps.newHashMap(parameterMap);
		// Get key set of given parameter map - use parameterMap to avoid ConcurrentModificationException
		Set<String> keys = parameterMap.keySet();
		// Deal with default (format beanName.fieldName) keys
		// match {beanName}. pattern to find parameters for given bean
		Pattern KEYP = Pattern.compile(".*" + bean.getBeanName() + "\\..*");
		// Get the last token of a string delimited by something like "."
		StringBuffer buff;
		String propertyName;
		// Deal with customized (need to be defined in FormKey nested class) key
		Class<?> formKeyClass = bean.formKeyClass;
		Field[] fields = null;
		if (formKeyClass == null) {
			// There is no FormKey class defined in the bean
			LOGGER.warn("There is no nested class FormKey defined in -" + bean.getClass().getSimpleName() +
					"- The binding framework rely on the FormKey to find the matches between bean properties " +
					"and customized form key. If you use your own string in form keys, PLEASE DEFINE THEM IN FormKey class!");
		}
		else {
			// Get all fields defined in FormKey class
			fields = formKeyClass.getFields();
		}
		// build field/value pair for the given bean and add them to the map.
		for (String key : keys) {
			if (KEYP.matcher(key).matches()) {
				// Deal with default (format beanName.fieldName) keys
				buff = new StringBuffer(key);
				propertyName = buff.substring(buff.lastIndexOf(".") + 1);
				// Add matching property to the map
				parameterMap2.put(propertyName, parameterMap.get(key));
			}
			else {
				// Deal with customized (need to be defined in FormKey nested class) key
				if (formKeyClass != null) {
					for (int i=0; i<fields.length; i++) {
						Field field = fields[i];
						try {
							if (field.get(bean.formKeyInstance).equals(key)) {
								// Add matched key to parameter map
								parameterMap2.put(field.getName(), parameterMap.get(key));
							}
						}
						catch (IllegalArgumentException | IllegalAccessException e) {
							// Error happened when retrieve key from FormKey
							LOGGER.info("Error happened when retrieve key from FormKey");
							e.printStackTrace();
						}
					}
				}
			}
		}
		// Log parameter map info
		LOGGER.info(bean.getClass().getSimpleName() + " Converted parameter map -> " + printParameterMap(parameterMap2));

		// Return converted MAP
		return parameterMap2;
	}

	private String printParameterMap(Map<String, String[]> parameterMap) {
		StringBuilder builder = new StringBuilder("\n{ \n");
		String[] values;
		Set<String> keys = parameterMap.keySet();
		for (String key : keys) {
			builder.append("\t").append(key).append("=[");
			values = parameterMap.get(key);
			int length = values.length;
			if (values != null && length != 0) {
				for (int i=0; i<length-1; i++) {
					builder.append(values[i]).append(", ");
				}
				// Print the last value
				builder.append(values[length-1]).append("]\n");
			}
		}
		// Close bracket
		builder.append("}\n");
		// Return formmated string
		return builder.toString();
	}
}
