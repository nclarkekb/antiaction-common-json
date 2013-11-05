/*
 * Created on 24/07/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * De-serialize a JSON structure into Java Object(s).
 *
 * @author Nicholas
 */
public class JSONStructureUnmarshaller {

	protected JSONObjectMappings objectMappings;

	protected Map<String, JSONObjectMapping> classMappings;

	public JSONStructureUnmarshaller(JSONObjectMappings objectMappings) {
		this.objectMappings = objectMappings;
		this.classMappings = objectMappings.classMappings;
	}

	public <T> T toObject(JSONStructure json_struct, Class<T> clazz) throws JSONException {
		return toObject( json_struct, clazz, null );
	}

	public <T> T toObject(JSONStructure json_struct, Class<T> clazz, JSONConverterAbstract[] converters) throws JSONException {
		Boolean booleanVal;
		Integer intVal;
		Long longVal;
		Float floatVal;
		Double doubleVal;
		BigInteger bigIntegerVal;
		BigDecimal bigDecimalVal;
		String stringVal;
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

		JSONObjectMapping json_om = classMappings.get( clazz.getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class '" + clazz.getName() + "' not registered." );
		}
		if ( json_om.converters == true && converters == null ) {
			throw new JSONException( "Class '" + clazz.getName() + "' may required converters!" );
		}

		JSONObject srcJSONObject = json_struct.getObject();
		T dstObj = null;

		try {
			dstObj = clazz.newInstance();

			Iterator<JSONObjectFieldMapping> fieldMappingsIter = json_om.fieldMappingsList.iterator();
			JSONObjectFieldMapping fieldMapping;
			JSONValue json_value;
			while ( fieldMappingsIter.hasNext() ) {
				fieldMapping = fieldMappingsIter.next();
				json_value = srcJSONObject.get( fieldMapping.jsonName );
				if ( json_value != null ) {
					switch ( fieldMapping.type ) {
					case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
						if ( fieldMapping.converterId == -1 ) {
							booleanVal = json_value.getBoolean();
						}
						else {
							booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
						}
						if ( booleanVal == null ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
						fieldMapping.field.setBoolean( dstObj, booleanVal );
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
						if ( fieldMapping.converterId == -1 ) {
							intVal = json_value.getInteger();
						}
						else {
							intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
						}
						if ( intVal == null ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
						fieldMapping.field.setInt( dstObj, intVal );
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
						if ( fieldMapping.converterId == -1 ) {
							longVal = json_value.getLong();
						}
						else {
							longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
						}
						if ( longVal == null ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
						fieldMapping.field.setLong( dstObj, longVal );
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
						if ( fieldMapping.converterId == -1 ) {
							floatVal = json_value.getFloat();
						}
						else {
							floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
						}
						if ( floatVal == null ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
						fieldMapping.field.setFloat( dstObj, floatVal );
						break;
					case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
						if ( fieldMapping.converterId == -1 ) {
							doubleVal = json_value.getDouble();
						}
						else {
							doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
						}
						if ( doubleVal == null ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not be null." );
						}
						fieldMapping.field.setDouble( dstObj, doubleVal );
						break;
					case JSONObjectMappingConstants.T_BOOLEAN:
						if ( fieldMapping.converterId == -1 ) {
							booleanVal = json_value.getBoolean();
						}
						else {
							booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
						}
						if ( booleanVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, booleanVal );
						break;
					case JSONObjectMappingConstants.T_INTEGER:
						if ( fieldMapping.converterId == -1 ) {
							intVal = json_value.getInteger();
						}
						else {
							intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
						}
						if ( intVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, intVal );
						break;
					case JSONObjectMappingConstants.T_LONG:
						if ( fieldMapping.converterId == -1 ) {
							longVal = json_value.getLong();
						}
						else {
							longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
						}
						if ( longVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, longVal );
						break;
					case JSONObjectMappingConstants.T_FLOAT:
						if ( fieldMapping.converterId == -1 ) {
							floatVal = json_value.getFloat();
						}
						else {
							floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
						}
						if ( floatVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, floatVal );
						break;
					case JSONObjectMappingConstants.T_DOUBLE:
						if ( fieldMapping.converterId == -1 ) {
							doubleVal = json_value.getDouble();
						}
						else {
							doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
						}
						if ( doubleVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, doubleVal );
						break;
					case JSONObjectMappingConstants.T_BIGINTEGER:
						if ( fieldMapping.converterId == -1 ) {
							bigIntegerVal = json_value.getBigInteger();
						}
						else {
							bigIntegerVal = converters[ fieldMapping.converterId ].getBigInteger( fieldMapping.fieldName, json_value );
						}
						if ( bigIntegerVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, bigIntegerVal );
						break;
					case JSONObjectMappingConstants.T_BIGDECIMAL:
						if ( fieldMapping.converterId == -1 ) {
							bigDecimalVal = json_value.getBigDecimal();
						}
						else {
							bigDecimalVal = converters[ fieldMapping.converterId ].getBigDecimal( fieldMapping.fieldName, json_value );
						}
						if ( bigDecimalVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, bigDecimalVal );
						break;
					case JSONObjectMappingConstants.T_STRING:
						if ( fieldMapping.converterId == -1 ) {
							stringVal = json_value.getString();
						}
						else {
							stringVal = converters[ fieldMapping.converterId ].getString( fieldMapping.fieldName, json_value );
						}
						if ( stringVal == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, stringVal );
						break;
					case JSONObjectMappingConstants.T_BYTEARRAY:
						if ( fieldMapping.converterId == -1 ) {
							byteArray = json_value.getBytes();
						}
						else {
							byteArray = converters[ fieldMapping.converterId ].getBytes( fieldMapping.fieldName, json_value );
						}
						if ( byteArray == null && !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						fieldMapping.field.set( dstObj, byteArray );
						break;
					case JSONObjectMappingConstants.T_OBJECT:
						json_object = json_value.getObject();
						if ( json_object != null ) {
							object = toObject( json_object, fieldMapping.clazz, converters );
						}
						else if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							object = null;
						}
						fieldMapping.field.set( dstObj, object );
						break;
					case JSONObjectMappingConstants.T_ARRAY:
						json_array = json_value.getArray();
						if ( json_array != null ) {
							switch (  fieldMapping.arrayType ) {
							case JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN:
								json_values = json_array.values;
								arrayOf_boolean = new boolean[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										booleanVal = json_value.getBoolean();
									}
									else {
										booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
									}
									if ( booleanVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
									arrayOf_boolean[ i ] = booleanVal;
								}
								object = arrayOf_boolean;
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_INTEGER:
								json_values = json_array.values;
								arrayOf_int = new int[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										intVal = json_value.getInteger();
									}
									else {
										intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
									}
									if ( intVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
									arrayOf_int[ i ] = intVal;
								}
								object = arrayOf_int;
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_LONG:
								json_values = json_array.values;
								arrayOf_long = new long[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										longVal = json_value.getLong();
									}
									else {
										longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
									}
									if ( longVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
									arrayOf_long[ i ] = longVal;
								}
								object = arrayOf_long;
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_FLOAT:
								json_values = json_array.values;
								arrayOf_float = new float[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										floatVal = json_value.getFloat();
									}
									else {
										floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
									}
									if ( floatVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
									arrayOf_float[ i ] = floatVal;
								}
								object = arrayOf_float;
								break;
							case JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE:
								json_values = json_array.values;
								arrayOf_double = new double[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										doubleVal = json_value.getDouble();
									}
									else {
										doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
									}
									if ( doubleVal == null ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' is primitive and can not have null values." );
									}
									arrayOf_double[ i ] =  doubleVal;
								}
								object = arrayOf_double;
								break;
							case JSONObjectMappingConstants.T_BOOLEAN:
								json_values = json_array.values;
								arrayOf_Boolean = new Boolean[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										booleanVal = json_value.getBoolean();
									}
									else {
										booleanVal = converters[ fieldMapping.converterId ].getBoolean( fieldMapping.fieldName, json_value );
									}
									if ( booleanVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_Boolean[ i ] = booleanVal;
								}
								object = arrayOf_Boolean;
								break;
							case JSONObjectMappingConstants.T_INTEGER:
								json_values = json_array.values;
								arrayOf_Integer = new Integer[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										intVal = json_value.getInteger();
									}
									else {
										intVal = converters[ fieldMapping.converterId ].getInteger( fieldMapping.fieldName, json_value );
									}
									if ( intVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_Integer[ i ] = intVal;
								}
								object = arrayOf_Integer;
								break;
							case JSONObjectMappingConstants.T_LONG:
								json_values = json_array.values;
								arrayOf_Long = new Long[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										longVal = json_value.getLong();
									}
									else {
										longVal = converters[ fieldMapping.converterId ].getLong( fieldMapping.fieldName, json_value );
									}
									if ( longVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_Long[ i ] = longVal;
								}
								object = arrayOf_Long;
								break;
							case JSONObjectMappingConstants.T_FLOAT:
								json_values = json_array.values;
								arrayOf_Float = new Float[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										floatVal = json_value.getFloat();
									}
									else {
										floatVal = converters[ fieldMapping.converterId ].getFloat( fieldMapping.fieldName, json_value );
									}
									if ( floatVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_Float[ i ] = floatVal;
								}
								object = arrayOf_Float;
								break;
							case JSONObjectMappingConstants.T_DOUBLE:
								json_values = json_array.values;
								arrayOf_Double = new Double[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										doubleVal = json_value.getDouble();
									}
									else {
										doubleVal = converters[ fieldMapping.converterId ].getDouble( fieldMapping.fieldName, json_value );
									}
									if ( doubleVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_Double[ i ] = doubleVal;
								}
								object = arrayOf_Double;
								break;
							case JSONObjectMappingConstants.T_BIGINTEGER:
								json_values = json_array.values;
								arrayOf_BigInteger = new BigInteger[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										bigIntegerVal = json_value.getBigInteger();
									}
									else {
										bigIntegerVal = converters[ fieldMapping.converterId ].getBigInteger( fieldMapping.fieldName, json_value );
									}
									if ( bigIntegerVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_BigInteger[ i ] = bigIntegerVal;
								}
								object = arrayOf_BigInteger;
								break;
							case JSONObjectMappingConstants.T_BIGDECIMAL:
								json_values = json_array.values;
								arrayOf_BigDecimal = new BigDecimal[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										bigDecimalVal = json_value.getBigDecimal();
									}
									else {
										bigDecimalVal = converters[ fieldMapping.converterId ].getBigDecimal( fieldMapping.fieldName, json_value );
									}
									if ( bigDecimalVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_BigDecimal[ i ] = bigDecimalVal;
								}
								object = arrayOf_BigDecimal;
								break;
							case JSONObjectMappingConstants.T_STRING:
								json_values = json_array.values;
								arrayOf_String = new String[ json_values.size() ];
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									if ( fieldMapping.converterId == -1 ) {
										stringVal = json_value.getString();
									}
									else {
										stringVal = converters[ fieldMapping.converterId ].getString( fieldMapping.fieldName, json_value );
									}
									if ( stringVal == null && !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									arrayOf_String[ i ] = stringVal;
								}
								object = arrayOf_String;
								break;
							case JSONObjectMappingConstants.T_OBJECT:
								json_values = json_array.values;
								arrayOf_Object = (Object[])Array.newInstance( fieldMapping.clazz, json_array.values.size() );
								for ( int i=0; i<json_values.size(); ++i ) {
									json_value = json_values.get( i );
									json_object = json_value.getObject();
									if ( json_object != null ) {
										object = toObject( json_object, fieldMapping.clazz, converters );
									}
									else if ( !fieldMapping.nullValues ) {
										throw new JSONException( "Field '" + fieldMapping.fieldName + "' does not allow null values." );
									}
									else {
										object = null;
									}
									arrayOf_Object[ i ] = object;
								}
								object = arrayOf_Object;
								break;
							default:
								throw new JSONException( "Field '" + fieldMapping.fieldName + "' has an unsupported array type." );
							}
						}
						else if ( !fieldMapping.nullable ) {
							throw new JSONException( "Field '" + fieldMapping.fieldName + "' is not nullable." );
						}
						else {
							object = null;
						}
						fieldMapping.field.set( dstObj, object );
					}
				}
				else {
					if ( !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.fieldName + "'/'" + fieldMapping.jsonName + "' is not nullable." );
					}
				}
			}
		}
		catch (InstantiationException e) {
			throw new JSONException( e );
		}
		catch (IllegalAccessException e) {
			throw new JSONException( e );
		}
		return dstObj;
	}

}
