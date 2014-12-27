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
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

/**
 * Serialize Java Object(s) into a JSON data stream.
 *
 * @author Nicholas
 * Created on 17/10/2013
 */
public class JSONStreamMarshaller {

	private static final int S_OBJECT_BEGIN = 1;

	private static final int S_OBJECT_END = 2;

	private static final int S_ARRAY_BEGIN = 3;

	private static final int S_ARRAY_END = 4;

	private static final int S_OBJECT = 5;

	private static final int S_ARRAY = 6;

	/** Null string cached as bytes. */
	protected static final byte[] nullBytes = "null".getBytes();

	/** True string cached as bytes. */
	protected static final byte[] trueBytes = "true".getBytes();

	/** False string cached as bytes. */
	protected static final byte[] falseBytes = "false".getBytes();

	protected byte[] indentationArr;

	protected JSONObjectMappings objectMappings;

	protected Map<String, JSONObjectMapping> classMappings;

	private static final class StackEntry {
		int state;
		Object object;
		JSONObjectMapping objectMapping;
		JSONObjectFieldMapping[] fieldMappingsArr;
		int fieldMappingIdx;
		JSONObjectFieldMapping fieldMapping;
		Object array;
		int arrayIdx;
		int arrayLen;
	}

	public JSONStreamMarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
		indentationArr = new byte[ 32 ];
		Arrays.fill( indentationArr, (byte)' ' );
	}

	public <T> void toJSONText(T srcObj, JSONEncoder encoder, boolean bPretty, OutputStream out) throws IOException, JSONException {
		toJSONText( srcObj, null, encoder, bPretty, out );
	}

	public <T> void toJSONText(T srcObj, JSONConverterAbstract[] converters, JSONEncoder encoder, boolean bPretty, OutputStream out) throws IOException, JSONException {
		Boolean booleanVal;
		Integer intVal;
		Long longVal;
		Float floatVal;
		Double doubleVal;
		BigInteger bigIntegerVal;
		BigDecimal bigDecimalVal;
		String stringVal;
		byte[] byteArray;
		Object tmpObject;

		Object tmpArray;
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

		encoder.init( out );

		Object object = srcObj;
		Object array = null;
		JSONObjectMapping objectMapping = classMappings.get( object.getClass().getName() );
		if ( objectMapping == null ) {
			throw new IllegalArgumentException( "Class '" + object.getClass().getName() + "' not registered." );
		}
		if ( objectMapping.converters == true && converters == null ) {
			throw new JSONException( "Class '" + object.getClass().getName() + "' may required converters!" );
		}

		int state = S_OBJECT_BEGIN;
		int indentation = 0;
		JSONObjectFieldMapping[] fieldMappingsArr = null;
		int fieldMappingIdx = 0;
		JSONObjectFieldMapping fieldMapping = null;
		int arrayIdx = 0;
		int arrayLen = 0;

		LinkedList<StackEntry> stack = new LinkedList<StackEntry>();
		StackEntry stackEntry;

		boolean bLoop = true;
		boolean bFieldLoop;
		try {
			while ( bLoop ) {
				switch ( state ) {
				case S_OBJECT_BEGIN:
					if ( bPretty ) {
						encoder.write( "{\n" );
						indentation += 2;
						if ( indentation > indentationArr.length ) {
							byte[] newIndentationArr = new byte[ indentationArr.length * 2 ];
							Arrays.fill( newIndentationArr, (byte)' ' );
							indentationArr = newIndentationArr;
						}
					}
					else {
						encoder.write( '{' );
					}
					fieldMappingsArr = objectMapping.fieldMappingsArr;
					fieldMappingIdx = 0;
					state = S_OBJECT;
					break;
				case S_OBJECT_END:
					if ( bPretty ) {
						indentation -= 2;
						encoder.write( '\n' );
						encoder.write( indentationArr, 0, indentation );
					}
					encoder.write( '}' );
					if ( stack.size() > 0 ) {
						stackEntry = stack.removeLast();
						state = stackEntry.state;
						object = stackEntry.object;
						objectMapping = stackEntry.objectMapping;
						fieldMappingsArr = stackEntry.fieldMappingsArr;
						fieldMappingIdx = stackEntry.fieldMappingIdx;
						fieldMapping = stackEntry.fieldMapping;
						array = stackEntry.array;
						arrayIdx = stackEntry.arrayIdx;
						arrayLen = stackEntry.arrayLen;
					}
					else {
						bLoop = false;
					}
					break;
				case S_ARRAY_BEGIN:
					if ( bPretty ) {
						encoder.write( '[' );
						if ( arrayLen > 0 ) {
							encoder.write( '\n' );
							indentation += 2;
							if ( indentation > indentationArr.length ) {
								byte[] newIndentationArr = new byte[ indentationArr.length * 2 ];
								Arrays.fill( newIndentationArr, (byte)' ' );
								indentationArr = newIndentationArr;
							}
						}
					}
					else {
						encoder.write( '[' );
					}
					if ( arrayLen > 0 ) {
						state = S_ARRAY;
					}
					else {
						state = S_ARRAY_END;
					}
					break;
				case S_ARRAY_END:
					if ( bPretty ) {
						if ( arrayLen > 0 ) {
							indentation -= 2;
							encoder.write( '\n' );
							encoder.write( indentationArr, 0, indentation );
						}
					}
					encoder.write( ']' );
					if ( stack.size() > 0 ) {
						stackEntry = stack.removeLast();
						state = stackEntry.state;
						object = stackEntry.object;
						objectMapping = stackEntry.objectMapping;
						fieldMappingsArr = stackEntry.fieldMappingsArr;
						fieldMappingIdx = stackEntry.fieldMappingIdx;
						fieldMapping = stackEntry.fieldMapping;
						array = stackEntry.array;
						arrayIdx = stackEntry.arrayIdx;
						arrayLen = stackEntry.arrayLen;
					}
					else {
						bLoop = false;
					}
					break;
				case S_OBJECT:
					bFieldLoop = true;
					while ( bFieldLoop ) {
						if ( fieldMappingIdx < fieldMappingsArr.length ) {
							if ( fieldMappingIdx > 0 ) {
								if ( bPretty ) {
									encoder.write( ",\n" );
								}
								else {
									encoder.write( ',' );
								}
							}
							if ( bPretty ) {
								encoder.write( indentationArr, 0, indentation );
							}
							fieldMapping = fieldMappingsArr[ fieldMappingIdx++ ];
							encoder.write( '"' );
							encoder.write( fieldMapping.jsonName );
							encoder.write( '"' );
							if ( bPretty ) {
								encoder.write( ": " );
							}
							else {
								encoder.write( ':' );
							}
							switch ( fieldMapping.type ) {
							case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
								booleanVal = fieldMapping.field.getBoolean( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
									if ( booleanVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
									}
								}
								if ( booleanVal ) {
									encoder.write( trueBytes );
								}
								else {
									encoder.write( falseBytes );
								}
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
								intVal = fieldMapping.field.getInt( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
									if ( intVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
									}
								}
								encoder.write( intVal.toString().getBytes() );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
								longVal = fieldMapping.field.getLong( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
									if ( longVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
									}
								}
								encoder.write( longVal.toString().getBytes() );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
								floatVal = fieldMapping.field.getFloat( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
									if ( floatVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
									}
								}
								encoder.write( floatVal.toString().getBytes() );
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
								doubleVal = fieldMapping.field.getDouble( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
									if ( doubleVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
									}
								}
								encoder.write( doubleVal.toString().getBytes() );
								break;
							case JSONObjectMappingConstants.T_BOOLEAN:
								booleanVal = (Boolean)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
								}
								if ( booleanVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( booleanVal != null ) {
									if ( booleanVal ) {
										encoder.write( trueBytes );
									}
									else {
										encoder.write( falseBytes );
									}
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_INTEGER:
								intVal = (Integer)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
								}
								if ( intVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( intVal != null) {
									encoder.write( intVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_LONG:
								longVal = (Long)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
								}
								if ( longVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( longVal != null ) {
									encoder.write( longVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_FLOAT:
								floatVal = (Float)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
								}
								if ( floatVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( floatVal != null ) {
									encoder.write( floatVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_DOUBLE:
								doubleVal = (Double)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
								}
								if ( doubleVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( doubleVal != null ) {
									encoder.write( doubleVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_BIGINTEGER:
								bigIntegerVal = (BigInteger)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//bigIntegerVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigIntegerVal );
								}
								if ( bigIntegerVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( bigIntegerVal != null ) {
									encoder.write( bigIntegerVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_BIGDECIMAL:
								bigDecimalVal = (BigDecimal)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//bigDecimalVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigDecimalVal );
								}
								if ( bigDecimalVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( bigDecimalVal != null ) {
									encoder.write( bigDecimalVal.toString().getBytes() );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_STRING:
								stringVal = (String)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//strVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, strVal );
								}
								if ( stringVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( stringVal != null ) {
									encoder.write( '"' );
									encoder.write( stringVal );
									encoder.write( '"' );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_BYTEARRAY:
								byteArray = (byte[])fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//byteArray = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, byteArray );
								}
								if ( byteArray == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( byteArray != null ) {
									encoder.write( '"' );
									encoder.write( byteArray );
									encoder.write( '"' );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_OBJECT:
								tmpObject = (Object)fieldMapping.field.get( object );
								if ( tmpObject != null ) {
									stackEntry = new StackEntry();
									stackEntry.state = state;
									stackEntry.object = object;
									stackEntry.objectMapping = objectMapping;
									stackEntry.fieldMappingsArr = fieldMappingsArr;
									stackEntry.fieldMappingIdx = fieldMappingIdx;
									stackEntry.fieldMapping = fieldMapping;
									//arrayLen = stackEntry.arrayLen;
									stack.add( stackEntry );
									object = tmpObject;
									objectMapping = classMappings.get( object.getClass().getName() );
									if ( objectMapping == null ) {
										throw new IllegalArgumentException( "Class '" + object.getClass().getName() + "' not registered." );
									}
									if ( objectMapping.converters == true && converters == null ) {
										throw new JSONException( "Class '" + object.getClass().getName() + "' may required converters!" );
									}
									state = S_OBJECT_BEGIN;
									bFieldLoop = false;
								}
								else if ( !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_ARRAY:
								tmpArray = fieldMapping.field.get( object );
								if ( tmpArray != null ) {
									stackEntry = new StackEntry();
									stackEntry.state = state;
									stackEntry.object = object;
									stackEntry.objectMapping = objectMapping;
									stackEntry.fieldMappingsArr = fieldMappingsArr;
									stackEntry.fieldMappingIdx = fieldMappingIdx;
									stackEntry.fieldMapping = fieldMapping;
									//stackEntry.array = array;
									//arrayLen = stackEntry.arrayLen;
									stack.add( stackEntry );
									array = tmpArray;
									arrayIdx = 0;
									arrayLen = Array.getLength( array );
									/*
									objectMapping = classMappings.get( object.getClass().getName() );
									if ( objectMapping == null ) {
										throw new IllegalArgumentException( "Class '" + object.getClass().getName() + "' not registered." );
									}
									if ( objectMapping.converters == true && converters == null ) {
										throw new JSONException( "Class '" + object.getClass().getName() + "' may required converters!" );
									}
									*/
									state = S_ARRAY_BEGIN;
									bFieldLoop = false;
								}
								else if ( !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								else {
									encoder.write( nullBytes );
								}
							}
						}
						else {
							state = S_OBJECT_END;
							bFieldLoop = false;
						}
					}
					break;
				case S_ARRAY:
					switch ( fieldMapping.arrayType ) {
					case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
						arrayOf_boolean = (boolean[])array;
						while ( arrayIdx < arrayLen ) {
							booleanVal = arrayOf_boolean[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
								if ( booleanVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
								}
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( booleanVal ) {
								encoder.write( trueBytes );
							}
							else {
								encoder.write( falseBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
						arrayOf_int = (int[])array;
						while ( arrayIdx < arrayLen ) {
							intVal = arrayOf_int[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
								if ( intVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
								}
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							encoder.write( intVal.toString().getBytes() );
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
						arrayOf_long = (long[])array;
						while ( arrayIdx < arrayLen ) {
							longVal = arrayOf_long[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
								if ( longVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
								}
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							encoder.write( longVal.toString().getBytes() );
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
						arrayOf_float = (float[])array;
						while ( arrayIdx < arrayLen ) {
							floatVal = arrayOf_float[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
								if ( floatVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
								}
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							encoder.write( floatVal.toString().getBytes() );
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
						arrayOf_double = (double[])array;
						while ( arrayIdx < arrayLen ) {
							doubleVal = arrayOf_double[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
								if ( doubleVal == null ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
								}
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							encoder.write( doubleVal.toString().getBytes() );
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_BOOLEAN:
						arrayOf_Boolean = (Boolean[])array;
						while ( arrayIdx < arrayLen ) {
							booleanVal = arrayOf_Boolean[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
							}
							if ( booleanVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( booleanVal != null ) {
								if ( booleanVal ) {
									encoder.write( trueBytes );
								}
								else {
									encoder.write( falseBytes );
								}
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_INTEGER:
						arrayOf_Integer = (Integer[])array;
						while ( arrayIdx < arrayLen ) {
							intVal = arrayOf_Integer[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
							}
							if ( intVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( intVal != null ) {
								encoder.write( intVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_LONG:
						arrayOf_Long = (Long[])array;
						while ( arrayIdx < arrayLen ) {
							longVal = arrayOf_Long[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
							}
							if ( longVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( longVal != null ) {
								encoder.write( longVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_FLOAT:
						arrayOf_Float = (Float[])array;
						while ( arrayIdx < arrayLen ) {
							floatVal = arrayOf_Float[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
							}
							if ( floatVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( floatVal != null ) {
								encoder.write( floatVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_DOUBLE:
						arrayOf_Double = (Double[])array;
						while ( arrayIdx < arrayLen ) {
							doubleVal = arrayOf_Double[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
							}
							if ( doubleVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( doubleVal != null ) {
								encoder.write( doubleVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_BIGINTEGER:
						arrayOf_BigInteger = (BigInteger[])array;
						while ( arrayIdx < arrayLen ) {
							bigIntegerVal = arrayOf_BigInteger[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//bigIntegerVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigIntegerVal );
							}
							if ( bigIntegerVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( bigIntegerVal != null ) {
								encoder.write( bigIntegerVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_BIGDECIMAL:
						arrayOf_BigDecimal = (BigDecimal[])array;
						while ( arrayIdx < arrayLen ) {
							bigDecimalVal = arrayOf_BigDecimal[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//bigDecimalVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigDecimalVal );
							}
							if ( bigDecimalVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( bigDecimalVal != null ) {
								encoder.write( bigDecimalVal.toString().getBytes() );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_STRING:
						arrayOf_String = (String[])array;
						while ( arrayIdx < arrayLen ) {
							stringVal = arrayOf_String[ arrayIdx++ ];
							if ( fieldMapping.converterId != -1 ) {
								// TODO
								//strVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, strVal );
							}
							if ( stringVal == null && !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( stringVal != null ) {
								encoder.write( '"' );
								encoder.write( stringVal );
								encoder.write( '"' );
							}
							else {
								encoder.write( nullBytes );
							}
						}
						state = S_ARRAY_END;
						break;
					case JSONObjectMappingConstants.T_OBJECT:
						arrayOf_Object = (Object[])array;
						if ( arrayIdx < arrayLen ) {
							tmpObject = arrayOf_Object[ arrayIdx++ ];
							if ( bPretty ) {
								if ( arrayIdx > 1 ) {
									encoder.write( ",\n" );
								}
								encoder.write( indentationArr, 0, indentation );
							}
							else {
								if ( arrayIdx > 1 ) {
									encoder.write( ',' );
								}
							}
							if ( tmpObject != null ) {
								stackEntry = new StackEntry();
								stackEntry.state = state;
								stackEntry.object = object;
								stackEntry.objectMapping = objectMapping;
								stackEntry.fieldMappingsArr = fieldMappingsArr;
								stackEntry.fieldMappingIdx = fieldMappingIdx;
								stackEntry.fieldMapping = fieldMapping;
								stackEntry.array = array;
								stackEntry.arrayIdx = arrayIdx;
								stackEntry.arrayLen = arrayLen;
								//arrayLen = stackEntry.arrayLen;
								stack.add( stackEntry );
								object = tmpObject;
								objectMapping = classMappings.get( object.getClass().getName() );
								if ( objectMapping == null ) {
									throw new IllegalArgumentException( "Class '" + object.getClass().getName() + "' not registered." );
								}
								if ( objectMapping.converters == true && converters == null ) {
									throw new JSONException( "Class '" + object.getClass().getName() + "' may required converters!" );
								}
								state = S_OBJECT_BEGIN;
							}
							else if ( !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							else {
								encoder.write( nullBytes );
							}
							/*
							objectVal = arrayOf_Object[ arrayIdx ];
							if ( objectVal != null ) {
								objectVal = toJSON( objectVal, converters );
							}
							else if ( !fieldMapping.nullValues ) {
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
							}
							json_array.add( json_value );
							*/
						}
						else {
							state = S_ARRAY_END;
						}
						break;
					default:
						throw new JSONException( "Field '" + fieldMapping.fieldName + "' has an unsupported array type." );
					}
					break;
				}
			}
		}
		catch (IllegalAccessException e) {
			throw new JSONException( e );
		}

		encoder.close();
	}

}
