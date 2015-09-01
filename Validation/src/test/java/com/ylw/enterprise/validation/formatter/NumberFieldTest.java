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
package com.ylw.enterprise.validation.formatter;

import static org.junit.Assert.*;

import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ylw.validation.util.NumberUtils;

/**
 *
 */
public class NumberFieldTest {
	private static final Logger LOGGER = Logger.getLogger(NumberFieldTest.class);

	@Test
	public void formatTest() {
		// Test default format
		String numberStr = NumberField.format(123456.789, "default");
		LOGGER.info("The default format of number 123456.789 -> " + numberStr);

		// Test integer format
		numberStr = NumberField.format(123456.789, "integer");
		LOGGER.info("The integer format of number 123456.789 -> " + numberStr);

		// Test percent format
		numberStr = NumberField.format(123456.789, "percent");
		LOGGER.info("The percent format of number 123456.789 -> " + numberStr);

		// Test currency format
		numberStr = NumberField.format(123456.789, "currency");
		LOGGER.info("The currency format of number 123456.789 -> " + numberStr);

		// Test user defined format
		numberStr = NumberField.format(123456.789, "#,##0.##");
		LOGGER.info("The user defined #,##0.## format of number 123456.789 -> " + numberStr);

		// Test user defined invalid number format
		numberStr = NumberField.format(123456.789, "Invalid.Format");
		LOGGER.info("The user defined Invalid.Format format of number 123456.789 -> " + numberStr);

		// Test null format
		numberStr = NumberField.format(123456.789, null);
		LOGGER.info("The null format of number 123456.789 -> " + numberStr);

		// Test null number value
		numberStr = NumberField.format(null, "default");
		LOGGER.info("The default format of number null -> " + numberStr);

	}

}
