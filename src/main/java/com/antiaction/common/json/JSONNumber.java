/*
 * Created on 02/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public class JSONNumber extends JSONValue {

	protected Integer intVal;

	protected Long longVal;

	protected Float floatVal;

	protected Double doubleVal;

	protected BigInteger bigIntegerVal;

	protected BigDecimal bigDecimalVal;

	protected String numberStr;

	protected byte[] numberBytes;

	public static JSONNumber Integer(int intVal) {
		JSONNumber number = new JSONNumber( Integer.toString( intVal ) );
		number.intVal = intVal;
		return number;
	}

	public static JSONNumber Long(long longVal) {
		JSONNumber number = new JSONNumber( Long.toString( longVal ) );
		number.longVal = longVal;
		return number;
	}

	public static JSONNumber Float(float floatVal) {
		JSONNumber number = new JSONNumber( Float.toString( floatVal ) );
		number.floatVal = floatVal;
		return number;
	}

	public static JSONNumber Double(double doubleVal) {
		JSONNumber number = new JSONNumber( Double.toString( doubleVal ) );
		number.doubleVal = doubleVal;
		return number;
	}

	public static JSONNumber BigInteger(BigInteger bigIntegerVal) {
		JSONNumber number = new JSONNumber( bigIntegerVal.toString() );
		number.bigIntegerVal = bigIntegerVal;
		return number;
	}

	public static JSONNumber BigDecimal(BigDecimal bigDecimalVal) {
		JSONNumber number = new JSONNumber( bigDecimalVal.toString() );
		number.bigDecimalVal = bigDecimalVal;
		return number;
	}

	protected JSONNumber(String str) {
		type = JSONConstants.VT_NUMBER;
		this.numberStr = str;
		this.numberBytes = str.getBytes();
	}

	@Override
	public Integer getInteger() {
		if ( intVal == null ) {
			intVal = Integer.parseInt( numberStr );
		}
		return intVal;
	}

	@Override
	public Long getLong() {
		if ( longVal == null ) {
			longVal = Long.parseLong( numberStr );
		}
		return longVal;
	}

	@Override
	public Float getFloat() {
		if ( floatVal == null ) {
			floatVal = Float.parseFloat( numberStr );
		}
		return floatVal;
	}

	@Override
	public Double getDouble() {
		if ( doubleVal == null ) {
			doubleVal = Double.parseDouble( numberStr );
		}
		return doubleVal;
	}

	@Override
	public BigInteger getBigInteger() {
		if ( bigIntegerVal == null ) {
			 bigIntegerVal = new BigInteger( numberStr );
		}
		return  bigIntegerVal;
	}

	@Override
	public BigDecimal getBigDecimal() {
		if ( bigDecimalVal == null ) {
			bigDecimalVal = new BigDecimal( numberStr );
		}
		return bigDecimalVal;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( numberBytes );
	}

	@Override
	public String toString() {
		return new String( numberBytes );
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null || !(obj instanceof JSONNumber) ) {
			return false;
		}
		JSONNumber json_numberObj = (JSONNumber)obj;
		if ( !Arrays.equals( numberBytes, json_numberObj.numberBytes ) ) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode( new Object[] { numberBytes } );
	}

}
