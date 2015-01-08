package com.ylw.enterprise.validation.common;

import java.math.RoundingMode;

public class NumberFormatOption {
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
