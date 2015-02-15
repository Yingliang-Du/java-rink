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

import java.util.Collection;

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
	private Collection<?> in;
	// For adding customized validation logic
	private boolean badCondition;
	// Specify if the validation rule is ignorable
	private boolean ignorable;

	public boolean isIgnorable() {
		return ignorable;
	}

	public void setIgnorable(boolean ignorable) {
		this.ignorable = ignorable;
	}

	public boolean isBadCondition() {
		return badCondition;
	}

	private void setBadCondition(boolean badCondition) {
		this.badCondition = badCondition;
	}

	public boolean isCreditCard() {
		return creditCard;
	}

	private void setCreditCard(boolean creditCard) {
		this.creditCard = creditCard;
	}

	public boolean isEmail() {
		return email;
	}

	private void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isNonBlank() {
		return nonBlank;
	}

	private void setNonBlank(boolean nonBlank) {
		this.nonBlank = nonBlank;
	}

	public boolean isRequired() {
		return required;
	}

	private void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isPositiveNumber() {
		return positiveNumber;
	}

	private void setPositiveNumber(boolean positiveNumber) {
		this.positiveNumber = positiveNumber;
	}

	public boolean isUnique() {
		return unique;
	}

	private void setUnique(boolean unique) {
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

	private void setMin(Object min) {
		this.min = min;
	}

	public Object getMax() {
		return max;
	}

	private void setMax(Object max) {
		this.max = max;
	}

	public Collection<?> getIn() {
		return in;
	}

	public void setIn(Collection<?> in) {
		this.in = in;
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

		public Builder ignorable(boolean ignorable) {
			rule.setIgnorable(ignorable);
			return this;
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

		public Builder withIn(Collection<?> in) {
			rule.setIn(in);
			return this;
		}

		public Builder withBadCondition(boolean badCondition) {
			rule.setBadCondition(badCondition);
			return this;
		}

		// Return the built instance
		public ValidationRule build() {
			return rule;
		}
	}
}
