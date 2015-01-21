package com.ylw.enterprise.validation.example;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.dummycreator.DummyCreator;
import org.junit.Test;

public class MyContextTest {
	private static final Logger LOGGER = Logger.getLogger(MyContextTest.class);

	@Test
	public void testGetJson() {
		// Prepare object
		MyBean bean = new DummyCreator().create(MyBean.class);
		LOGGER.info("The dummy populated bean: " + bean);
		assertNotNull("The dummy populated bean should not be null", bean);
		MyContext context = new MyContext();
		context.setMyBean(bean);

		// Call business method
		context.setJson();
		String jsonString = context.getJson();
		assertNotNull("The bean's json string should not be null", jsonString);
		assertNotNull("The JSON String should not be null", jsonString);
	}

}
