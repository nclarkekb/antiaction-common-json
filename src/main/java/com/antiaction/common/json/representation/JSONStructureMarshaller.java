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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;

import com.antiaction.common.json.JSONConstants;
import com.antiaction.common.json.JSONConverterAbstract;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectFieldMapping;
import com.antiaction.common.json.JSONObjectMapping;
import com.antiaction.common.json.JSONObjectMappingConstants;
import com.antiaction.common.json.JSONObjectMappings;

/**
 * Serialize Java Object(s) into a JSON structure.
 *
 * @author Nicholas
 * Created on 24/07/2013
 */
public class JSONStructureMarshaller {

	protected JSONObjectMappings objectMappings;

	protected Map<String, JSONObjectMapping> classMappings;

	public JSONStructureMarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
	}

	public <T> JSONCollection toJSONStructure(T srcObj) throws JSONException {
		return toJSONStructure( srcObj, null );
	}

	public <T> JSONCollection toJSONStructure(T srcObj, JSONConverterAbstract[] converters) throws JSONException {
		Boolean booleanVal;
		Integer intVal;
		Long longVal;
		Float floatVal;
		Double doubleVal;
		BigInteger bigIntegerVal;
		BigDecimal bigDecimalVal;
		String stringVal;
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

		JSONObjectMapping json_om = classMappings.get( srcObj.getClass().getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class '" + srcObj.getClass().getName() + "' not registered." );
		}
		if ( json_om.converters == true && converters == null ) {
			throw new JSONException( "Class '" + srcObj.getClass().getName() + "' may required converters!" );
		}

		JSONCollection json_struct = new JSONObject();
		JSONArray json_array;

		try {
			Iterator<JSONObjectFieldMapping> fieldMappingsIter = json_om.fieldMappingsList.iterator();
			JSONObjectFieldMapping fieldMapping;
			JSONValue json_value;
			int len;
			while ( fieldMappingsIter.hasNext() ) {
				fieldMapping = fieldMappingsIter.next();
				switch ( fieldMapping.type ) {
				case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
					booleanVal = fieldMapping.field.getBoolean( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						json_value = JSONBoolean.Boolean( booleanVal );
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
						if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
					intVal = fieldMapping.field.getInt( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						json_value = JSONNumber.Integer( intVal );
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
						if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
					longVal = fieldMapping.field.getLong( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						json_value = JSONNumber.Long( longVal );
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
						if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
					floatVal = fieldMapping.field.getFloat( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						json_value = JSONNumber.Float( floatVal );
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
						if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
					doubleVal = fieldMapping.field.getDouble( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						json_value = JSONNumber.Double( doubleVal );
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
						if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_BOOLEAN:
					booleanVal = (Boolean)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( booleanVal != null ) {
							json_value = JSONBoolean.Boolean( booleanVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_INTEGER:
					intVal = (Integer)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( intVal != null ) {
							json_value = JSONNumber.Integer( intVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_LONG:
					longVal = (Long)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( longVal != null ) {
							json_value = JSONNumber.Long( longVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_FLOAT:
					floatVal = (Float)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( floatVal != null ) {
							json_value = JSONNumber.Float( floatVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_DOUBLE:
					doubleVal = (Double)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( doubleVal != null ) {
							json_value = JSONNumber.Double( doubleVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_BIGINTEGER:
					bigIntegerVal = (BigInteger)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( bigIntegerVal != null ) {
							json_value = JSONNumber.BigInteger( bigIntegerVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigIntegerVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_BIGDECIMAL:
					bigDecimalVal = (BigDecimal)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( bigDecimalVal != null ) {
							json_value = JSONNumber.BigDecimal( bigDecimalVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigDecimalVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_STRING:
					stringVal = (String)fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( stringVal != null ) {
							json_value = JSONString.String( stringVal );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, stringVal );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_BYTEARRAY:
					byteArray = (byte[])fieldMapping.field.get( srcObj );
					if ( fieldMapping.converterId == -1 ) {
						if ( byteArray != null ) {
							json_value = JSONString.String( byteArray );
						}
						else {
							json_value = null;
						}
					}
					else {
						json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, byteArray );
					}
					if ( json_value == null ) {
						if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							json_value = JSONNull.Null;
						}
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_OBJECT:
					objectVal = (Object)fieldMapping.field.get( srcObj );
					if ( objectVal != null ) {
						json_value = toJSONStructure( objectVal, converters );
					}
					else if ( !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
					}
					else {
						json_value = JSONNull.Null;
					}
					json_struct.put( fieldMapping.jsonName, json_value );
					break;
				case JSONObjectMappingConstants.T_ARRAY:
					array_object = fieldMapping.field.get( srcObj );
					if ( array_object != null ) {
						len = Array.getLength( array_object );
						json_array = new JSONArray();
						switch ( fieldMapping.arrayType ) {
						case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
							arrayOf_boolean = (boolean[])array_object;
							for ( int i=0; i<len; ++i ) {
								booleanVal = arrayOf_boolean[ i ];
								if ( fieldMapping.converterId == -1 ) {
									json_value = JSONBoolean.Boolean( booleanVal );
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
									if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
							arrayOf_int = (int[])array_object;
							for ( int i=0; i<len; ++i ) {
								intVal = arrayOf_int[ i ];
								if ( fieldMapping.converterId == -1 ) {
									json_value = JSONNumber.Integer( intVal );
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
									if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
							arrayOf_long = (long[])array_object;
							for ( int i=0; i<len; ++i ) {
								longVal = arrayOf_long[ i ];
								if ( fieldMapping.converterId == -1 ) {
									json_value = JSONNumber.Long( longVal );
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
									if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
							arrayOf_float = (float[])array_object;
							for ( int i=0; i<len; ++i ) {
								floatVal = arrayOf_float[ i ];
								if ( fieldMapping.converterId == -1 ) {
									json_value = JSONNumber.Float( floatVal );
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
									if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
							arrayOf_double = (double[])array_object;
							for ( int i=0; i<len; ++i ) {
								doubleVal = arrayOf_double[ i ];
								if ( fieldMapping.converterId == -1 ) {
									json_value = JSONNumber.Double( doubleVal );
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
									if ( json_value == null || json_value.type == JSONConstants.VT_NULL ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_BOOLEAN:
							arrayOf_Boolean = (Boolean[])array_object;
							for ( int i=0; i<len; ++i ) {
								booleanVal = arrayOf_Boolean[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( booleanVal != null ) {
										json_value = JSONBoolean.Boolean( booleanVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, booleanVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_INTEGER:
							arrayOf_Integer = (Integer[])array_object;
							for ( int i=0; i<len; ++i ) {
								intVal = arrayOf_Integer[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( intVal != null ) {
										json_value = JSONNumber.Integer( intVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, intVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_LONG:
							arrayOf_Long = (Long[])array_object;
							for ( int i=0; i<len; ++i ) {
								longVal = arrayOf_Long[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( longVal != null ) {
										json_value = JSONNumber.Long( longVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, longVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_FLOAT:
							arrayOf_Float = (Float[])array_object;
							for ( int i=0; i<len; ++i ) {
								floatVal = arrayOf_Float[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( floatVal != null ) {
										json_value = JSONNumber.Float( floatVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, floatVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_DOUBLE:
							arrayOf_Double = (Double[])array_object;
							for ( int i=0; i<len; ++i ) {
								doubleVal = arrayOf_Double[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( doubleVal != null ) {
										json_value = JSONNumber.Double( doubleVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, doubleVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_BIGINTEGER:
							arrayOf_BigInteger = (BigInteger[])array_object;
							for ( int i=0; i<len; ++i ) {
								bigIntegerVal = arrayOf_BigInteger[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( bigIntegerVal != null ) {
										json_value = JSONNumber.BigInteger( bigIntegerVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigIntegerVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_BIGDECIMAL:
							arrayOf_BigDecimal = (BigDecimal[])array_object;
							for ( int i=0; i<len; ++i ) {
								bigDecimalVal = arrayOf_BigDecimal[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( bigDecimalVal != null ) {
										json_value = JSONNumber.BigDecimal( bigDecimalVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, bigDecimalVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_STRING:
							arrayOf_String = (String[])array_object;
							for ( int i=0; i<len; ++i ) {
								stringVal = arrayOf_String[ i ];
								if ( fieldMapping.converterId == -1 ) {
									if ( stringVal != null ) {
										json_value = JSONString.String( stringVal );
									}
									else {
										json_value = null;
									}
								}
								else {
									json_value = converters[ fieldMapping.converterId ].getJSONValue( fieldMapping.fieldName, stringVal );
								}
								if ( json_value == null ) {
									if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										json_value = JSONNull.Null;
									}
								}
								json_array.add( json_value );
							}
							break;
						case JSONObjectMappingConstants.T_OBJECT:
							arrayOf_Object = (Object[])array_object;
							for ( int i=0; i<len; ++i ) {
								objectVal = arrayOf_Object[ i ];
								if ( objectVal != null ) {
									json_value = toJSONStructure( objectVal, converters );
								}
								else if ( !fieldMapping.nullValues ) {
									throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
								}
								else {
									json_value = JSONNull.Null;
								}
								json_array.add( json_value );
							}
							break;
						default:
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' has an unsupported array type." );
						}
						json_value = json_array;
					}
					else if ( !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
					}
					else {
						json_value = JSONNull.Null;
					}
					json_struct.put( fieldMapping.jsonName, json_value );
				}
			}
		}
		catch (IllegalAccessException e) {
			throw new JSONException( e );
		}
		return json_struct;
	}

}
