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
package com.ylw.enterprise.validation.example;

import java.util.Map;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.binder.AbstractBeanBinder;

public class MyBeanBinder extends AbstractBeanBinder {
	private Map<String, String[]> parameterMap;
	
	public MyBeanBinder(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	/**
	 * @see com.ylw.enterprise.validation.binder.AbstractBeanBinder#preBind()
	 */
	@Override
	protected AbstractValidationBean preBind() {
		// Populate MyBean with default values
		return MyBean.Builder.defaultValues().build();
	}

	/* 
	 * @see com.ylw.enterprise.validation.binder.AbstractBeanBinder#buildParameterMap()
	 */
	@Override
	protected Map<String, String[]> buildParameterMap() {
		// Return HTTP request parameter map
		return parameterMap;
	}

	/* (non-Javadoc)
	 * @see com.ylw.enterprise.validation.binder.AbstractBeanBinder#postBind(com.ylw.enterprise.validation.bean.AbstractValidationBean)
	 */
	@Override
	protected AbstractValidationBean postBind(AbstractValidationBean bean) {
		// Post bind process
		MyBean myBean = (MyBean)bean;
		myBean.setErrorMessage("Bind this field in post bind");
		return myBean;
	}

}
