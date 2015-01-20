package com.ylw.enterprise.validation.example;

import org.apache.log4j.Logger;

public class MyContext {
	private static final Logger LOGGER = Logger.getLogger(MyContext.class);

	private MyBean myBean;

	public MyBean getMyBean() {
		return myBean;
	}

	public void setMyBean(MyBean myBean) {
		this.myBean = myBean;
	}

	/* ------------------Utility Methods-------------------- */
	/**
	 * Build JSON representation of all objects in this page context
	 * Can not create getJson() method in contained object b/c that will cause StackOverflowError
	 *
	 * @return JSON String represent the bean
	 */
	public String getJson() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName()).append(": { \n");
		// Add JSON String for billing address
		String jsonString = myBean.toJson();
		builder.append(myBean.getClass().getSimpleName()).append(": ").append(jsonString);

		// Close and get the JSON String
		builder.append("\n}");
		jsonString = builder.toString();
		LOGGER.info("JSON String for page context: \n" + jsonString);

		// Return the JSON String
		return jsonString;
	}
}
