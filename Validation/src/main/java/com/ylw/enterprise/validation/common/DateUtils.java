package com.ylw.enterprise.validation.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtils {
	private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

	private static Map<String, Integer> formats = new HashMap<String, Integer>() {
		{
			put("full", DateFormat.FULL);
			put("long", DateFormat.LONG);
			put("medium", DateFormat.MEDIUM);
			put("short", DateFormat.SHORT);
		}
	};

	/**
	 * Create Date instance from month and year
	 *
	 * @param month
	 * @param year
	 * @return Date instance
	 */
	public static Date getDate(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * Format Date base on given pattern
	 *
	 * @param date
	 * @param pattern should be one of:
	 * <ul>
	 * <li>"full": full date format; example: Wednesday, January 7, 2015</li>
	 * <li>"long": long date format; example: January 7, 2015</li>
	 * <li>"medium": medium date format; example: Jan 7, 2015</li>
	 * <li>"short": short date format; example: 1/7/15</li>
	 * <li>pattern: a user defined date pattern; example: "MMyyyy" - 12015</li>
	 * </ul>
	 * @return formatted date string
	 */
	public static String format(Date date, String pattern) {
		return format(date, pattern, null, null);
	}

	/**
	 * Format Date base on given pattern and locale
	 *
	 * @param date
	 * @param pattern should be one of:
	 * <ul>
	 * <li>"full": full date format; example: Wednesday, January 7, 2015</li>
	 * <li>"long": long date format; example: January 7, 2015</li>
	 * <li>"medium": medium date format; example: Jan 7, 2015</li>
	 * <li>"short": short date format; example: 1/7/15</li>
	 * <li>pattern: a user defined date pattern; example: "MMyyyy" - 12015</li>
	 * </ul>
	 * @param timeZone
	 * @param locale
	 * @return formatted date string
	 */
	public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
		DateFormat dateFormat;
		// Deal with Locale
		locale = locale == null ? Locale.getDefault() : locale;
		// Deal with format
		Integer format = formats.get(pattern);
		if (format == null) {
			dateFormat = new SimpleDateFormat(pattern, locale);
		}
		else {
			dateFormat = DateFormat.getDateInstance(format, locale);
		}
		// Deal with time zone
		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}
		// Return formatted date string
		return dateFormat.format(date);
	}

}
