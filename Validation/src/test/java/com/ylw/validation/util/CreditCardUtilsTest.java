package com.ylw.validation.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ylw.validation.util.CreditCardUtils;

public class CreditCardUtilsTest {
	private static final Logger LOGGER = Logger.getLogger(CreditCardUtilsTest.class);

	@Test
	public void testRemoveNonDigit() {
		String cardNumber = "4111-1111-1111-1111";
		String digitsCardNumber = CreditCardUtils.removeNonDigit(cardNumber);
		LOGGER.info("All Digits Credit Card Number -> " + digitsCardNumber);
		assertTrue("Credit card number should be all digits after remove non-digits", CreditCardUtils.isAllDigits(digitsCardNumber));
		assertTrue("Credit card number should be non masked", CreditCardUtils.isNonMasked(digitsCardNumber));

		// Test remove space and dash
		digitsCardNumber = CreditCardUtils.removeSpaceDash(cardNumber);
		LOGGER.info("All Digits Credit Card Number -> " + digitsCardNumber);
		assertTrue("Credit card number should be all digits after remove space and dash", CreditCardUtils.isAllDigits(digitsCardNumber));
		assertTrue("Credit card number should be non masked", CreditCardUtils.isNonMasked(digitsCardNumber));
		// Verify credit card type
		assertTrue("It is a Visa card", CreditCardUtils.isVisa(digitsCardNumber));
		assertFalse("It is not a American Express card", CreditCardUtils.isAmericanExpress(digitsCardNumber));
		assertFalse("It is not a Discovery card", CreditCardUtils.isDiscover(digitsCardNumber));
		assertFalse("It is not a Master card", CreditCardUtils.isMasterCard(digitsCardNumber));
		
		// Test masked credit card number
		cardNumber = "************1111";
		digitsCardNumber = CreditCardUtils.removeNonDigit(cardNumber);
		LOGGER.info("All Digits Credit Card Number -> " + digitsCardNumber);
		assertTrue("Credit card number should be all digits after remove non-digits", CreditCardUtils.isAllDigits(digitsCardNumber));
		assertFalse("Credit card number should be masked", CreditCardUtils.isNonMasked(digitsCardNumber));

	}

}
