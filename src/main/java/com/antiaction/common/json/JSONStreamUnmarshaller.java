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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * De-serialize a JSON data stream into Java Object(s).
 *
 * @author Nicholas
 * Created on 17/10/2013
 */
public class JSONStreamUnmarshaller {

	private static final int S_START = 0;
	private static final int S_OBJECT = 1;
	private static final int S_OBJECT_NAME = 20;
	private static final int S_OBJECT_COLON = 21;
	private static final int S_OBJECT_VALUE = 23;
	private static final int S_OBJECT_VALUE_NEXT = 24;
	private static final int S_OBJECT_NAME_NEXT = 25;
	private static final int S_ARRAY = 2;
	private static final int S_ARRAY_VALUE = 3;
	private static final int S_ARRAY_NEXT = 4;
	private static final int S_VALUE_START = 5;
	private static final int S_STRING = 6;
	private static final int S_STRING_UNESCAPE = 7;
	private static final int S_STRING_UNHEX = 8;
	private static final int S_CONSTANT = 9;
	private static final int S_NUMBER_MINUS = 10;
	private static final int S_NUMBER_ZERO = 11;
	private static final int S_NUMBER_INTEGER = 12;
	private static final int S_NUMBER_DECIMAL = 13;
	private static final int S_NUMBER_DECIMALS = 14;
	private static final int S_NUMBER_E = 15;
	private static final int S_NUMBER_EXPONENT = 16;
	private static final int S_NUMBER_EXPONENTS = 17;
	private static final int S_EOF = 18;

	private static final int T_NULL = 1;
	private static final int T_BOOLEAN = 2;
	private static final int T_STRING = 3;
	private static final int T_NUMBER = 4;
	private static final int T_OBJECT = 5;

	protected JSONObjectMappings objectMappings;

	protected Map<String, JSONObjectMapping> classMappings;

	/** Temporary <code>StringBuilder</code> used to store JSON strings and values. */
	protected StringBuilder sbStr = new StringBuilder();

	private static final class StackEntry {
		Object curObj;
		Map<String, JSONObjectFieldMapping> fieldMappingsMap;
		JSONObjectFieldMapping fieldMapping;
		int rstate;
	}

	public JSONStreamUnmarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
	}

	public <T> T toObject(InputStream in, JSONDecoder decoder, Class<T> clazz) throws IOException, JSONException {
		return toObject( in, decoder, clazz, null );
	}

	public <T> T toObject(InputStream in, JSONDecoder decoder, Class<T> clazz, JSONConverterAbstract[] converters) throws IOException, JSONException {
		Boolean booleanVal = null;
		Integer intVal = null;
		Long longVal = null;
		Float floatVal = null;
		Double doubleVal = null;
		BigInteger bigIntegerVal = null;
		BigDecimal bigDecimalVal = null;
		String stringVal = null;
		byte[] byteArray = null;
		Object object = null;

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

		JSONObjectMapping json_om = classMappings.get( clazz.getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class '" + clazz.getName() + "' not registered." );
		}
		if ( json_om.converters == true && converters == null ) {
			throw new JSONException( "Class '" + clazz.getName() + "' may required converters!" );
		}

		T rootObj = null;

		LinkedList<StackEntry> stack = new LinkedList<StackEntry>();
		StackEntry stackEntry = null;

		char[] charArray = new char[ 1024 ];
		CharBuffer charBuffer = CharBuffer.wrap( charArray );

		decoder.init( in );
		decoder.fill( charBuffer );

		// Switch buffer to read mode.
		charBuffer.flip();

		int pos = charBuffer.position();
		int limit = charBuffer.limit();

		int hexValue = 0;
		int hexCount = 0;
		int i;
		String constant;

		int y = 1;
		int x = 1;

		int json_value_type = 0;

		Object curObj = null;
		Map<String, JSONObjectFieldMapping> fieldMappingsMap;
		JSONObjectFieldMapping fieldMapping = null;

		int state = S_START;
		int rstate = -1;
		boolean bLoop = true;
		char c;
		try {
			rootObj = clazz.newInstance();
			curObj = rootObj;

			fieldMappingsMap = json_om.fieldMappingsMap;

			while ( bLoop ) {
				while ( pos < limit ) {
					c = charArray[ pos ];
					++x;
					switch ( state ) {
					case S_START:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case '{':
							// TODO
							//current = new JSONObject();
							//stack.add( new StackEntry( current, json_name ) );
							state = S_OBJECT;
							break;
						case '[':
							// TODO
							//current = new JSONArray();
							//stack.add( new StackEntry( current, json_name ) );
							state = S_ARRAY;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_OBJECT:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case '}':
							/*
							if ( stack.size() == 0 ) {
								throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
							}
							*/
							stackEntry = stack.removeLast();
							// TODO
							//current = entry.json_structure;
							//json_name = stackEntry.json_name;
							//json_value = current;
							if ( stack.size() > 0 ) {
								//current = stack.getLast().json_structure;
								/*
								if (current.type == JSONConstants.VT_OBJECT) {
									state = S_OBJECT_VALUE;
								}
								else {
									state = S_ARRAY_VALUE;
								}
								*/
							}
							else {
								state = S_EOF;
							}
							break;
						case '"':
							sbStr.setLength( 0 );
							state = S_STRING;
							rstate = S_OBJECT_NAME;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_OBJECT_NAME:
						fieldMapping = fieldMappingsMap.get( stringVal );
						state = S_OBJECT_COLON;
						// debug
						//System.out.println( stringVal );
					case S_OBJECT_COLON:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case ':':
							state = S_VALUE_START;
							rstate = S_OBJECT_VALUE;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_OBJECT_VALUE:
						switch ( json_value_type ) {
						case T_NULL:
							if ( !fieldMapping.nullable ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "'/'" + fieldMapping.jsonName + "' is not nullable." );
							}
							break;
						case T_BOOLEAN:
							switch ( fieldMapping.type ) {
							case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
								}
								if ( booleanVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setBoolean( curObj, booleanVal );
								break;
							case JSONObjectMappingConstants.T_BOOLEAN:
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
								}
								if ( booleanVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, booleanVal );
								break;
							default:
								throw new JSONException( "Wrong type." );
							}
							break;
						case T_STRING:
							switch ( fieldMapping.type ) {
							case JSONObjectMappingConstants.T_STRING:
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//strVal = converters[ fieldMapping.converterId ].getString( fieldMapping.fieldName, json_string );
								}
								if ( stringVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, stringVal );
								break;
							case JSONObjectMappingConstants.T_BYTEARRAY:
								if ( fieldMapping.converterId == -1 ) {
									byteArray = stringVal.getBytes();
								}
								else {
									// TODO
									//byteArray = converters[ fieldMapping.converterId ].getBytes( fieldMapping.fieldName, json_string );
								}
								if ( byteArray == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, byteArray );
								break;
							default:
								throw new JSONException( "Wrong type." );
							}
							break;
						case T_OBJECT:
							switch ( fieldMapping.type ) {
							case JSONObjectMappingConstants.T_OBJECT:
								if ( object != null ) {
									// TODO
									//object = toObject( object, fieldMapping.clazz, converters );
								}
								if ( object == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, object );
								break;
							default:
								throw new JSONException( "Wrong type." );
							}
							break;
						case T_NUMBER:
							switch ( fieldMapping.type ) {
							case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
								if ( "1".equals( stringVal ) ) {
									booleanVal = true;
								}
								else if ( "0".equals( stringVal ) ) {
									booleanVal = false;
								}
								else {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is a boolean and can not be '" + stringVal + "'." );
								}
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
									booleanVal = null;
								}
								if ( booleanVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setBoolean( curObj, booleanVal );
								break;
							case JSONObjectMappingConstants.T_BOOLEAN:
								if ( "1".equals( stringVal ) ) {
									booleanVal = true;
								}
								else if ( "0".equals( stringVal ) ) {
									booleanVal = false;
								}
								else {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is a boolean and can not be '" + stringVal + "'." );
								}
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
									booleanVal = null;
								}
								if ( booleanVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, booleanVal );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
								if ( fieldMapping.converterId == -1 ) {
									intVal = Integer.parseInt( stringVal );
								}
								else {
									// TODO
									//intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
								}
								if ( intVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setInt( curObj, intVal );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
								if ( fieldMapping.converterId == -1 ) {
									longVal = Long.parseLong( stringVal );
								}
								else {
									// TODO
									//longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
								}
								if ( longVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setLong( curObj, longVal );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
								if ( fieldMapping.converterId == -1 ) {
									floatVal = Float.parseFloat( stringVal );
								}
								else {
									// TODO
									//floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
								}
								if ( floatVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setFloat( curObj, floatVal );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
								if ( fieldMapping.converterId == -1 ) {
									doubleVal = Double.parseDouble( stringVal );
								}
								else {
									// TODO
									//doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
								}
								if ( doubleVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
								}
								fieldMapping.field.setDouble( curObj, doubleVal );
								break;
							case JSONObjectMappingConstants.T_INTEGER:
								if ( fieldMapping.converterId == -1 ) {
									intVal = Integer.parseInt( stringVal );
								}
								else {
									// TODO
									//intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
								}
								if ( intVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, intVal );
								break;
							case JSONObjectMappingConstants.T_LONG:
								if ( fieldMapping.converterId == -1 ) {
									longVal = Long.parseLong( stringVal );
								}
								else {
									// TODO
									//longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
								}
								if ( longVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, longVal );
								break;
							case JSONObjectMappingConstants.T_FLOAT:
								if ( fieldMapping.converterId == -1 ) {
									floatVal = Float.parseFloat( stringVal );
								}
								else {
									// TODO
									//floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
								}
								if ( floatVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, floatVal );
								break;
							case JSONObjectMappingConstants.T_DOUBLE:
								if ( fieldMapping.converterId == -1 ) {
									doubleVal = Double.parseDouble( stringVal );
								}
								else {
									// TODO
									//doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
								}
								if ( doubleVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, doubleVal );
								break;
							case JSONObjectMappingConstants.T_BIGINTEGER:
								if ( fieldMapping.converterId == -1 ) {
									bigIntegerVal = new BigInteger( stringVal );
								}
								else {
									// TODO
									//bigIntegerVal = converters[ fieldMapping.converterId ].getBigInteger( fieldMapping.fieldName, json_value );
								}
								if ( bigIntegerVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, bigIntegerVal );
								break;
							case JSONObjectMappingConstants.T_BIGDECIMAL:
								if ( fieldMapping.converterId == -1 ) {
									bigDecimalVal = new BigDecimal( stringVal );
								}
								else {
									// TODO
									//bigDecimalVal = converters[ fieldMapping.converterId ].getBigDecimal( fieldMapping.fieldName, json_value );
								}
								if ( bigDecimalVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								fieldMapping.field.set( curObj, bigDecimalVal );
								break;
							default:
								throw new JSONException( "Wrong type." );
							}
							break;
						default:
							throw new IllegalStateException( "Unknown json value type: " + json_value_type );
						}
						// TODO
						//current.put( json_name, json_value );
						state = S_OBJECT_VALUE_NEXT;
						// debug
						//System.out.println( json_value.toString() );
					case S_OBJECT_VALUE_NEXT:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case '}':
							/*
							if ( stack.size() == 0 ) {
								throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
							}
							*/
							if ( stack.size() > 0 ) {
								json_value_type = T_OBJECT;
								object = curObj;
								// TODO
								stackEntry = stack.removeLast();
								curObj = stackEntry.curObj;
								fieldMappingsMap = stackEntry.fieldMappingsMap;
								fieldMapping = stackEntry.fieldMapping;
								state = stackEntry.rstate;
								/*
								if (current.type == JSONConstants.VT_OBJECT) {
									state = S_OBJECT_VALUE;
								}
								else {
									state = S_ARRAY_VALUE;
								}
								*/
							}
							else {
								state = S_EOF;
							}
							break;
						case ',':
							state = S_OBJECT_NAME_NEXT;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_OBJECT_NAME_NEXT:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case '"':
							sbStr.setLength( 0 );
							state = S_STRING;
							rstate = S_OBJECT_NAME;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_ARRAY:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case ']':
							/*
							if ( stack.size() == 0 ) {
								throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
							}
							*/
							stackEntry = stack.removeLast();
							// TODO
							//current = entry.json_structure;
							//json_name = stackEntry.json_name;
							//json_value = current;
							if ( stack.size() > 0 ) {
								//current = stack.getLast().json_structure;
								/*
								if (current.type == JSONConstants.VT_OBJECT) {
									state = S_OBJECT_VALUE;
								}
								else {
									state = S_ARRAY_VALUE;
								}
								*/
							}
							else {
								state = S_EOF;
							}
							break;
						case '{':
							// TODO
							//current = new JSONObject();
							//stack.add( new StackEntry( current, json_name ) );
							state = S_OBJECT;
							break;
						case '[':
							// TODO
							//current = new JSONArray();
							//stack.add( new StackEntry( current, json_name ) );
							state = S_ARRAY;
							break;
						case '"':
							sbStr.setLength( 0 );
							state = S_STRING;
							rstate = S_ARRAY_VALUE;
							break;
						case 'f':
						case 'n':
						case 't':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_CONSTANT;
							rstate = S_ARRAY_VALUE;
							break;
						case '-':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_MINUS;
							rstate = S_ARRAY_VALUE;
							break;
						case '0':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_ZERO;
							rstate = S_ARRAY_VALUE;
							break;
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_INTEGER;
							rstate = S_ARRAY_VALUE;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_ARRAY_VALUE:
						// TODO
						//current.add( json_value );
						state = S_ARRAY_NEXT;
						// debug
						//System.out.println( json_value.toString() );
					case S_ARRAY_NEXT:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case ']':
							/*
							if ( stack.size() == 0 ) {
								throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
							}
							*/
							stackEntry = stack.removeLast();
							// TODO
							//current = entry.json_structure;
							//json_name = stackEntry.json_name;
							//json_value = current;
							if ( stack.size() > 0 ) {
								//current = stack.getLast().json_structure;
								/*
								if (current.type == JSONConstants.VT_OBJECT) {
									state = S_OBJECT_VALUE;
								}
								else {
									state = S_ARRAY_VALUE;
								}
								*/
							}
							else {
								state = S_EOF;
							}
							break;
						case ',':
							state = S_VALUE_START;
							rstate = S_ARRAY_VALUE;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_VALUE_START:
						// rstate should be set prior to this state.
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						case '{':
							// TODO
							stackEntry = new StackEntry();
							stackEntry.curObj = curObj;
							stackEntry.fieldMappingsMap = fieldMappingsMap;
							stackEntry.fieldMapping = fieldMapping;
							stackEntry.rstate = rstate;
							stack.add( stackEntry );

							curObj = fieldMapping.clazz.newInstance();
							json_om = classMappings.get( fieldMapping.clazz.getName() );
							if ( json_om == null ) {
								throw new IllegalArgumentException( "Class '" + fieldMapping.clazz.getName() + "' not registered." );
							}
							if ( json_om.converters == true && converters == null ) {
								throw new JSONException( "Class '" + fieldMapping.clazz.getName() + "' may required converters!" );
							}
							fieldMappingsMap = json_om.fieldMappingsMap;

							state = S_OBJECT;
							break;
						case '[':
							// TODO
							//current = new JSONArray();
							//stack.add( new StackEntry( current, json_name ) );
							state = S_ARRAY;
							break;
						case '"':
							sbStr.setLength( 0 );
							state = S_STRING;
							break;
						case 'f':
						case 'n':
						case 't':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_CONSTANT;
							break;
						case '-':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_MINUS;
							break;
						case '0':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_ZERO;
							break;
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.setLength( 0 );
							sbStr.append( c );
							state = S_NUMBER_INTEGER;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_STRING:
						switch ( c ) {
						case '"':
							json_value_type = T_STRING;
							stringVal = sbStr.toString();
							state = rstate;
							break;
						case '\\':
							state = S_STRING_UNESCAPE;
							break;
						default:
							if ( c < 32 ) {
								throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
							}
							sbStr.append( c );
							break;
						}
						++pos;
						break;
					case S_STRING_UNESCAPE:
						switch ( c ) {
						case '"':
							sbStr.append( '"');
							state = S_STRING;
							break;
						case '/':
							sbStr.append( '/' );
							state = S_STRING;
							break;
						case '\\':
							sbStr.append( '\\' );
							state = S_STRING;
							break;
						case 'b':
							sbStr.append( (char)0x08 );
							state = S_STRING;
							break;
						case 't':
							sbStr.append( (char)0x09 );
							state = S_STRING;
							break;
						case 'n':
							sbStr.append( (char)0x0A );
							state = S_STRING;
							break;
						case 'f':
							sbStr.append( (char)0x0C );
							state = S_STRING;
							break;
						case 'r':
							sbStr.append( (char)0x0D );
							state = S_STRING;
							break;
						case 'u':
							hexValue = 0;
							hexCount = 4;
							state = S_STRING_UNHEX;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_STRING_UNHEX:
						if ( c > 255 ) {
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						i = asciiHexTab[ c ];
						if ( i == -1 ) {
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						hexValue <<= 4;
						hexValue |= i;
						--hexCount;
						if (hexCount == 0) {
							sbStr.append( (char)hexValue );
							state = S_STRING;
						}
						++pos;
						break;
					case S_CONSTANT:
						switch ( c ) {
						case 'a':
						case 'l':
						case 's':
						case 'e':
						case 'r':
						case 'u':
							sbStr.append( c );
							++pos;
							break;
						default:
							constant = sbStr.toString();
							if ( "false".equals( constant ) ) {
								json_value_type = T_BOOLEAN;
								booleanVal = false;
							}
							else if ( "true".equals( constant ) ) {
								json_value_type = T_BOOLEAN;
								booleanVal = true;
							}
							else if ( "null".equals( constant ) ) {
								json_value_type = T_NULL;
							}
							else {
								throw new JSONException( "Invalid JSON constant: '" + constant + "' at (" + y + ":" + x + ")!" );
							}
							state = rstate;
							break;
						}
						break;
					case S_NUMBER_MINUS:
						switch ( c ) {
						case '0':
							sbStr.append( c );
							state = S_NUMBER_ZERO;
							break;
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							state = S_NUMBER_INTEGER;
							break;
						default:
							throw new JSONException( "Invalid JSON number structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_NUMBER_INTEGER:
						switch ( c ) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							++pos;
							break;
						case '.':
							sbStr.append( c );
							state = S_NUMBER_DECIMAL;
							++pos;
							break;
						case 'e':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						case 'E':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						default:
							json_value_type = T_NUMBER;
							stringVal = sbStr.toString();
							state = rstate;
							break;
						}
						break;
					case S_NUMBER_ZERO:
						switch ( c ) {
						case '.':
							sbStr.append( c );
							state = S_NUMBER_DECIMAL;
							++pos;
							break;
						case 'e':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						case 'E':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						default:
							json_value_type = T_NUMBER;
							stringVal = sbStr.toString();
							state = rstate;
							break;
						}
						break;
					case S_NUMBER_DECIMAL:
						switch ( c ) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							state = S_NUMBER_DECIMALS;
							++pos;
							break;
						default:
							throw new JSONException( "Invalid JSON number structure at (" + y + ":" + x + ")!" );
						}
						break;
					case S_NUMBER_DECIMALS:
						switch ( c ) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							++pos;
							break;
						case 'e':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						case 'E':
							sbStr.append( c );
							state = S_NUMBER_E;
							++pos;
							break;
						default:
							json_value_type = T_NUMBER;
							stringVal = sbStr.toString();
							state = rstate;
						}
						break;
					case S_NUMBER_E:
						switch ( c ) {
						case '+':
							sbStr.append( c );
							state = S_NUMBER_EXPONENT;
							break;
						case '-':
							sbStr.append( c );
							state = S_NUMBER_EXPONENT;
							break;
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							state = S_NUMBER_EXPONENTS;
							break;
						default:
							throw new JSONException( "Invalid JSON number structure at (" + y + ":" + x + ")!" );
						}
						++pos;
						break;
					case S_NUMBER_EXPONENT:
						switch ( c ) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							state = S_NUMBER_EXPONENTS;
							++pos;
							break;
						default:
							throw new JSONException( "Invalid JSON number structure at (" + y + ":" + x + ")!" );
						}
						break;
					case S_NUMBER_EXPONENTS:
						switch ( c ) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							sbStr.append( c );
							++pos;
							break;
						default:
							json_value_type = T_NUMBER;
							stringVal = sbStr.toString();
							state = rstate;
						}
						break;
					case S_EOF:
						switch ( c ) {
						case 0x20:
						case 0x09:
						case 0x0D:
							// Whitespace.
							break;
						case 0x0A:
							++y;
							x = 1;
							break;
						default:
							throw new JSONException( "Invalid JSON structure at (" + y + ":" + x + ")!" );
						}
						++pos;
					}
				}
				// Switch buffer to write mode.
				charBuffer.clear();
				decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();

				pos = charBuffer.position();
				limit = charBuffer.limit();

				bLoop = !(pos == limit);
			}
		}
		catch (InstantiationException e) {
			throw new JSONException( e );
		}
		catch (IllegalAccessException e) {
			throw new JSONException( e );
		}
		if (curObj == null || stack.size() > 0) {
			throw new JSONException( "Invalid JSON structure!" );
		}
		return rootObj;
	}

	/** Integer to hex char conversion table. */
	//private static char[] hexTab = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/** Hex char to integer conversion table. */
	private static int[] asciiHexTab = new int[256];

	/*
	 * Initialize ASCII hex table.
	 */
	static {
		String hex = "0123456789abcdef";
		for (int i=0; i<asciiHexTab.length; ++i) {
			asciiHexTab[i] = hex.indexOf(i);
		}
		hex = hex.toUpperCase();
		for (int i=0; i<hex.length(); ++i) {
			asciiHexTab[hex.charAt(i)] = i;
		}
	}

}
