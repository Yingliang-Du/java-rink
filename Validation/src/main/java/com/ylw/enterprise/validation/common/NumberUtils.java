package com.ylw.enterprise.validation.common;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

	public static String format(Number number, String pattern, Locale locale,
			NumberFormatOption option) {
		NumberFormat numberFormat = getFormat(pattern, locale);

		// Setup number format options
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

		// Return formatted number string
		return numberFormat.format(number);
	}

	private static NumberFormat getFormat(String pattern, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		NumberFormat numberFormat = NumberFormatType.DEFAULT.numberFormat(locale);
		// Return NumberFormat instance
		return numberFormat;
	}

}
