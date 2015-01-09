package com.ylw.enterprise.validation.formatter;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ylw.enterprise.validation.common.DateUtils;

public class DateFieldTest {
	private static final Logger LOGGER = Logger.getLogger(DateFieldTest.class);

	@Test
	public void formatTest() {
		Date date = new Date();
		// Test short date format
		String dateStr = DateField.format(date, "short");
		LOGGER.info("The short date format -> " + dateStr);

		// Test medium date format
		dateStr = DateField.format(date, "medium");
		LOGGER.info("The medium date format -> " + dateStr);

		// Test long date format
		dateStr = DateField.format(date, "long");
		LOGGER.info("The long date format -> " + dateStr);

		// Test full date format
		dateStr = DateField.format(date, "full");
		LOGGER.info("The full date format -> " + dateStr);

		// Test user defined format - "MMyyyy"
		dateStr = DateField.format(date, "MMyyyy");
		LOGGER.info("The MMyyyy date format -> " + dateStr);

		// Test user defined format - "yyyyMM"
		dateStr = DateField.format(date, "yyyyMM");
		LOGGER.info("The yyyyMM date format -> " + dateStr);

		// Test invalid format - "InvalidFormat"
		dateStr = DateField.format(date, "InvalidFormat");
		LOGGER.info("The InvalidFormat date format -> " + dateStr);

		// Test null format - null
		dateStr = DateField.format(date, null);
		LOGGER.info("The null date format -> " + dateStr);

		// Test null date value
		dateStr = DateField.format(null, "short");
		LOGGER.info("The null date value -> " + dateStr);

	}

}
