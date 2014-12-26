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
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Java representation of field error message.
 * Project using this validation framework need to create a class which extends this class
 * to represent errors for the project.
 *
 */
@AutoProperty
public class BaseValidationError {
	//	private FieldErrorCode code;
	private String fieldName;
	protected String message;
	
	// Default constructor for sub class
	protected BaseValidationError() {}

	public BaseValidationError(@Nonnull String fieldName, @Nonnull FieldErrorCode code) {
		this.fieldName = fieldName;
		this.message = buildMessage(fieldName, code);
	}
	
	public BaseValidationError(@Nonnull String fieldName, @Nonnull FieldErrorCode code, @Nonnull String message) {
		this.fieldName = fieldName;
		this.message = buildMessage(fieldName, code, message);
	}

//	public FieldErrorCode getCode() {
//		return code;
//	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}

	private String buildMessage(String fieldName, FieldErrorCode code) {
		// Build message
		StringBuilder builder = new StringBuilder().append("The ").append(fieldName).append(" ")
				.append(code.getMessage());

		// Return message string
		return builder.toString();
	}
	
	private String buildMessage(String fieldName, FieldErrorCode code, String message) {
		// Build message
		StringBuilder builder = new StringBuilder().append("The ").append(fieldName).append(" ")
				.append(code.getMessage()).append(" ").append(message);

		// Return message string
		return builder.toString();
	}

	// ---------------Override equals, toString and hashCode--------------
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
