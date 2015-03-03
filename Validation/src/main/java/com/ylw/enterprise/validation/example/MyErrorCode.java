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

import com.ylw.enterprise.validation.error.ErrorMessage;

/**
 * Example ENUM for defining error message
 *
 */
public enum MyErrorCode implements ErrorMessage {
	BIRTH_DATE_TOO_LATE("BirthDateTooLate", "Birth Date can not be later than today!"),
	EXPIRATION_DATE_TOO_EARLY("ExpirationDateTooEarly", "Expiration Date can not be earlier than today!"),
	ZIP_NO_MATCH("ZipNoMatch", "You have input a zip code that not match the zip code provided, please check and make sure!"),
	INVALID_NUMBER("InvalidNumber", "The number is not valid"),
	EMPTY_STRING("EmptyString", "The string should not be empty");
	
	private String id;
	private String message;
	
	/**
	 * Construct error object
	 */
	private MyErrorCode(String id, String message) {
		this.id = id;
		this.message = message;
	}

	/*
	 * @see com.ylw.enterprise.validation.error.ErrorMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		// return message
		return message;
	}
}
