package com.ylw.validation.common;

import org.pojomatic.Pojomatic;

public class PojomaticBean {
	// ---------------Override equals, toString and hashCode--------------
	@Override
	public boolean equals(Object other) {
		return Pojomatic.equals(this, other);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
