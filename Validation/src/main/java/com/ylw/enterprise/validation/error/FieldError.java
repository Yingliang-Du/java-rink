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

import javax.annotation.Nonnull;

import org.pojomatic.annotations.AutoProperty;

/**
 * Java representation of error message for each field in domain object.
 * 
 */
@AutoProperty
public class FieldError extends BeanError {
	//	private FieldErrorCode code;
	private String fieldName;
	
	/* ---------------Constructors-------------- */
	/**
	 * Construct error base on customized error message
	 * @param ignorable TODO
	 */
	public FieldError(String fieldName, String errorMessage, boolean ignorable) {
		super(errorMessage, ignorable);
		this.fieldName = fieldName;
	}
	
	/**
	 * Construct error base on FieldErrorCode message
	 * @param fieldName
	 * @param code - FieldErrorCode
	 * @param ignorable TODO
	 */
	public FieldError(@Nonnull String fieldName, @Nonnull FieldErrorCode code, boolean ignorable) {
		this(fieldName, buildMessage(fieldName, code), ignorable);
	}
	
	/**
	 * Construct error base on FieldErrorCode and extra error message
	 * @param fieldName
	 * @param code - FieldErrorCode
	 * @param message
	 * @param ignorable TODO
	 */
	public FieldError(@Nonnull String fieldName, @Nonnull FieldErrorCode code, @Nonnull String message, boolean ignorable) {
		this(fieldName, buildMessage(fieldName, code, message), ignorable);
	}

	/* ---------------Getting/Setting methods-------------- */
//	public FieldErrorCode getCode() {
//		return code;
//	}

	public String getFieldName() {
		return fieldName;
	}

	/* -------------------Utility Methods----------------------- */
	private static String buildMessage(String fieldName, FieldErrorCode code) {
		// Build message
		StringBuilder builder = new StringBuilder().append("The ").append(fieldName).append(" ")
				.append(code.getMessage());

		// Return message string
		return builder.toString();
	}
	
	private static String buildMessage(String fieldName, FieldErrorCode code, String message) {
		// Build message
		StringBuilder builder = new StringBuilder().append("The ").append(fieldName).append(" ")
				.append(code.getMessage()).append(" ").append(message);

		// Return message string
		return builder.toString();
	}
}
