/*
 * Created on 18/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class JSONNull extends JSONValue {

	public static final JSONNull Null = new JSONNull();

	protected static final byte[] nullBytes = "null".getBytes();

	protected static int hashCode = Arrays.deepHashCode( new Object[] { nullBytes } );

	protected JSONNull() {
		type = JSONConstants.VT_NULL;
	}

	@Override
	public JSONArray getArray() {
		return null;
	}

	@Override
	public JSONObject getObject() {
		return null;
	}

	@Override
	public Boolean getBoolean() {
		return null;
	}

	@Override
	public String getString() {
		return null;
	}

	@Override
	public byte[] getBytes() {
		return null;
	}

	@Override
	public Integer getInteger() {
		return null;
	}

	@Override
	public Long getLong() {
		return null;
	}

	@Override
	public Float getFloat() {
		return null;
	}

	@Override
	public Double getDouble() {
		return null;
	}

	@Override
	public BigInteger getBigInteger() {
		return null;
	}

	@Override
	public BigDecimal getBigDecimal() {
		return null;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( nullBytes );
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null || !(obj instanceof JSONNull) ) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

}
