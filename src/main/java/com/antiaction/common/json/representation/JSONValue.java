/*
 * JSON library.
 * Copyright 2012-2013 Antiaction (http://antiaction.com/)
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

package com.antiaction.common.json.representation;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.antiaction.common.json.JSONEncoder;

/**
 * Abstract JSON value base. Implementations should only override the methods
 * which are relevant for their supported value type.
 *
 * @author Nicholas
 * Created on 01/08/2012
 */
public abstract class JSONValue {

	/** Type of the extending instance. */
	public int type;

	/**
	 * Output the JSON value to the given Encoder.
	 * @param encoder output encoder
	 * @throws IOException if an I/O error occurs while encoding
	 */
	public void encode(JSONEncoder encoder) throws IOException {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Output pretty print the JSON value to the given Encoder.
	 * @param encoder output encoder
	 * @param indentation indentation to use before output text
	 * @param indent indentation to use for each level
	 * @throws IOException if an I/O error occurs while encoding
	 */
	public void encode(JSONEncoder encoder, String indentation, String indent) throws IOException {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as an <code>JSONArray</code> or null
	 * @return the JSON value as an <code>JSONArray</code> or null
	 */
	public JSONArray getArray() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as an <code>JSONObject</code> or null
	 * @return the JSON value as an <code>JSONObject</code> or null
	 */
	public JSONObject getObject() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>Boolean</code> or null
	 * @return the JSON value as a <code>Boolean</code> or null
	 */
	public Boolean getBoolean() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>String</code> of null
	 * @return the JSON value as a <code>String</code> of null
	 */
	public String getString() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>Byte</code> array or null
	 * @return the JSON value as a <code>Byte</code> array or null
	 */
	public byte[] getBytes() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as an <code>Integer</code> or null
	 * @return the JSON value as an <code>Integer</code> or null
	 */
	public Integer getInteger() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>Long</code> or null
	 * @return the JSON value as a <code>Long</code> or null
	 */
	public Long getLong() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>Float</code> or null
	 * @return the JSON value as a <code>Float</code> or null
	 */
	public Float getFloat() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>Double</code> or null
	 * @return the JSON value as a <code>Double</code> or null
	 */
	public Double getDouble() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>BigInteger</code> or null
	 * @return the JSON value as a <code>BigInteger</code> or null
	 */
	public BigInteger getBigInteger() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	/**
	 * Returns the JSON value as a <code>BigDecimal</code> or null
	 * @return the JSON value as a <code>BigDecimal</code> or null
	 */
	public BigDecimal getBigDecimal() {
		throw new UnsupportedOperationException("Unimplemented");
	}

}
