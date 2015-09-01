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
package com.ylw.validation.util;

import java.util.regex.Pattern;

/**
 *
 */
public class CreditCardUtils {

	private static String NON_DIGIT = "[^0-9]+";
	private static String SPACE_DASH = "[ -]+";
	private static Pattern ALL_DIGITS = Pattern.compile("\\d*");
	// All credit card numbers have move than 12 digits
	private static Pattern NON_MASKED = Pattern.compile("[0-9]{12}[0-9]+");
	// All Visa card numbers start with a 4. New cards have 16 digits. Old cards have 13.
	private static Pattern VISA = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");
	// All MasterCard numbers start with the numbers 51 through 55. All have 16 digits.
	private static Pattern MASTER_CARD = Pattern.compile("^5[1-5][0-9]{14}$");
	// American Express card numbers start with 34 or 37 and have 15 digits.
	private static Pattern AMERICAN_EXPRESS = Pattern.compile("^3[47][0-9]{13}$");
	// Diners Club card numbers begin with 300 through 305, 36 or 38. All have 14 digits.
	private static Pattern DINERS_CLUB = Pattern.compile(" ^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
	// Discover card numbers begin with 6011 or 65. All have 16 digits.
	private static Pattern DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$");
	// JCB cards beginning with 2131 or 1800 have 15 digits. JCB cards beginning with 35 have 16 digits.
	private static Pattern JCB = Pattern.compile("^(?:2131|1800|35\\d{3})\\d{11}$");

	public static String removeNonDigit(String cardNumber) {
		return cardNumber.replaceAll(NON_DIGIT, "");
	}

	public static String removeSpaceDash(String cardNumber) {
		return cardNumber.replaceAll(SPACE_DASH, "");
	}

	public static boolean isAllDigits(String cardNumber) {
		return ALL_DIGITS.matcher(cardNumber).matches();
	}

	public static boolean isNonMasked(String cardNumber) {
		return NON_MASKED.matcher(cardNumber).matches();
	}

	public static boolean isVisa(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return VISA.matcher(cardNumber).matches();
	}

	public static boolean isMasterCard(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return MASTER_CARD.matcher(cardNumber).matches();
	}

	public static boolean isAmericanExpress(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return AMERICAN_EXPRESS.matcher(cardNumber).matches();
	}

	public static boolean isDiscover(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return DISCOVER.matcher(cardNumber).matches();
	}

	public static boolean isDinersClub(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return DINERS_CLUB.matcher(cardNumber).matches();
	}

	public static boolean isJCB(String cardNumber) {
		// Remove dash or space (allowed in credit card number)
		removeSpaceDash(cardNumber);
		// Verify if it is Visa card
		return JCB.matcher(cardNumber).matches();
	}
}
