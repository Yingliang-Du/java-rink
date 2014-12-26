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

import javax.annotation.Nonnull;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;

/**
 * This class need to be override to add customized binding logic
 *
 */
public abstract class AbstractBeanBinder {
	/**
	 * Override and put your binding logic
	 * @param bean
	 * @param parameterObject
	 */
	public abstract void bindToBean(@Nonnull AbstractValidationBean bean, @Nonnull Object parameterObject);
	
	protected void bindToField(AbstractValidationBean bean, String fieldName, String stringValue) {
		FieldBinder.bindToField(bean, fieldName, stringValue);
	}
}
