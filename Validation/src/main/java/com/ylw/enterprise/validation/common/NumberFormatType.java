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

import java.text.NumberFormat;
import java.util.Locale;

enum NumberFormatType {
	/**
	 * The default number format.
	 */
	DEFAULT {
		@Override
		public NumberFormat numberFormat(final Locale locale) {
			return NumberFormat.getInstance(locale);
		}
	},
	/**
	 * The integer number format.
	 */
	INTEGER {
		@Override
		public NumberFormat numberFormat(final Locale locale) {
			return NumberFormat.getIntegerInstance(locale);
		}
	},
	/**
	 * The currency number format.
	 */
	CURRENCY {
		@Override
		public NumberFormat numberFormat(final Locale locale) {
			return NumberFormat.getCurrencyInstance(locale);
		}
	},
	/**
	 * The percent number format.
	 */
	PERCENT {
		@Override
		public NumberFormat numberFormat(final Locale locale) {
			return NumberFormat.getPercentInstance(locale);
		}
	};
	/**
	 * Build a new number format.
	 * 
	 * @param locale
	 *            The locale to use.
	 * @return A new number format.
	 */
	public abstract NumberFormat numberFormat(Locale locale);
}
