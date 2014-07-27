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

package com.antiaction.common.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.antiaction.common.json.representation.JSONValue;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 09/01/2013
 */
public abstract class JSONConverterAbstract {

	public Boolean getBoolean(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Integer getInteger(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Long getLong(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Float getFloat(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Double getDouble(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public BigInteger getBigInteger(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public BigDecimal getBigDecimal(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public String getString(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public byte[] getBytes(String fieldName, JSONValue json_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, Boolean boolean_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, Integer integer_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, Long long_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, Float float_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, Double double_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, BigInteger biginteger_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, BigDecimal bigdecimal_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, String string_value) {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONValue getJSONValue(String fieldName, byte[] byte_array) {
		throw new UnsupportedOperationException("Unimplemented");
	}

}
