/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JSONValue {

	public int type;

	public void encode(JSONEncoder encoder) throws IOException {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONArray getArray() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public JSONObject getObject() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Boolean getBoolean() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public String getString() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Integer getInteger() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Long getLong() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Float getFloat() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public Double getDouble() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public BigInteger getBigInteger() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	public BigDecimal getBigDecimal() {
		throw new UnsupportedOperationException("Unimplemented");
	}

}
