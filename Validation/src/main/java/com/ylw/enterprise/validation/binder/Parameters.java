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
package com.ylw.enterprise.validation.binder;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Utilities for Web Request Parameters
 */
public class Parameters {
private static final Logger LOGGER = Logger.getLogger(Parameters.class);
	
	private final Map<String, String[]> params;
	
	private String[] value;
	
	/**
	 * Construct with Http request parameter map
	 */
	public Parameters(Map<String, String[]> params) {
		// Construct with Http request parameter map
		this.params = params;
	}
	
	public boolean getBoolean(String key) {
		value = params.get(key);
		return value != null && value[0].equalsIgnoreCase("true");
	}

	/**
	 * Get a parameter value as an integer, providing a default if there is no value.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return the value as an integer, or {@code defaultValue} if it is not parsable as an integer
	 */
	public int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(get(key));
		}
		catch (NumberFormatException ex) {
			return defaultValue;
		}
	}
	
	public Integer getInteger(String key) {
		try {
			return Integer.valueOf(get(key));
		}
		catch (NumberFormatException ex) {
			return null;
		}
	}
	
	/**
	 * Determine if a checkbox was checked
	 * 
	 * @param key
	 * @return true if it was checked, false otherwise
	 */
	public boolean isCheckboxChecked(String key) {
		return "on".equals(get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return {@code true} if the given key maps to a non-empty value, and {@code false} otherwise.
	 */
	public boolean hasValue(String key) {
		String value = get(key);
		return value != null && value.length() > 0;
	}
	
	/**
	 * Get the value associated with a key, if there is one. If the key does not appear in the form
	 * parameters or the query string, return null. If it appears multiple times, the last occurrence
	 * is returned, and a message is logged at the {@code INFO} level to this class logger.
	 * 
	 * @param key
	 * @return the <em>last</em> value seen in the query string or form parameters, or null 
	 */
	public String get(String key) {
		String[] values = params.get(key);
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length > 1) {
			LOGGER.info("Multiple values found for key " + key);
		}
		return values[values.length - 1].trim();
	}

}
