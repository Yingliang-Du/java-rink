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

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 */
public class ItemTest {
	private static final Logger LOGGER = Logger.getLogger(ItemTest.class);

	/**
	 * Test add error instance
	 */
	@Test
	public void testValidation() {
		Item item = Item.Builder.defaults()
				.withIntField(-1)
				.withStringField("  ")
				.build();
		
		item.validate();
		LOGGER.info("Validation Errors -> " + item.getErrors());
		assertTrue("Got validation errors", item.hasError());
		// Verify error instance
		for (Object error : item.getErrors()) {
			LOGGER.info("Error instance -> " + error.getClass().getSimpleName());
			assertTrue("Error instance not right", error instanceof MyErrorCode);
		}
	}

}
