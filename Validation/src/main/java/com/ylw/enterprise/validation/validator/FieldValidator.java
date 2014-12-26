/**
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
package com.ylw.enterprise.validation.validator;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

import com.ylw.enterprise.validation.error.FieldErrorCode;


public class FieldValidator {
	private final static Logger LOGGER = Logger.getLogger(FieldValidator.class);
	
	private ValidationRule validationRule;
	
	public FieldValidator(@Nonnull ValidationRule validationRule) {
		this.validationRule = validationRule;
	}
	
	/**
	 * validate field validation rule
	 * @return violation rule indicate rule violated; null if there no rule violated
	 */
//	public ViolationRule validate(Object fieldValue) {
//				
//		// Check if the field is required and deal with NonNull rule
//		if (validationRule.isNonNull() && fieldValue == null) {
//			return ViolationRule.NONNULL;
//		}
//		
//		// Deal with NonBlank rule for string type field
//		if (fieldValue != null && validationRule.isNonBlank()) {
//			try {
//				String valueString = (String)fieldValue;
//				if (valueString.trim().equals("")) {
//					return ViolationRule.NONBLANK;
//				}
//			}
//			catch (ClassCastException e) {
//				LOGGER.error("The field is not an instance of String, so NonBlank rule can not apply.");
//				e.printStackTrace();
//			}
//		}
//		
//		// Deal with PositiveNumber rule
//		if (fieldValue != null && validationRule.isPositiveNumber()) {
//			LOGGER.info("the instance of fieldValue: " + fieldValue.getClass().getName());
//			if (fieldValue instanceof Number) {
//				Number vNum = (Number)fieldValue;
//				if (vNum.intValue() < 1) {
//					return ViolationRule.POSITIVENUMBER;
//				}
//			}
//			else {
//				LOGGER.error("The field is not an instance of Number, so PositiveNumber rule can not apply.");	
//			}
//		}
//		
//		// Return null if there is no rule violated
//		return null;
//	}
	
	public FieldErrorCode validate(Object fieldValue) {
		
		// Check if the field is required and deal with NonNull rule
		if (validationRule.isNonNull() && fieldValue == null) {
			return FieldErrorCode.FIELD_NON_NULL;
		}
		
		// Deal with NonBlank rule for string type field
		if (fieldValue != null && validationRule.isNonBlank()) {
			try {
				String valueString = (String)fieldValue;
				if (valueString.trim().equals("")) {
					return FieldErrorCode.FIELD_NON_BLANK;
				}
			}
			catch (ClassCastException e) {
				LOGGER.error("The field is not an instance of String, so NonBlank rule can not apply.");
				e.printStackTrace();
			}
		}
		
		// Deal with PositiveNumber rule
		if (fieldValue != null && validationRule.isPositiveNumber()) {
			LOGGER.info("the instance of fieldValue: " + fieldValue.getClass().getName());
			if (fieldValue instanceof Number) {
				Number vNum = (Number)fieldValue;
				if (vNum.intValue() < 1) {
					return FieldErrorCode.FIELD_POSITIVE_NUMBER;
				}
			}
			else {
				LOGGER.error("The field is not an instance of Number, so PositiveNumber rule can not apply.");	
			}
		}
		
		// Return null if there is no rule violated
		return null;
	}
}
