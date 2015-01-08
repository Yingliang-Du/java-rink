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
package com.ylw.enterprise.validation.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
	/**
	 * The pattern parameter is one of:
	 * <ul>
	 * <li>"integer": the integer number format</li>
	 * <li>"percent": the percent number format</li>
	 * <li>"currency": the decimal number format</li>
	 * <li>pattern: a decimal pattern.</li>
	 * </ul>
	 * 
	 * @param number
	 * @param pattern
	 * @return formatted number string
	 */
	public static String format(Number number, String pattern) {
		return format(number, pattern, null, null);
	}

	/**
	 * The pattern parameter is one of:
	 * <ul>
	 * <li>"integer": the integer number format</li>
	 * <li>"percent": the percent number format</li>
	 * <li>"currency": the currency number format</li>
	 * <li>pattern: a decimal pattern.</li>
	 * </ul>
	 * 
	 * @param number
	 * @param pattern
	 * @param locale
	 * @param option
	 * @return formatted number string
	 */
	public static String format(Number number, String pattern, Locale locale, NumberFormatOption option) {
		if (locale == null) {
			locale = Locale.getDefault();
		}

		NumberFormat numberFormat = getFormat(pattern, locale);

		// Setup number format options
		if (option != null) {
			if (option.getGroupingUsed() != null) {
				numberFormat.setGroupingUsed(option.getGroupingUsed());
			}
			if (option.getMaximumFractionDigits() != null) {
				numberFormat.setMaximumFractionDigits(option.getMaximumFractionDigits());
			}
			if (option.getMaximumIntegerDigits() != null) {
				numberFormat.setMaximumIntegerDigits(option.getMaximumIntegerDigits());
			}
			if (option.getMinimumFractionDigits() != null) {
				numberFormat.setMinimumFractionDigits(option.getMinimumFractionDigits());
			}
			if (option.getMinimumIntegerDigits() != null) {
				numberFormat.setMinimumIntegerDigits(option.getMinimumIntegerDigits());
			}
			if (option.getParseIntegerOnly() != null) {
				numberFormat.setParseIntegerOnly(option.getParseIntegerOnly());
			}
			if (option.getRoundingMode() != null) {
				numberFormat.setRoundingMode(option.getRoundingMode());
			}
		}

		// Return formatted number string
		return numberFormat.format(number);
	}

	private static NumberFormat getFormat(String pattern, Locale locale) {

		NumberFormat numberFormat = NumberFormatType.DEFAULT.numberFormat(locale);
		try {
			NumberFormatType formatType = NumberFormatType.valueOf(pattern.toUpperCase().trim());
			numberFormat = formatType.numberFormat(locale);
		}
		catch (ArrayIndexOutOfBoundsException ex) {
		}
		catch (IllegalArgumentException ex) {
			numberFormat = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
		}
		// Return NumberFormat instance
		return numberFormat;
	}

}
