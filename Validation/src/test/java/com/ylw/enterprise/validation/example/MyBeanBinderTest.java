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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;
import com.ylw.enterprise.validation.error.BeanError;

public class MyBeanBinderTest {
	private static final Logger LOGGER = Logger.getLogger(MyBeanBinderTest.class);

	private MyBeanBinder myBeanBinder;
	private MyBean myBean;
//	private Set<? extends BeanError> errors;
	// MAP keys (propertyName) match bean's properties name (propertyName)
	Map<String, String[]> parameterMap;
	// MAP keys (something.propertyName) match bean's properties name (propertyName)
	Map<String, String[]> parameterMap2;

	// Params values
	private String[] stringField = { "4bb 5cc 6dd" };
	String[] intField = { "8" };
	String[] integerField = { "77" };
	String[] expirDate = { "12/12/2014" };

	@Before
	public void initInstance() {
		// Prepare parameter MAPs

		// MAP keys (propertyName) match bean's properties name (propertyName)
		parameterMap = new HashMap<String, String[]>();
		parameterMap.put("stringField", stringField);
		parameterMap.put("intField", intField);
		parameterMap.put("integerField", integerField);
		parameterMap.put("expirDate", expirDate);

		// MAP keys (something.propertyName) match bean's properties name (propertyName)
		parameterMap2 = new HashMap<String, String[]>();
		parameterMap2.put("myBean.stringField", stringField);
		parameterMap2.put("myBean.intField", intField);
		parameterMap2.put("myBean.integerField", integerField);
		parameterMap2.put("myBean.expirDate", expirDate);

	}

	@Test
	public void testBindWhenParamsKeyMatchBeanProperty() {
		// Instantiate binder
		myBeanBinder = new MyBeanBinder(parameterMap);
		// Test success Bind
		Object bean = myBeanBinder.bind();
		assertNotNull("The binded bean should not be null", bean);
		assertTrue("The thye of binded bean should be MyBean", bean instanceof MyBean);

		myBean = (MyBean)bean;

		LOGGER.debug("MyBean.errors -> " + myBean.getErrors());
		assertEquals("myBean.stringField binded correctly", stringField[0], myBean.getStringField());
		assertEquals("myBean.intField binded correctly", intField[0], String.valueOf(myBean.getIntField()));
		assertEquals("myBean.integerField binded correctly", integerField[0], myBean.getIntegerField().toString());
		assertEquals("myBean.expirDate binded correctly", expirDate[0], myBean.getExpirDateAsText());
		LOGGER.debug("MyBean -> " + myBean);
		assertFalse("Bean bind success.", myBean.hasError());

		// Test non-success bind
		String[] intField = { "non-int" };
		parameterMap.put("intField", intField);
		bean = myBeanBinder.bind();
		myBean = (MyBean)bean;
		LOGGER.debug("MyBean.errors -> " + myBean.getErrors());
		LOGGER.debug("MyBean -> " + myBean);
		assertTrue("Bean bind not success - non-int to int field.", myBean.hasError());
	}

	@Test
	public void testBindWhenLastTokenOfParamsKeyMatchBeanProperty() {
		// Instantiate binder
		myBeanBinder = new MyBeanBinder(parameterMap2);
		// Bind bean
		Object bean = myBeanBinder.bind();
		assertNotNull("The binded bean should not be null", bean);
		assertTrue("The thye of binded bean should be MyBean", bean instanceof MyBean);

		myBean = (MyBean)bean;

		// Verify binding fields
		assertEquals("myBean.stringField binded correctly", stringField[0], myBean.getStringField());
		assertEquals("myBean.intField binded correctly", intField[0], String.valueOf(myBean.getIntField()));
		assertEquals("myBean.integerField binded correctly", integerField[0], myBean.getIntegerField().toString());
		assertEquals("myBean.expirDate binded correctly", expirDate[0], myBean.getExpirDateAsText());
		LOGGER.debug("MyBean.errors -> " + myBean.getErrors());
		LOGGER.debug("MyBean -> " + myBean);
		// Verify binding errors
		assertFalse("MyBean bind success.", myBean.hasError());
	}

	@Test
	public void testStringUtils() {
		// Test get the last token of a string delimited by something like "."
		String key = "myBean.integerField";
		StringBuffer buff = new StringBuffer(key);
		String propertyName = buff.substring(buff.lastIndexOf(".") + 1);
		LOGGER.info("Get last token of myBean.integerField -> " + propertyName);
		assertEquals("The last token of 'myBean.integerField' should be 'integerField'", "integerField", propertyName);
		// Test match the last second token
		Pattern KEYP = Pattern.compile(".*" + "myBean" + "\\..*");
		assertTrue("Found matching of the last second token", KEYP.matcher(key).matches());

		// What if there is no delimiter
		buff = new StringBuffer("integerField");
		key = buff.substring(buff.lastIndexOf(".") + 1);
		LOGGER.info("Get last token of integerField -> " + key);
		assertEquals("The last token of 'integerField' should be 'integerField'", "integerField", key);
	}
}
