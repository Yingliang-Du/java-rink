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

import java.math.RoundingMode;

class NumberFormatOption {
	private Boolean groupingUsed;
	private Integer maximumFractionDigits;
	private Integer maximumIntegerDigits;
	private Integer minimumFractionDigits;
	private Integer minimumIntegerDigits;
	private Boolean parseIntegerOnly;
	private RoundingMode roundingMode;
	
	public Boolean getGroupingUsed() {
		return groupingUsed;
	}
	public void setGroupingUsed(Boolean groupingUsed) {
		this.groupingUsed = groupingUsed;
	}
	public Integer getMaximumFractionDigits() {
		return maximumFractionDigits;
	}
	public void setMaximumFractionDigits(Integer maximumFractionDigits) {
		this.maximumFractionDigits = maximumFractionDigits;
	}
	public Integer getMaximumIntegerDigits() {
		return maximumIntegerDigits;
	}
	public void setMaximumIntegerDigits(Integer maximumIntegerDigits) {
		this.maximumIntegerDigits = maximumIntegerDigits;
	}
	public Integer getMinimumFractionDigits() {
		return minimumFractionDigits;
	}
	public void setMinimumFractionDigits(Integer minimumFractionDigits) {
		this.minimumFractionDigits = minimumFractionDigits;
	}
	public Integer getMinimumIntegerDigits() {
		return minimumIntegerDigits;
	}
	public void setMinimumIntegerDigits(Integer minimumIntegerDigits) {
		this.minimumIntegerDigits = minimumIntegerDigits;
	}
	public Boolean getParseIntegerOnly() {
		return parseIntegerOnly;
	}
	public void setParseIntegerOnly(Boolean parseIntegerOnly) {
		this.parseIntegerOnly = parseIntegerOnly;
	}
	public RoundingMode getRoundingMode() {
		return roundingMode;
	}
	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}
}
