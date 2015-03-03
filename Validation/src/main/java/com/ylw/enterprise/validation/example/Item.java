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

import org.pojomatic.annotations.AutoProperty;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;

/**
 *
 */
@AutoProperty
public class Item extends AbstractValidationBean {
	private String stringField;
	private int intField;
	
	public Item() {
		super();
	}
	
	/**
	 * Construct bean for web form
	 */
	public Item(String beanName) {
		// Specify form bean name and build form key map
		super(beanName);
	}
	
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	public int getIntField() {
		return intField;
	}
	public void setIntField(int intField) {
		this.intField = intField;
	}
	
	/* ------------------Builder--------------------- */
	public static class Builder {
		private Item item;
		
		public Builder() {
			this.item = new Item();
		}
		
		public static Builder defaults() {
			return new Builder();
		}
		
		/**
		 * Construct builder for form bean
		 *
		 * @param beanName
		 */
		public Builder(String beanName) {
			this.item = new Item(beanName);
		}
				
		/**
		 * @param beanName
		 * @return Builder with formKey build in this bean
		 */
		public static Builder formKeyValues(String beanName) {
			return new Builder(beanName);
		}

		/**
		 * Build the bean instance
		 *
		 * @return bean instance
		 */
		public Item build() {
			return item;
		}

		public Builder withStringField(String stringField) {
			item.setStringField(stringField);
			return this;
		}

		public Builder withIntField(int intField) {
			item.setIntField(intField);
			return this;
		}
	}
	
	/* 
	 * @see com.ylw.enterprise.validation.bean.AbstractValidationBean#validate()
	 */
	@Override
	public AbstractValidationBean validate() {
		validate("stringField", stringField, onRule().required(true).nonBlank(true).build(), MyErrorCode.EMPTY_STRING);
		validate("intField", intField, onRule().required(true).positiveNumber(true).build(), MyErrorCode.INVALID_NUMBER);
		// return this bean
		return this;
	}

}
