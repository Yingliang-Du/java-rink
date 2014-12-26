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
	boolean creditCard;
	boolean email;
	boolean nonBlank;
	boolean nonNull;
	boolean positiveNumber;
	boolean unique;
	boolean url;

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

	public boolean isNonNull() {
		return nonNull;
	}

	public void setNonNull(boolean nonNull) {
		this.nonNull = nonNull;
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

		public Builder withNonNull(boolean nonNull) {
			rule.setNonNull(nonNull);
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

		// Return the built instance
		public ValidationRule build() {
			return rule;
		}
	}
}
