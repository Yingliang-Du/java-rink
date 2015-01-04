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

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ylw.enterprise.validation.error.BeanError;

public class MyBeanBinderTest {
	private static final Logger LOGGER = Logger.getLogger(MyBeanBinderTest.class);

	private MyBeanBinder myBeanBinder;
	private MyBean myBean;
	private Set<? extends BeanError> errors;

	@Before
	public void initInstance() {
		// Prepare parameter map
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		String[] stringField = { "4bb" };
		String[] intField = { "8" };
		String[] integerField = { "77" };
		String[] expirDate = { "12/12/2014" };

		parameterMap.put("stringField", stringField);
		parameterMap.put("intField", intField);
		parameterMap.put("integerField", integerField);
		parameterMap.put("expirDate", expirDate);

		// Instantiate binder 
		myBeanBinder = new MyBeanBinder(parameterMap);
	}

	@Test
	public void testBind() {
		// Bind bean
		Object bean = myBeanBinder.bind();
		assertNotNull("The binded bean should not be null", bean);
		assertTrue("The thye of binded bean should be MyBean", bean instanceof MyBean);
		
		myBean = (MyBean)bean; 
		errors = myBean.getErrors();

		LOGGER.debug("MyBean.errors -> " + errors);
		LOGGER.debug("myBean.stringField - " + myBean.getStringField());
		LOGGER.debug("myBean.intField - " + myBean.getIntField());
		LOGGER.debug("myBean.integerField - " + myBean.getIntegerField());
		LOGGER.debug("MyBean -> " + myBean);
		// assertFalse("Errors in myBean should not empty.", errors.isEmpty());
	}

}
