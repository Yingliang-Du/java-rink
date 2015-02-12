package com.ylw.enterprise.validation.example;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class MySubBeanTest {
	private static final Logger LOGGER = Logger.getLogger(MySubBeanTest.class);

	@Test
	public void testBuildMySubBean() {
		// Test Builder in super class
		MyBean myBean = MyBean.Builder2.defaults()
				.withStringField("The stringField in MyBean").build();
		LOGGER.info("MyBean instance built with Builder2 -> " + myBean);
		assertNotNull("The stringField field in MyBean should be populated", myBean.getStringField());

		// Test Builder in sub class
		MySubBean mySubBean = MySubBean.Builder2.defaults()
				.withStringField("stringField").withSubString("subString")
				.build();
		LOGGER.info("MySubBean instance built with Builder2 -> " + mySubBean);
		assertNotNull("The subString field in MySubBean should not be null", mySubBean.getSubString());
		assertNotNull("The stringField field in MySubBean should not be null", mySubBean.getStringField());
	}

	@Test
	public void testBuildRectangleShape() {
		// Test Builder in super class
//		Shape shape = new Shape.Builder().opacity(0.5).build();
		Shape shape = Shape.Builder.defaults().opacity(0.5).build();
		LOGGER.info("Super class instance -> " + shape);
		assertNotNull("The opacity field in super class should be populated", shape.getOpacity());

		// Test Builder in super class
//		Rectangle rectangle = new Rectangle.Builder().height(11.11).opacity(0.5).build();
		Rectangle rectangle = Rectangle.Builder.defaults().height(11.11).opacity(0.5).build();
		LOGGER.info("Sub class instance -> " + rectangle);
		assertNotNull("The opacity field in sub class should be populated", rectangle.getOpacity());
		assertNotNull("The height field in sub class should be populated", rectangle.getHeight());
	}
}
