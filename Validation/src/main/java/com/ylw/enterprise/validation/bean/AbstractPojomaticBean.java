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
package com.ylw.enterprise.validation.bean;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.pojomatic.Pojomatic;

/**
 * Simply extends this class and annotate the fields and/or methods of your class with @Property that you would like
 * included, or annotate the class with @AutoProperty to have all of the fields included by default. Then, delegate to
 * Pojomatic for your hashCode(), toString(), and equals(Object) methods and you have implementations that essentially
 * maintain themselves.
 *
 */
public abstract class AbstractPojomaticBean extends AbstractValidationBean {
	private static final Logger LOGGER = Logger.getLogger(AbstractPojomaticBean.class);
	
	// ---------------Convert to JSON----------------
	public String toJson() {
		// Place holder for converted JSON string
		String jsonString = null;
		// Instantiate Jackson object mapper
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Unformatted JSON string
//			jsonString = mapper.writeValueAsString(this);
			// Formatted JSON string
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		}
		catch (IOException e) {
			// Got exception during conversion
			LOGGER.error("Exception happened when convert " + this.getClass().getSimpleName() + " to JSON");
			e.printStackTrace();
		}
		LOGGER.info(this.getClass().getSimpleName() + ": " + jsonString);
		// Return the JSON string
		return jsonString;
	}
	
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
