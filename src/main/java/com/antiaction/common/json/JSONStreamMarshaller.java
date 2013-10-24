/*
 * Created on 17/10/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

/**
 * Serialize Java Object(s) into a JSON data stream.
 *
 * @author Nicholas
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

	private static class StackEntry {
		Object object;
		JSONObjectMapping objectMapping;
		int state;
		//int indentation;
		JSONObjectFieldMapping[] fieldMappingsArr;
		int fieldMappingIdx;
		//JSONObjectFieldMapping fieldMapping;
		int len;
	}

	public JSONStreamMarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
		indentationArr = new byte[ 32 ];
		Arrays.fill( indentationArr, (byte)' ' );
	}

	public <T> void toJSON(T srcObj, JSONEncoder encoder, boolean bPretty, OutputStream out) throws JSONException, IOException {
		toJSON( srcObj, null, encoder, bPretty, out );
	}

	public <T> void toJSON(T srcObj, JSONConverterAbstract[] converters, JSONEncoder encoder, boolean bPretty, OutputStream out) throws JSONException, IOException {
		Boolean booleanVal;
		Integer intVal;
		Long longVal;
		Float floatVal;
		Double doubleVal;
		BigInteger bigIntegerVal;
		BigDecimal bigDecimalVal;
		String strVal;
		byte[] byteArray;
		Object objectVal;

		Object array_object;
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
		JSONObjectFieldMapping fieldMapping;
		int len = 0;

		Stack<StackEntry> stack = new Stack<StackEntry>();
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
						encoder.write( "\n" );
						encoder.write( indentationArr, 0, indentation );
					}
					encoder.write( "}" );
					if ( stack.size() > 0 ) {
						stackEntry = stack.pop();
						object = stackEntry.object;
						objectMapping = stackEntry.objectMapping;
						state = stackEntry.state;
						//indentation = stackEntry.indentation;
						fieldMappingsArr = stackEntry.fieldMappingsArr;
						fieldMappingIdx = stackEntry.fieldMappingIdx;
						//fieldMapping = stackEntry.fieldMapping;
						len = stackEntry.len;
					}
					else {
						bLoop = false;
					}
					break;
				case S_ARRAY_BEGIN:
					if ( bPretty ) {
						encoder.write( "[\n" );
						indentation += 2;
						if ( indentation > indentationArr.length ) {
							byte[] newIndentationArr = new byte[ indentationArr.length * 2 ];
							Arrays.fill( newIndentationArr, (byte)' ' );
							indentationArr = newIndentationArr;
						}
					}
					else {
						encoder.write( '[' );
					}
					state = S_ARRAY;
					break;
				case S_ARRAY_END:
					if ( bPretty ) {
						indentation -= 2;
						encoder.write( "\n" );
						encoder.write( indentationArr, 0, indentation );
					}
					encoder.write( ']' );
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
							encoder.write( fieldMapping.jsonName );
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
								strVal = (String)fieldMapping.field.get( object );
								if ( fieldMapping.converterId != -1 ) {
									// TODO
									//strVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, strVal );
								}
								if ( strVal == null && !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( strVal != null ) {
									encoder.write( '"' );
									encoder.write( strVal );
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
								objectVal = (Object)fieldMapping.field.get( object );
								if ( objectVal != null ) {
									// TODO
									//objectVal = toJSON( objectVal, converters );
								}
								else if ( !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
								}
								if ( objectVal != null ) {
									stackEntry = new StackEntry();
									stackEntry.object = object;
									stackEntry.objectMapping = objectMapping;
									stackEntry.state = S_OBJECT_END;
									//stackEntry.indentation = indentation;
									stackEntry.fieldMappingsArr = fieldMappingsArr;
									stackEntry.fieldMappingIdx = fieldMappingIdx;
									//stackEntry.fieldMapping = fieldMapping;
									len = stackEntry.len;
									stack.push( stackEntry );
									object = objectVal;
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
								else {
									encoder.write( nullBytes );
								}
								break;
							case JSONObjectMappingConstants.T_ARRAY:
								array_object = fieldMapping.field.get( object );
								if ( array_object != null ) {
									len = Array.getLength( array_object );
									switch ( fieldMapping.arrayType ) {
									case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
										arrayOf_boolean = (boolean[])array_object;
										for ( int i=0; i<len; ++i ) {
											booleanVal = arrayOf_boolean[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
												if ( booleanVal == null ) {
													throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
												}
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( booleanVal ) {
												encoder.write( trueBytes );
											}
											else {
												encoder.write( falseBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
										arrayOf_int = (int[])array_object;
										for ( int i=0; i<len; ++i ) {
											intVal = arrayOf_int[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
												if ( intVal == null ) {
													throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
												}
											}
											encoder.write( intVal.toString().getBytes() );
											encoder.write( ',' );
										}
										break;
									case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
										arrayOf_long = (long[])array_object;
										for ( int i=0; i<len; ++i ) {
											longVal = arrayOf_long[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
												if ( longVal == null ) {
													throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
												}
											}
											encoder.write( longVal.toString().getBytes() );
											encoder.write( ',' );
										}
										break;
									case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
										arrayOf_float = (float[])array_object;
										for ( int i=0; i<len; ++i ) {
											floatVal = arrayOf_float[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
												if ( floatVal == null ) {
													throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
												}
											}
											encoder.write( floatVal.toString().getBytes() );
											encoder.write( ',' );
										}
										break;
									case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
										arrayOf_double = (double[])array_object;
										for ( int i=0; i<len; ++i ) {
											doubleVal = arrayOf_double[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
												if ( doubleVal == null ) {
													throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
												}
											}
											encoder.write( doubleVal.toString().getBytes() );
											encoder.write( ',' );
										}
										break;
									case JSONObjectMappingConstants.T_BOOLEAN:
										arrayOf_Boolean = (Boolean[])array_object;
										for ( int i=0; i<len; ++i ) {
											booleanVal = arrayOf_Boolean[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//booleanVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
											}
											if ( booleanVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
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
										break;
									case JSONObjectMappingConstants.T_INTEGER:
										arrayOf_Integer = (Integer[])array_object;
										for ( int i=0; i<len; ++i ) {
											intVal = arrayOf_Integer[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//intVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
											}
											if ( intVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( intVal != null ) {
												encoder.write( intVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_LONG:
										arrayOf_Long = (Long[])array_object;
										for ( int i=0; i<len; ++i ) {
											longVal = arrayOf_Long[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//longVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
											}
											if ( longVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( longVal != null ) {
												encoder.write( longVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_FLOAT:
										arrayOf_Float = (Float[])array_object;
										for ( int i=0; i<len; ++i ) {
											floatVal = arrayOf_Float[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//floatVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
											}
											if ( floatVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( floatVal != null ) {
												encoder.write( floatVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_DOUBLE:
										arrayOf_Double = (Double[])array_object;
										for ( int i=0; i<len; ++i ) {
											doubleVal = arrayOf_Double[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//doubleVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
											}
											if ( doubleVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( doubleVal != null ) {
												encoder.write( doubleVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_BIGINTEGER:
										arrayOf_BigInteger = (BigInteger[])array_object;
										for ( int i=0; i<len; ++i ) {
											bigIntegerVal = arrayOf_BigInteger[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//bigIntegerVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigIntegerVal );
											}
											if ( bigIntegerVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( bigIntegerVal != null ) {
												encoder.write( bigIntegerVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_BIGDECIMAL:
										arrayOf_BigDecimal = (BigDecimal[])array_object;
										for ( int i=0; i<len; ++i ) {
											bigDecimalVal = arrayOf_BigDecimal[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//bigDecimalVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigDecimalVal );
											}
											if ( bigDecimalVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( bigDecimalVal != null ) {
												encoder.write( bigDecimalVal.toString().getBytes() );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_STRING:
										arrayOf_String = (String[])array_object;
										for ( int i=0; i<len; ++i ) {
											strVal = arrayOf_String[ i ];
											if ( fieldMapping.converterId != -1 ) {
												// TODO
												//strVal = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, strVal );
											}
											if ( strVal == null && !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											if ( i > 0 ) {
												encoder.write( ',' );
											}
											if ( strVal != null ) {
												encoder.write( '"' );
												encoder.write( strVal );
												encoder.write( '"' );
											}
											else {
												encoder.write( nullBytes );
											}
										}
										break;
									case JSONObjectMappingConstants.T_OBJECT:
										arrayOf_Object = (Object[])array_object;
										for ( int i=0; i<len; ++i ) {
											/*
											objectVal = arrayOf_Object[ i ];
											if ( objectVal != null ) {
												objectVal = toJSON( objectVal, converters );
											}
											else if ( !fieldMapping.nullValues ) {
												throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
											}
											json_array.add( json_value );
											*/
										}
										break;
									default:
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' has an unsupported array type." );
									}
								}
								else if ( !fieldMapping.nullable ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
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
