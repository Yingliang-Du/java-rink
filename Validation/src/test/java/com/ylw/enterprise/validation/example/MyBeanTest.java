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

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ylw.enterprise.validation.error.BeanError;
import com.ylw.enterprise.validation.error.FieldError;

/**
 * Unit test for MyBean example.
 */
public class MyBeanTest {
	private static final Logger LOGGER = Logger.getLogger(MyBeanTest.class);

	MyBean myBean;
	Set<? extends BeanError> errors;

	@Before
	public void initInstance() {
		// Create myBean instance and populate initial state of the bean
		myBean = MyBean.Builder.defaultValues().withIntField(7).withIntegerField(7).withCreditCard("4111111111111111")
				.withEmail("email").withStringField("stringField").withUrl("url").build();

		// Validate initial state of the bean
		myBean.clearErrors().validate();
		LOGGER.info("Initial state errors in myBean -> " + myBean.getErrors());
		assertFalse("There should be no error to start with", myBean.hasError());
	}

	@Test
	public void testValidate() {
		// Test stringField is a required field should not be null
		myBean.setStringField(null);
		myBean.setIntField(-1);
		// Validate
		myBean.clearErrors().validate();
		// verify object errors
		assertTrue("There should be errors in myBean", myBean.hasError());
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		// verify field errors - stringField
		LOGGER.info("Field error map -> " + myBean.getFieldErrorMap());
		errors = myBean.getFieldErrorMap().get("stringField");
		LOGGER.info("stringField errors -> " + errors);
		assertNotNull("The stringfield error should populated", errors);
		assertFalse("Errors for stringField should not empty", errors.isEmpty());
		// verify field errors - intField
		errors = myBean.getFieldErrorMap().get("intField");
		LOGGER.info("intField errors -> " + errors);
		assertNotNull("The intField error should populated", errors);
		assertFalse("Errors for stringField should not empty", errors.isEmpty());

		// Test string field should not be blank
		myBean.setStringField("    ");
		myBean.setIntField(8);
		myBean.clearErrors().validate();
		// verify object errors
		assertTrue("There should be errors in myBean", myBean.hasError());
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		// verify field errors - stringField
		LOGGER.info("Field error map -> " + myBean.getFieldErrorMap());
		errors = myBean.getFieldErrorMap().get("stringField");
		assertNotNull("The stringfield error should populated", errors);
		assertFalse("Errors for stringField should not empty", errors.isEmpty());
		LOGGER.info("stringField errors -> " + errors);
		// verify field errors - intField
		errors = myBean.getFieldErrorMap().get("intField");
		LOGGER.info("intField errors -> " + errors);
		assertNull("The intField error should be null", errors);
	}

	@Test
	public void testValidateDate() throws ParseException {
		// Validate non-valid Date
		SimpleDateFormat sdf = new SimpleDateFormat(myBean.getDateFormat());
		myBean.setExpirDate(sdf.parse("10/30/2014"));
		myBean.clearErrors().validate();
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		assertTrue("There should be error due to min date violation", myBean.hasError());

		// Validate valid Date
		myBean.setExpirDate(sdf.parse("10/30/2028"));
		myBean.clearErrors().validate();
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		assertFalse("There should be no error whothout min date violation", myBean.hasError());
	}

	@Test
	public void testValidateCreditCard() {
		// test invalid credit card
		myBean.setCreditCardNumber("InvalidCreditCardNumber");
		myBean.clearErrors().validate();
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		assertTrue("The credit card number is invalid", myBean.hasError());
	}

	@Test
	public void testValidateIntegerRange() {
		// test integer not in range
		myBean.setIntegerField(8);
		myBean.clearErrors().validate();
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		assertTrue("The integer is not in the given range", myBean.hasError());
	}

	@Test
	public void testValidateStringRange() {
		// test string not in range
		myBean.setStringRange("8");
		myBean.clearErrors().validate();
		LOGGER.info("Errors in myBean -> " + myBean.getErrors());
		assertTrue("The string is not in the given range", myBean.hasError());
	}

	@Test
	public void testToJson() {
		myBean.toJson();
	}

	@Test
	public void testBuildFormKey() {
		myBean.buildFormKeyMap();
	}

	@Test
	public void testBuilderFormKey() {
		String beanName = "BillingAddress";
		MyBean bean = MyBean.Builder.formKeyValues(beanName).withStringField("This bean contains form key").build();
		LOGGER.info("The bean contains formKey -> " + bean);
		assertNotNull("The bean should not be null", bean);
		assertFalse("The formKeys in the bean should not be empty", bean.getFormKeyMap().isEmpty());

		// Test clone exist bean and add formKey map to it
		bean = MyBean.Builder.defaultValues().withStringField("This bean need to add formKey").build();
		LOGGER.info("Bean with no formKey -> " + bean);
		assertNotNull("The bean should not be null", bean);
		assertTrue("The formKeys in the bean should be empty", bean.getFormKeyMap().isEmpty());
		// Clone and add form key map
		bean = MyBean.Builder.formKeyValues(beanName, bean).withStringRange("The form key map had been added").build();
		LOGGER.info("Bean with formKey added -> " + bean);
		assertNotNull("The bean should not be null", bean);
		assertFalse("The formKeys in the bean should not be empty", bean.getFormKeyMap().isEmpty());
	}

	@Test
	public void testTreeMap() {
		// Creating tree map
		// TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
		HashMap<Integer, String> treemap = new HashMap<Integer, String>();

		// Populating tree map
		treemap.put(2, "two");
		treemap.put(1, "one");
		treemap.put(3, "three");
		treemap.put(6, "six");
		treemap.put(5, "five");

		// Putting value at key 3
		System.out.println("Value before modification: " + treemap);
		System.out.println("Value returned: " + treemap.put(3, "TP"));
		System.out.println("Value after modification: " + treemap);
	}

	@Test
	public void testBind() {
		// Prepare parameter map
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		String[] stringField = { "4bb" };
		String[] intField = { "8" };
		String[] integerField = { "77" };

		parameterMap.put("stringField", stringField);
		parameterMap.put("intField", intField);
		parameterMap.put("integerField", integerField);

		// bind bean
		myBean.bind(parameterMap);

		errors = myBean.getErrors();

		LOGGER.debug("Errors - " + errors);
		LOGGER.debug("myBean.stringField - " + myBean.getStringField());
		LOGGER.debug("myBean.intField - " + myBean.getIntField());
		LOGGER.debug("myBean.integerField - " + myBean.getIntegerField());
		// assertFalse("Errors in myBean should not empty.", errors.isEmpty());
	}
}
