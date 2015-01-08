package com.ylw.enterprise.validation.common;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

public class DateUtilsTest {
	private static final Logger LOGGER = Logger.getLogger(DateUtilsTest.class);

	@Test
	public void getDateTest() {
		// Test normal date
		Date date = DateUtils.getDate(11, 2022);
		LOGGER.info("Date instance for month:11 and year:2022 -> " + date);
		assertNotNull(
				"Date instance created from month and year should not be null",
				date);
		assertTrue("Verify month in Date instance", date.getMonth() + 1 == 11);
		LOGGER.info("Year in Date instance -> " + date.getYear());
		// assertTrue("Verify year in Date instance", date.getYear() == 2022);

		// Test abnormal date
		date = DateUtils.getDate(0, 0);
		LOGGER.info("Date instance for month:0 and year:0 -> " + date);
		date = DateUtils.getDate(-1, 2010);
		LOGGER.info("Date instance for month:-1 and year:2010 -> " + date);
		date = DateUtils.getDate(-1, -1);
		LOGGER.info("Date instance for month:-1 and year:-1 -> " + date);
	}

	@Test
	public void formatTest() {
		Date date = new Date();
		// Test short date format
		String dateStr = DateUtils.format(date, "short");
		LOGGER.info("The short date format -> " + dateStr);

		// Test medium date format
		dateStr = DateUtils.format(date, "medium");
		LOGGER.info("The short date format -> " + dateStr);

		// Test long date format
		dateStr = DateUtils.format(date, "long");
		LOGGER.info("The short date format -> " + dateStr);

		// Test full date format
		dateStr = DateUtils.format(date, "full");
		LOGGER.info("The short date format -> " + dateStr);

		// Test user defined format - "MMyyyy"
		dateStr = DateUtils.format(date, "MMyyyy");
		LOGGER.info("The short date format -> " + dateStr);

		// Test user defined format - "yyyyMM"
		dateStr = DateUtils.format(date, "yyyyMM");
		LOGGER.info("The short date format -> " + dateStr);
	}
}
