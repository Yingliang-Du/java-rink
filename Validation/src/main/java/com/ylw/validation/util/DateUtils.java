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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtils {
	private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

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
	 * Create Date instance for today without time 
	 */
	public static Date getDateWithoutTime() {
		long millisInDay = 60 * 60 * 24 * 1000;
		long currentTime = new Date().getTime();
		long dateOnly = (currentTime / millisInDay) * millisInDay;
		return new Date(dateOnly);
	}
	
	/**
	 * Create Date instance for the beginning of today
	 */
	public static Date getDateForBeginningOfToday() {
		Calendar calStart = new GregorianCalendar();
		calStart.setTime(new Date());
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		return calStart.getTime();
	}
}
