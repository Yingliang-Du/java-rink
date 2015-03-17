package com.ylw.enterprise.validation.example;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

public class RecurrenceExpressionUtil {

	@VisibleForTesting
	static final String SUPPORTED_RECURRENCE_EXPRESSION_START = "RRULE:FREQ=WEEKLY;INTERVAL=1;WKST=SU;BYDAY=";
	private static final Logger LOGGER = Logger.getLogger(RecurrenceExpressionUtil.class);

	// TECHDEBT Very fragile
	/**
	 * Extracts a message for delivery schedule from a recurrence expression
	 * from the os_home_delivery_schedule table.
	 *
	 * @param recurrenceExpression
	 * @return
	 */
	public static String getDays(String recurrenceExpression) {
		if (recurrenceExpression == null
				|| !recurrenceExpression
						.startsWith(SUPPORTED_RECURRENCE_EXPRESSION_START)) {
			LOGGER.warn("Unsupported recurrence_expression: "
					+ recurrenceExpression);
			return null;
		}
		String dayString = recurrenceExpression
				.substring(SUPPORTED_RECURRENCE_EXPRESSION_START.length());
		List<String> days = Lists.newArrayList(dayString.split(","));
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < days.size(); i++) {
			builder.append(toFullName(days.get(i)));
			if (i < days.size() - 2) {
				builder.append(", ");
			} else if (i == days.size() - 2) {
				builder.append(" and ");
			}
		}

		return builder.toString();
	}

	private static enum Days {
		MO("Mondays"), TU("Tuesdays"), WE("Wednesdays"), TH("Thursdays"), FR(
				"Fridays"), SA("Saturdays"), SU("Sundays");

		private final String fullName;

		Days(String fullName) {
			this.fullName = fullName;
		}

		String getFullName() {
			return fullName;
		}
	}

	private static String toFullName(String dayAbbreviation) {
		return Days.valueOf(dayAbbreviation).getFullName();
	}

}
