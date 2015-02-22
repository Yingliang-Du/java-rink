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
package com.ylw.enterprise.validation.example;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.pojomatic.annotations.AutoProperty;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;

/**
 *
 */
@AutoProperty
public class NumberBean extends AbstractValidationBean {
	private Byte byteField;
	private Short shortField;
	private Integer integerField;
	private Long longField;
	private Float floatField;
	private Double doubleField;
	private BigInteger bigIntegerField;
	private BigDecimal bigDecimalField;

	public Byte getByteField() {
		return byteField;
	}
	public void setByteField(Byte byteField) {
		this.byteField = byteField;
	}
	public Short getShortField() {
		return shortField;
	}
	public void setShortField(Short shortField) {
		this.shortField = shortField;
	}
	public Integer getIntegerField() {
		return integerField;
	}
	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public Long getLongField() {
		return longField;
	}
	public void setLongField(Long longField) {
		this.longField = longField;
	}

	public Float getFloatField() {
		return floatField;
	}
	public void setFloatField(Float floatField) {
		this.floatField = floatField;
	}

	public Double getDoubleField() {
		return doubleField;
	}
	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	public BigInteger getBigIntegerField() {
		return bigIntegerField;
	}
	public void setBigIntegerField(BigInteger bigIntegerField) {
		this.bigIntegerField = bigIntegerField;
	}

	public BigDecimal getBigDecimalField() {
		return bigDecimalField;
	}
	public void setBigDecimalField(BigDecimal bigDecimalField) {
		this.bigDecimalField = bigDecimalField;
	}

	/**
	 * Validate on fields need to be validated
	 * @see com.ylw.enterprise.validation.bean.AbstractValidationBean#validate()
	 */
	@Override
	public AbstractValidationBean validate() {
		validate("byteField", byteField, onRule().min(5).max(9).build());
		validate("shortField", shortField, onRule().min(5).max(9).build());
		validate("integerField", integerField, onRule().min(5).build());
		validate("longField", longField, onRule().min(5).build());
		validate("floatField", floatField, onRule().min(5).build());
		validate("doubleField", doubleField, onRule().min(5).build());
		validate("bigIntegerField", bigIntegerField, onRule().min(5).build());
		validate("bigDecimalField", bigDecimalField, onRule().min(5).build());
		// Return this bean
		return this;
	}

}
