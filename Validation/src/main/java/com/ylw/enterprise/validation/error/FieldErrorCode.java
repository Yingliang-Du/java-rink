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

/**
 * Constant code to categorize field error messages.
 * Project using this validation framework need to create another Enum to categorize error messages in the project.
 *
 *
 */
public enum FieldErrorCode implements ErrorMessage {
	//-----Exception and runtime Error Message-----
	EXCEPTION("Exception", "field got exception:"),

	RUNTIME("Runtime", "field got runtime error:"),

	//-----Field Validation Error Message-----
	FIELD_NON_NULL("FieldNonNull", "field is required and should not be null."),

	FIELD_NON_BLANK("FieldNonBlank", "field should not be blank."),

	FIELD_POSITIVE_NUMBER("FieldPositiveNumber", "field should be a positive number."),

	FIELD_MIN("FieldMin", "field should be greater than the minimum allowed"),

	//-----Field Binding Error Message-----
	NON_INTEGER("NonInteger", "field should be an Integer."),
	
	NON_LONG("NonLong", "field should be a Long."),

	NON_FLOAT("NonFloat", "field should be a Float."),

	NON_DATE("NonDate", "field is not a valid Date."),
	
	NON_CREDIT_CARD("NonCreditCard", "field is not a valid Credit Card Number.");

	private String id;
	private String message;

	FieldErrorCode(String id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
