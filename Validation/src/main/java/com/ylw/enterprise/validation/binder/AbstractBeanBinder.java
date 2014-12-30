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
	private AbstractValidationBean bind(AbstractValidationBean bean, @Nonnull Map<String, String[]> parameterMap) {
		FieldError error;
		// get all fields of this bean
		Field[] fields = bean.getClass().getDeclaredFields();
		// bind parameter to each matching field
		for (int i=0; i<fields.length; i++) {
			Field field = fields[i];
			String[] stringValues = parameterMap.get(field.getName());
			if (stringValues != null) {
				// found a match - bind to the field
				error = FieldBinder.bindToField(bean, field, stringValues);
				// add error
				if (error != null) {
					bean.addError(error);
				}
			}
		}
		
		// return this bean
		return bean;
	}
	
	/**
	 * Call from bean to bind all fields
	 * @param bean
	 * @return populated bean
	 */
	public AbstractValidationBean bind() {
	
		// Pre bind process - get bean instance populated with default value
		AbstractValidationBean bindBean = preBind();
		// Build request parameters map
		Map<String, String[]> parameterMap = buildParameterMap();
		// Bind fields
		bindBean = bind(bindBean, parameterMap);
		// Return the binded bean after post bind process
		return postBind(bindBean);
		
	}
	
	/**
	 * Override this method to put pre bind logic like populate bean with 
	 * default values from data source... 
	 * @return Pre populated bean
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
}
