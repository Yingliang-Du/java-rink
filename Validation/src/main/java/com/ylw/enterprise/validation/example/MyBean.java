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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableSet;
import com.ylw.enterprise.validation.bean.AbstractPojomaticBean;
import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.FieldError;

@AutoProperty
public class MyBean extends AbstractPojomaticBean {
	private static final Logger LOGGER = Logger.getLogger(MyBean.class);

	/**
	 * Default constructor
	 */
	public MyBean() {
	}

	/**
	 * Construct bean for web form
	 */
	public MyBean(String beanName) {
		// Specify form bean name and build form key map
		this.setBeanName(beanName);
		this.buildFormKey();
	}

	/* ------------------Properties------------------ */
	private String stringField;
	private String stringRange;
	private int intField;
	private Integer integerField;
	private Date expirDate;
	private String creditCardNumber;
	private String email;
	private String url;
	private String builder;	// can i have the same name as the nested class

	/* ------------------Getting/Setting------------------- */
	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public String getStringRange() {
		return stringRange;
	}

	public void setStringRange(String stringRange) {
		this.stringRange = stringRange;
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

	public String getBuilder() {
		return builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	/* -----------------Utilities------------------- */
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


	/* ------------------Builder--------------------- */
	public static class Builder {
		private MyBean myBean;

		public Builder() {
			this.myBean = new MyBean();
		}

		/**
		 * Construct builder for form bean
		 * @param beanName
		 */
		public Builder(String beanName) {
			this.myBean = new MyBean(beanName);
		}

		/**
		 *
		 * @return Builder start with default values
		 */
		public static Builder defaultValues() {
			return new Builder();
		}

		/**
		 * @param beanName
		 * @return Builder with formKey build in this bean
		 */
		public static Builder formKeyValues(String beanName) {
			return new Builder(beanName);
		}

		/**
	     * Clone the bean without formKey map and add the formKey map to it
	     * @param beanName
	     * @param address - bean without formKey
	     * @return Builder with formKey build in the bean
	     */
	    public static Builder formKeyValues(String beanName, MyBean bean) {
	    	return formKeyValues(beanName)
	    			.withStringField(bean.getStringField())
	    			.withCreditCard(bean.getCreditCardNumber())
	    			.withEmail(bean.getEmail())
	    			.withIntegerField(bean.getIntegerField())
	    			.withIntField(bean.getIntField())
	    			.withStringRange(bean.getStringRange())
	    			.withUrl(bean.getUrl());
	    }

		/**
		 * Build the bean instance
		 * @return bean instance
		 */
		public MyBean build() {
			return myBean;
		}

		public Builder withStringField(String stringField) {
			myBean.setStringField(stringField);
			return this;
		}

		public Builder withStringRange(String stringRange) {
			myBean.setStringRange(stringRange);
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

	/* -------------------Binding------------------- */
	/**
	 * Define form key will be used in the form and data binding logic
	 *
	 */
	public static class FormKey {
		public static final String builder = "form_key_builder";
		public static final String stringField = "customized_form_key_stringField";
	}

	/* ------------------Validation-------------------- */

	/**
	 * Validate on fields need to be validated
	 */
	@Override
	public MyBean validate() {
		Collection<?> range;
		// Specify validation rule and validate each field that need to be validated
		validate("stringField", stringField, onRule().withRequired(true).withNonBlank(true).build());
		range = ImmutableSet.of("5", "7", "9");
		validate("stringRange", stringRange, onRule().withIn(range).build(),
				"stringRange " + stringRange + " is not in range: " + Arrays.toString(range.toArray()));
		validate("intField", intField, onRule().withPositiveNumber(true).build());
		validate("integerField", integerField, onRule().withIn(ImmutableSet.of(5, 7, 9)).build(),
				"integerField " + integerField + " is not in range: " + Arrays.toString(range.toArray()));
		validate("expirDate", expirDate, onRule().withMin(new Date()).build(), MyErrorCode.EXPIRATION_DATE_TOO_EARLY);
		validate("email", email, onRule().withRequired(true).build(), "Customized error message for email field");
		validate("creditCardNumber", creditCardNumber, onRule().withCreditCard(true).build(),
				"Credit card number -" + creditCardNumber + "- is not valid");
		// Return this bean
		return this;
	}


}
