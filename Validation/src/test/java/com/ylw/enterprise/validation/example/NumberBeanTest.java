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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ylw.enterprise.validation.error.BeanError;

/**
 *
 */
public class NumberBeanTest {
	private static final Logger LOGGER = Logger.getLogger(NumberBeanTest.class);

	NumberBean numberBean;
	Set<? extends BeanError> errors;

	@Before
	public void initInstance() {
		// Create numberBean instance
		numberBean = new NumberBean();
	}

	@Test
	public void testValidateByteField() {
		// Test validate Byte field
		numberBean.setByteField((byte) 4);
		LOGGER.info("Byte field value -> " + numberBean.getByteField());
		numberBean.clearErrors().validate();
		assertTrue("The Byte field value should be greater than the minimum", numberBean.hasError());
		numberBean.setByteField((byte) 6);
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateShortField() {
		// Test validate Short field
		numberBean.setShortField((short) 4);
		LOGGER.info("Short field value -> " + numberBean.getShortField());
		numberBean.clearErrors().validate();
		assertTrue("The Short field value should be greater than the minimum", numberBean.hasError());
		numberBean.setShortField((short) 6);
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateIntegerField() {
		// Test validate integer field
		numberBean.setIntegerField(4);
		LOGGER.info("integer field value -> " + numberBean.getIntegerField());
		numberBean.clearErrors().validate();
		assertTrue("The integer field value should be greater than the minimum", numberBean.hasError());
		numberBean.setIntegerField(6);
		numberBean.clearErrors().validate();
		assertFalse("The integer field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateLongField() {
		// Test validate Long field
		numberBean.setLongField(4l);
		LOGGER.info("long field value -> " + numberBean.getLongField());
		numberBean.clearErrors().validate();
		assertTrue("The long field value should be greater than the minimum", numberBean.hasError());
		numberBean.setLongField(6l);
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateFloatField() {
		// Test validate Float field
		numberBean.setFloatField(4f);
		LOGGER.info("Float field value -> " + numberBean.getFloatField());
		LOGGER.info("Float field int value -> " + numberBean.getFloatField().intValue());
		numberBean.clearErrors().validate();
		assertTrue("The Float field value should be greater than the minimum", numberBean.hasError());
		numberBean.setFloatField(6f);
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateDoubleField() {
		// Test validate Double field
		numberBean.setDoubleField(4d);
		LOGGER.info("Double field value -> " + numberBean.getDoubleField());
		LOGGER.info("Double field int value -> " + numberBean.getDoubleField().intValue());
		numberBean.clearErrors().validate();
		assertTrue("The Double field value should be greater than the minimum", numberBean.hasError());
		numberBean.setDoubleField(6d);
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateBigIntegerField() {
		// Test validate BigInteger field
		numberBean.setBigIntegerField(new BigInteger("4"));
		LOGGER.info("BigInteger field value -> " + numberBean.getBigIntegerField());
		LOGGER.info("BigInteger field int value -> " + numberBean.getBigIntegerField().intValue());
		numberBean.clearErrors().validate();
		assertTrue("The BigInteger field value should be greater than the minimum", numberBean.hasError());
		numberBean.setBigIntegerField(new BigInteger("6"));
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

	@Test
	public void testValidateBigDecimalField() {
		// Test validate BigDecimal field
		numberBean.setBigDecimalField(new BigDecimal("4.44"));
		LOGGER.info("BigDecimal field value -> " + numberBean.getBigDecimalField());
		LOGGER.info("BigDecimal field int value -> " + numberBean.getBigDecimalField().intValue());
		numberBean.clearErrors().validate();
		assertTrue("The BigDecimal field value should be greater than the minimum", numberBean.hasError());
		numberBean.setBigDecimalField(new BigDecimal("6.66"));
		numberBean.clearErrors().validate();
		assertFalse("The field value is greater than the minimum", numberBean.hasError());
	}

}
