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
package com.ylw.enterprise.validation.validator;

public class ValidationRule {
	private boolean creditCard;
	private boolean email;
	private boolean nonBlank;
	private boolean required;
	private boolean positiveNumber;
	private boolean unique;
	private boolean url;
	private Object min;
	private Object max;

	public boolean isCreditCard() {
		return creditCard;
	}

	public void setCreditCard(boolean creditCard) {
		this.creditCard = creditCard;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isNonBlank() {
		return nonBlank;
	}

	public void setNonBlank(boolean nonBlank) {
		this.nonBlank = nonBlank;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isPositiveNumber() {
		return positiveNumber;
	}

	public void setPositiveNumber(boolean positiveNumber) {
		this.positiveNumber = positiveNumber;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isUrl() {
		return url;
	}

	public void setUrl(boolean url) {
		this.url = url;
	}

	public Object getMin() {
		return min;
	}

	public void setMin(Object min) {
		this.min = min;
	}

	public Object getMax() {
		return max;
	}

	public void setMax(Object max) {
		this.max = max;
	}

	/** ------------------Builder Pattern-------------------- */
	public static class Builder {
		private ValidationRule rule;

		public Builder() {
			rule = new ValidationRule();
		}

		public static Builder defaultValues() {
			return new Builder();
		}

		public Builder withCreditCard(boolean creditCard) {
			rule.setCreditCard(creditCard);
			return this;
		}

		public Builder withEmail(boolean email) {
			rule.setEmail(email);
			return this;
		}

		public Builder withNonBlank(boolean nonBlank) {
			rule.setNonBlank(nonBlank);
			return this;
		}

		public Builder withRequired(boolean required) {
			rule.setRequired(required);
			return this;
		}

		public Builder withPositiveNumber(boolean positiveNumber) {
			rule.setPositiveNumber(positiveNumber);
			return this;
		}

		public Builder withUnique(boolean unique) {
			rule.setUnique(unique);
			return this;
		}

		public Builder withUrl(boolean url) {
			rule.setUrl(url);
			return this;
		}

		public Builder withMin(Object min) {
			rule.setMin(min);
			return this;
		}

		public Builder withMax(Object max) {
			rule.setMax(max);
			return this;
		}

		// Return the built instance
		public ValidationRule build() {
			return rule;
		}
	}
}
