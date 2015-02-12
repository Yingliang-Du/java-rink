package com.ylw.enterprise.validation.example;

import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MySubBean extends MyBean {
	private String subString;

	public String getSubString() {
		return subString;
	}

	private void setSubString(String subString) {
		this.subString = subString;
	}

	/* ---------------For Builder Inheritance------------------ */
	protected static abstract class Init<T extends Init<T>> extends MyBean.Init<T> {
		private MySubBean mySubBean;

		public Init() {
			this.myBean = new MySubBean();
			this.mySubBean = (MySubBean) myBean;
		}

		public T withSubString(String subString) {
			mySubBean.setSubString(subString);
			return self();
		}

		public MySubBean build() {
			return mySubBean;
		}
	}

	public static class Builder2 extends Init<Builder2> {

		@Override
		protected Builder2 self() {
			return this;
		}

		/**
		 * @return Builder with default values
		 */
		public static Builder2 defaults() {
			return new Builder2();
		}

	}

	/* ------------------Builder--------------------- */

}
