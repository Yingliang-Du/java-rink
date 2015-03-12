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

/**
 *
 */
public enum ItemErrorCode {
	INVALID_NUMBER("InvalidNumber", "The number is not valid - lagecy code"),
	EMPTY_STRING("EmptyString", "The string should not be empty - lagecy code");
	
	private String id;
	private String message;
	
	/**
	 * Construct error object
	 */
	private ItemErrorCode(String id, String message) {
		this.id = id;
		this.message = message;
	}

	/*
	 * Get message
	 */
	public String getMessage() {
		return message;
	}

}
