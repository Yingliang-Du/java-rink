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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.pojomatic.annotations.AutoProperty;

import com.ylw.enterprise.validation.bean.AbstractPojomaticBean;
import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.FieldError;

@AutoProperty
public class MyBean extends AbstractPojomaticBean {
	private static final Logger LOGGER = Logger.getLogger(MyBean.class);

	private String stringField;
	private int intField;
	private Integer integerField;
	private Date expirDate;
	private String creditCardNumber;
	private String email;
	private String url;

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

	public Date getExpirDate() {
		return expirDate;
	}

	public void setExpirDate(Date expirDate) {
		this.expirDate = expirDate;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
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
	
	/** -----------------Utilities------------------- */
	public String getExpirDateAsText() {
		if (expirDate != null) {
			// format date
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			return format.format(expirDate);
		}
		else {
			// return null if date is null
			return null;
		}
	}
	

	/** ------------------Builder--------------------- */
	public static class Builder {
		private MyBean myBean;

		public Builder() {
			this.myBean = new MyBean();
		}

		public static Builder defaultValues() {
			return new Builder();
		}

		public MyBean build() {
			return myBean;
		}

		public Builder withStringField(String stringField) {
			myBean.setStringField(stringField);
			return this;
		}

		public Builder withIntField(int intField) {
			myBean.setIntField(intField);
			return this;
		}

		public Builder withIntegerField(Integer integerField) {
			myBean.setIntegerField(integerField);
			return this;
		}

		public Builder withCreditCard(String creditCardNumber) {
			myBean.setCreditCardNumber(creditCardNumber);
			return this;
		}

		public Builder withEmail(String email) {
			myBean.setEmail(email);
			return this;
		}

		public Builder withUrl(String url) {
			myBean.setUrl(url);
			return this;
		}

	}

	/** ------------------Validation-------------------- */

	/**
	 * Validate on fields need to be validated
	 */
	@Override
	public MyBean validate() {
		// Specify validation rule and validate each field that need to be validated
		validate("stringField", stringField, onRule().withRequired(true).withNonBlank(true).build());
		validate("intField", intField, onRule().withPositiveNumber(true).build());
		validate("expirDate", expirDate, onRule().withMin(new Date()).build(), MyErrorCode.EXPIRATION_DATE_TOO_EARLY);
		validate("email", email, onRule().withRequired(true).build(), "Customized error message for email field");
		// Return this bean
		return this;
	}


}
