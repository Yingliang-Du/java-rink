package com.ylw.enterprise.validation.common;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class CreditCardUtilsTest {
	private static final Logger LOGGER = Logger.getLogger(CreditCardUtilsTest.class);

	@Test
	public void testRemoveNonDigit() {
		String cardNumber = "4111-1111-1111-1111";
		String allDigits = CreditCardUtils.removeNonDigit(cardNumber);
		LOGGER.info("All Digits Credit Card Number -> " + allDigits);
		assertTrue("Credit card number should be all digits after remove non-digits", CreditCardUtils.isUnMasked(allDigits));

		// Test remove space and dash
		allDigits = CreditCardUtils.removeSpaceDash(cardNumber);
		LOGGER.info("All Digits Credit Card Number -> " + allDigits);
		assertTrue("Credit card number should be all digits after remove space and dash", CreditCardUtils.isUnMasked(allDigits));
	}

}
