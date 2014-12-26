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

import java.util.Set;

import org.apache.log4j.Logger;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.BaseValidationError;

public class MyBean extends AbstractValidationBean {
	private static final Logger LOGGER = Logger.getLogger(MyBean.class);

	private String stringField;
	private int intField;
	private Integer integerField;
	private String creditCardNumber;
	private String email;
	private String url;
	// Simulate runtime error message
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	
	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public String getCardNumber() {
		return creditCardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.creditCardNumber = cardNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/** ------------------Validation-------------------- */

	/**
	 * Validate on fields need to be validated 
	 */
	@Override
	public void validate() {		
		// Specify validation rule and validate each field that need to be validated
		validate("stringField", stringField, onRule().withNonNull(true).withNonBlank(true).build());
		validate("intField", intField, onRule().withPositiveNumber(true).build());
		validate("email", email, onRule().withEmail(true).build());	
	}


}
