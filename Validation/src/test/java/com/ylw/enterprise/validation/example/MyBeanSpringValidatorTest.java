package com.ylw.enterprise.validation.example;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ylw.enterprise.validation.common.DateUtils;
import com.ylw.validation.error.BeanErrors;

import static junit.framework.Assert.*;

import java.util.Date;

public class MyBeanSpringValidatorTest {
	private static final Logger LOGGER = Logger.getLogger(MyBeanSpringValidatorTest.class);

	@Test
	public void validate() {
		MyBeanSpringValidator validator = new MyBeanSpringValidator();

		MyBean bean = new MyBean();

		assertTrue(validator.supports(bean.getClass()));

		// test null values
		BeanErrors errors = validator.validate(bean);
		LOGGER.info("-----test null values-----" + errors);
		assertTrue(errors.hasErrors());

		// test startDate before today
		validator = new MyBeanSpringValidator();
		bean.setStringField("Not Null");
		bean.setStartDate(DateUtils.getDate(7, 2015));
		bean.setExpirDate(new Date());
		errors = validator.validate(bean);
		LOGGER.info("-----test startDate before today-----" + errors);
		assertTrue(errors.hasErrors());
		
		// test expirDate before startDate
		validator = new MyBeanSpringValidator();
		bean.setStringField("Not Null");
		bean.setExpirDate(DateUtils.getDate(7, 2015));
		bean.setStartDate(new Date());
		errors = validator.validate(bean);
		LOGGER.info("-----test expirDate before startDate-----" + errors);
		assertTrue(errors.hasErrors());

		// test valid values
		validator = new MyBeanSpringValidator();
		bean.setStringField("Not Null");
		bean.setExpirDate(DateUtils.getDate(7, 2022));
		bean.setStartDate(new Date());
		errors = validator.validate(bean);
		LOGGER.info("-----test valid values-----" + errors);
		assertFalse(errors.hasErrors());
	}

}
