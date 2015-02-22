package com.ylw.enterprise.validation.example;

import org.pojomatic.annotations.AutoProperty;

import com.ylw.enterprise.validation.bean.AbstractValidationBean;

@AutoProperty
public class Shape extends AbstractValidationBean {
	private final double opacity;

	public double getOpacity() {
		return opacity;
	}

	protected static abstract class Init<T extends Init<T>> {
        private double opacity;

        protected abstract T self();

        public T opacity(double opacity) {
            this.opacity = opacity;
            return self();
        }

        public Shape build() {
            return new Shape(this);
        }
    }

    public static class Builder extends Init<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        public static Builder defaults() {
			return new Builder();
		}
    }

    protected Shape(Init<?> init) {
        this.opacity = init.opacity;
    }

	@Override
	public AbstractValidationBean validate() {
		// TODO Auto-generated method stub
		return null;
	}
}
