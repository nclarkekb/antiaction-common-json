/*
 * Created on 17/10/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * De-serialize a JSON data stream into Java Object(s).
 *
 * @author Nicholas
 */
public class JSONStreamUnmarshaller {

	protected JSONObjectMappings objectMappings;

	protected Map<String, JSONObjectMapping> classMappings;

	public JSONStreamUnmarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
	}

	public <T> T toObject(InputStream in, JSONDecoder decoder, Class<T> clazz) throws JSONException {
		return toObject( in, decoder, clazz, null );
	}

	public <T> T toObject(InputStream in, JSONDecoder decoder, Class<T> clazz, JSONConverterAbstract[] converters) throws JSONException {
		Boolean booleanVal;
		Integer intVal;
		Long longVal;
		Float floatVal;
		Double doubleVal;
		BigInteger bigIntegerVal;
		BigDecimal bigDecimalVal;
		String strVal;
		byte[] byteArray;
		Object object;
		JSONObject json_object;

		JSONArray json_array;
		List<JSONValue> json_values;
		boolean[] arrayOf_boolean;
		int[] arrayOf_int;
		long[] arrayOf_long;
		float[] arrayOf_float;
		double[] arrayOf_double;
		Boolean[] arrayOf_Boolean;
		Integer[] arrayOf_Integer;
		Long[] arrayOf_Long;
		Float[] arrayOf_Float;
		Double[] arrayOf_Double;
		BigInteger[] arrayOf_BigInteger;
		BigDecimal[] arrayOf_BigDecimal;
		String[] arrayOf_String;
		Object[] arrayOf_Object;


		return null;
	}

}
