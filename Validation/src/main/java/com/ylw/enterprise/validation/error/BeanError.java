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
package com.ylw.enterprise.validation.error;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Project using this validation framework need to create an error class that extends this class
 * to represent and category errors for the project.
 *
 */
@AutoProperty
public class BeanError {

	private String message;
	private boolean ignorable;
	
	/**
	 * Must specify default constructor for sub class
	 */
	public BeanError() {
		super();
	}

	/**
	 * Default constructor for constructing error message
	 */
//	public BeanError(String message) {
//		this.message = message;
//		this.ignorable = false;
//	}
	
	public BeanError(String message, boolean ignorable) {
		this.message = message;
		this.ignorable = ignorable;
	}

	public String getMessage() {
		return message;
	}
	
	public boolean isIgnorable() {
		return ignorable;
	}
	
	/* ---------------Utility Methods-------------- */
	/**
	 * Build error message with error code
	 * @param error code
	 * @return error message
	 */
	protected static String buildMessage(ErrorMessage code) {
		return code.getMessage();
	}
	
	/**
	 * Build error message with error code and extra error message
	 * @param error code
	 * @param extraMessage
	 * @return error message
	 */
	protected static String buildMessage(ErrorMessage code, String extraMessage) {
		return new StringBuffer().append(code.getMessage()).append(" ").append(extraMessage).toString();
	}

	/* ---------------Override equals, toString and hashCode-------------- */
	@Override
	public boolean equals(Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

}