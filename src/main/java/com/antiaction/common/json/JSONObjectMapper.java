/*
 * Created on 13/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONNullable;

public class JSONObjectMapper {

	public static final int T_PRIMITIVE_BOOLEAN = 1;
	public static final int T_PRIMITIVE_INTEGER = 2;
	public static final int T_PRIMITIVE_LONG = 3;
	public static final int T_PRIMITIVE_FLOAT = 4;
	public static final int T_PRIMITIVE_DOUBLE = 5;
	public static final int T_OBJECT = 100;
	public static final int T_BOOLEAN = 101;
	public static final int T_INTEGER = 102;
	public static final int T_LONG = 103;
	public static final int T_FLOAT = 104;
	public static final int T_DOUBLE = 105;
	public static final int T_BIGINTEGER = 106;
	public static final int T_BIGDECIMAL = 107;
	public static final int T_STRING = 108;
	public static final int T_BYTEARRAY = 109;
	public static final int T_ARRAY = 110;

	// TODO maybe better not static.
	protected static Map<String, JSONObjectMapping> classMappings = new TreeMap<String, JSONObjectMapping>();

	protected static Map<String, Integer> typeMappings = new TreeMap<String, Integer>();

	protected static Map<String, Integer> arrayTypeMappings = new TreeMap<String, Integer>();

	static {
		typeMappings.put( boolean.class.getName(), T_PRIMITIVE_BOOLEAN );
		typeMappings.put( int.class.getName(), T_PRIMITIVE_INTEGER );
		typeMappings.put( long.class.getName(), T_PRIMITIVE_LONG );
		typeMappings.put( float.class.getName(), T_PRIMITIVE_FLOAT );
		typeMappings.put( double.class.getName(), T_PRIMITIVE_DOUBLE );
		typeMappings.put( Boolean.class.getName(), T_BOOLEAN );
		typeMappings.put( Integer.class.getName(), T_INTEGER );
		typeMappings.put( Long.class.getName(), T_LONG );
		typeMappings.put( Float.class.getName(), T_FLOAT );
		typeMappings.put( Double.class.getName(), T_DOUBLE );
		typeMappings.put( BigInteger.class.getName(), T_BIGINTEGER );
		typeMappings.put( BigDecimal.class.getName(), T_BIGDECIMAL );
		typeMappings.put( String.class.getName(), T_STRING );
		typeMappings.put( byte[].class.getName(), T_BYTEARRAY );

		arrayTypeMappings.put( boolean[].class.getName(), T_PRIMITIVE_BOOLEAN );
		arrayTypeMappings.put( int[].class.getName(), T_PRIMITIVE_INTEGER );
		arrayTypeMappings.put( long[].class.getName(), T_PRIMITIVE_LONG );
		arrayTypeMappings.put( float[].class.getName(), T_PRIMITIVE_FLOAT );
		arrayTypeMappings.put( double[].class.getName(), T_PRIMITIVE_DOUBLE );
		arrayTypeMappings.put( Boolean[].class.getName(), T_BOOLEAN );
		arrayTypeMappings.put( Integer[].class.getName(), T_INTEGER );
		arrayTypeMappings.put( Long[].class.getName(), T_LONG );
		arrayTypeMappings.put( Float[].class.getName(), T_FLOAT );
		arrayTypeMappings.put( Double[].class.getName(), T_DOUBLE );
		arrayTypeMappings.put( BigInteger[].class.getName(), T_BIGINTEGER );
		arrayTypeMappings.put( BigDecimal[].class.getName(), T_BIGDECIMAL );
		arrayTypeMappings.put( String[].class.getName(), T_STRING );
	}

	public static final int CLASS_INVALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ANNOTATION
			| ClassTypeModifiers.CT_ANONYMOUSCLASS
			| ClassTypeModifiers.CT_ARRAY
			| ClassTypeModifiers.CT_ENUM
			| ClassTypeModifiers.CT_INTERFACE
			| ClassTypeModifiers.CT_LOCALCLASS
			| ClassTypeModifiers.CT_PRIMITIVE
			| ClassTypeModifiers.CM_ABSTRACT
			| ClassTypeModifiers.CM_NATIVE;

	public static final int CLASS_VALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_MEMBERCLASS
			| ClassTypeModifiers.CT_CLASS
			| ClassTypeModifiers.CM_STATIC;

	public static final int VALID_CLASS = ClassTypeModifiers.CT_CLASS;

	public static final int VALID_MEMBER_CLASS = ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_STATIC;

	public static final int VALID_ARRAY_CLASS = ClassTypeModifiers.CT_ARRAY | ClassTypeModifiers.CM_ABSTRACT;

	// ClassNotFoundException, SecurityException, NoSuchFieldException
	public void register(Class<?> clazz) throws JSONException {
		int classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( clazz );
		if ( (classTypeMask & CLASS_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
			throw new JSONException( "Unsupported class type." );
		}
		classTypeMask &= CLASS_VALID_TYPE_MODIFIERS_MASK;
		if ( (classTypeMask != VALID_CLASS) && (classTypeMask != VALID_MEMBER_CLASS) ) {
			throw new JSONException( "Unsupported class type." );
		}
		if ( !classMappings.containsKey( clazz.getName() ) ) {
			mapClass( clazz );
		}
	}

	public static final int FIELD_IGNORE_TYPE_MODIFIER = ClassTypeModifiers.CM_STATIC
			| ClassTypeModifiers.CM_TRANSIENT;

	// TODO support List/Set interface but required impl class annotation.
	public static final int FIELD_INVALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ANNOTATION
			| ClassTypeModifiers.CT_ANONYMOUSCLASS
			| ClassTypeModifiers.CT_ENUM
			| ClassTypeModifiers.CT_INTERFACE
			| ClassTypeModifiers.CT_LOCALCLASS
			| ClassTypeModifiers.CT_PRIMITIVE;

	public static final int FIELD_VALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ARRAY
			| ClassTypeModifiers.CT_MEMBERCLASS
			| ClassTypeModifiers.CT_CLASS
			| ClassTypeModifiers.CM_ABSTRACT
			| ClassTypeModifiers.CM_STATIC;

	// ClassNotFoundException, SecurityException, NoSuchFieldException
	protected JSONObjectMapping mapClass(Class<?> clazz) throws JSONException {
		try {
			JSONObjectFieldMapping json_fm;
			JSON json;
			JSONIgnore ignore;
			JSONNullable nullable;

			JSONObjectMapping json_om = new JSONObjectMapping();
			classMappings.put( clazz.getName(), json_om );

			json = clazz.getAnnotation( JSON.class );
			if ( json != null ) {
				// debug
				//System.out.println( "json" );
				String[] ignores = json.ignore();
				for ( int i=0; i<ignores.length; ++i) {
					json_om.ignore.add( ignores[ i ] );
				}
			}

			Field[] fields = clazz.getDeclaredFields();
			Field field;
			boolean bIgnore;
			Class<?> fieldType = null;
			String fieldTypeName;
			Integer type;
			Integer arrayType = 0;
			int fieldModsMask = 0;
			int classTypeMask = 0;
			JSONObjectMapping fieldObjectMapping;
			for ( int i=0; i<fields.length; ++i ) {
				field = fields[ i ];
				// debug
				//System.out.println( field.getName() );
				bIgnore = json_om.ignore.contains( field.getName() );
				ignore = field.getAnnotation( JSONIgnore.class );
				if ( ignore != null ) {
					// debug
					//System.out.println( "ignore" );
					bIgnore = true;
				}
				if ( !bIgnore ) {
					fieldModsMask = ClassTypeModifiers.getFieldModifiersMask( field );
					// debug
					System.out.println( field.getName() + " - " + ClassTypeModifiers.toString( fieldModsMask ) );
					bIgnore = (fieldModsMask & FIELD_IGNORE_TYPE_MODIFIER) != 0;
				}
				if ( !bIgnore ) {
					fieldType = field.getType();
					fieldTypeName = fieldType.getName();
					classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( fieldType );
					// debug
					System.out.println( fieldTypeName + " " + ClassTypeModifiers.toString( classTypeMask ) );

					type = typeMappings.get( fieldTypeName );
					fieldObjectMapping = null;
					if ( type == null ) {
						if ( (classTypeMask & FIELD_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
							throw new JSONException( "Unsupported field class type." );
						}
						classTypeMask &= FIELD_VALID_TYPE_MODIFIERS_MASK;
						if ( (classTypeMask == VALID_CLASS) || (classTypeMask == VALID_MEMBER_CLASS) ) {
							type = T_OBJECT;
							fieldObjectMapping = classMappings.get( fieldTypeName );
							if ( fieldObjectMapping == null ) {
								fieldObjectMapping = mapClass( Class.forName( fieldTypeName, true, clazz.getClassLoader() ) );
							}

							Type genericType = field.getGenericType();
							System.out.println( "GT: " + genericType + " " + genericType.getClass() );
							if( genericType instanceof ParameterizedType ) {
								ParameterizedType parameterizedType = (ParameterizedType)genericType;
								Type[] typeArguments = parameterizedType.getActualTypeArguments();
								for ( Type typeArgument : typeArguments ) {
									Class<?> classType = ((Class<?>)typeArgument);
									System.out.println( "Field " + field.getName() + " has a parameterized type of " + classType.getSimpleName() );
								}
							}

						}
						else if ( classTypeMask == VALID_ARRAY_CLASS ) {
							type = T_ARRAY;
							arrayType = arrayTypeMappings.get( fieldTypeName );
							if ( arrayType == null ) {
								/*
								int level = 0;
								while ( level < fieldTypeName.length() && fieldTypeName.charAt( level ) == '[' ) {
									++level;
								}
								String typeStr = fieldTypeName.substring( level );
								*/
								throw new JSONException( "Unsupported array class type." );
							}
							System.out.println( "ARRAY!" );
						}
						else {
							throw new JSONException( "Unsupported field class type." );
						}
					}
					// debug
					//System.out.println( type );

					if ( type != null ) {
						json_fm = new JSONObjectFieldMapping();
						json_fm.name = field.getName();
						json_fm.type = type;
						json_fm.arrayType = arrayType;
						json_fm.className = fieldTypeName;
						json_fm.clazz = fieldType;
						json_fm.objectMapping = fieldObjectMapping;
						json_fm.field = clazz.getDeclaredField( json_fm.name );
						json_fm.field.setAccessible( true );
						json_om.fieldMappingsMap.put( json_fm.name, json_fm );
						json_om.fieldMappingsList.add( json_fm );

						nullable = field.getAnnotation( JSONNullable.class );
						if ( nullable != null ) {
							if ( json_fm.type < T_OBJECT ) {
								throw new JSONException( "Primitive types can not be nullable." );
							}
							// debug
							//System.out.println( "nullable" );
							json_fm.nullable = true;
						}
					}
				}
			}
			return json_om;
		}
		catch (Exception e) {
			throw new JSONException( e );
		}
	}

	public <T> T toObject(JSONStructure json_struct, Class<T> clazz) throws InstantiationException, IllegalAccessException, JSONException {
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

		JSONObjectMapping json_om = classMappings.get( clazz.getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class '" + clazz.getName() + "'not registered." );
		}

		JSONObject srcJSONObject = json_struct.getObject();
		T dstObj = clazz.newInstance();

		Iterator<JSONObjectFieldMapping> fieldMappingsIter = json_om.fieldMappingsList.iterator();
		JSONObjectFieldMapping fieldMapping;
		JSONValue json_value;
		while ( fieldMappingsIter.hasNext() ) {
			fieldMapping = fieldMappingsIter.next();
			json_value = srcJSONObject.get( fieldMapping.name );
			if ( json_value != null ) {
				switch ( fieldMapping.type ) {
				case T_PRIMITIVE_BOOLEAN:
					booleanVal = json_value.getBoolean();
					if ( booleanVal == null ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is primitive and can not be null." );
					}
					fieldMapping.field.setBoolean( dstObj, booleanVal );
					break;
				case T_PRIMITIVE_INTEGER:
					intVal = json_value.getInteger();
					if ( intVal == null ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is primitive and can not be null." );
					}
					fieldMapping.field.setInt( dstObj, intVal );
					break;
				case T_PRIMITIVE_LONG:
					longVal = json_value.getLong();
					if ( longVal == null ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is primitive and can not be null." );
					}
					fieldMapping.field.setLong( dstObj, longVal );
					break;
				case T_PRIMITIVE_FLOAT:
					floatVal = json_value.getFloat();
					if ( floatVal == null ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is primitive and can not be null." );
					}
					fieldMapping.field.setFloat( dstObj, floatVal );
					break;
				case T_PRIMITIVE_DOUBLE:
					doubleVal = json_value.getDouble();
					if ( doubleVal == null ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is primitive and can not be null." );
					}
					fieldMapping.field.setDouble( dstObj, doubleVal );
					break;
				case T_BOOLEAN:
					booleanVal = json_value.getBoolean();
					if ( booleanVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, booleanVal );
					break;
				case T_INTEGER:
					intVal = json_value.getInteger();
					if ( intVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, intVal );
					break;
				case T_LONG:
					longVal = json_value.getLong();
					if ( longVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, longVal );
					break;
				case T_FLOAT:
					floatVal = json_value.getFloat();
					if ( floatVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, floatVal );
					break;
				case T_DOUBLE:
					doubleVal = json_value.getDouble();
					if ( doubleVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, doubleVal );
					break;
				case T_BIGINTEGER:
					bigIntegerVal = json_value.getBigInteger();
					if ( bigIntegerVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, bigIntegerVal );
					break;
				case T_BIGDECIMAL:
					bigDecimalVal = json_value.getBigDecimal();
					if ( bigDecimalVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, bigDecimalVal );
					break;
				case T_STRING:
					strVal = json_value.getString();
					if ( strVal == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, strVal );
					break;
				case T_BYTEARRAY:
					byteArray = json_value.getBytes();
					if ( byteArray == null && !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					fieldMapping.field.set( dstObj, byteArray );
					break;
				case T_OBJECT:
					json_object = json_value.getObject();
					if ( json_object != null ) {
						object = toObject( json_object, fieldMapping.clazz );
					}
					else if ( !fieldMapping.nullable ) {
						throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
					}
					else {
						object = null;
					}
					fieldMapping.field.set( dstObj, object );
					break;
				case T_ARRAY:
					throw new UnsupportedOperationException( "Not implemented!" );
				}
			}
			else {
				if ( !fieldMapping.nullable ) {
					throw new JSONException( "Field '" + fieldMapping.name + "' is not nullable." );
				}
			}
		}

		return dstObj;
	}

	public <T> JSONStructure toJSON(T srcObj) throws JSONException, IllegalArgumentException, IllegalAccessException {
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

		JSONObjectMapping json_om = classMappings.get( srcObj.getClass().getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class not registered." );
		}

		JSONStructure json_struct = new JSONObject();

		Iterator<JSONObjectFieldMapping> fieldMappingsIter = json_om.fieldMappingsList.iterator();
		JSONObjectFieldMapping fieldMapping;
		JSONValue json_value;
		while ( fieldMappingsIter.hasNext() ) {
			fieldMapping = fieldMappingsIter.next();
			switch ( fieldMapping.type ) {
			case T_PRIMITIVE_BOOLEAN:
				booleanVal = fieldMapping.field.getBoolean( srcObj );
				json_value = JSONBoolean.Boolean( booleanVal );
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_PRIMITIVE_INTEGER:
				intVal = fieldMapping.field.getInt( srcObj );
				json_value = JSONNumber.Integer( intVal );
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_PRIMITIVE_LONG:
				longVal = fieldMapping.field.getLong( srcObj );
				json_value = JSONNumber.Long( longVal );
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_PRIMITIVE_FLOAT:
				floatVal = fieldMapping.field.getFloat( srcObj );
				json_value = JSONNumber.Float( floatVal );
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_PRIMITIVE_DOUBLE:
				doubleVal = fieldMapping.field.getDouble( srcObj );
				json_value = JSONNumber.Double( doubleVal );
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_BOOLEAN:
				booleanVal = (Boolean)fieldMapping.field.get( srcObj );
				if ( booleanVal != null ) {
					json_value = JSONBoolean.Boolean( booleanVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_INTEGER:
				intVal = (Integer)fieldMapping.field.get( srcObj );
				if ( intVal != null ) {
					json_value = JSONNumber.Integer( intVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_LONG:
				longVal = (Long)fieldMapping.field.get( srcObj );
				if ( longVal != null ) {
					json_value = JSONNumber.Long( longVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_FLOAT:
				floatVal = (Float)fieldMapping.field.get( srcObj );
				if ( floatVal != null ) {
					json_value = JSONNumber.Float( floatVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_DOUBLE:
				doubleVal = (Double)fieldMapping.field.get( srcObj );
				if ( doubleVal != null ) {
					json_value = JSONNumber.Double( doubleVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_BIGINTEGER:
				bigIntegerVal = (BigInteger)fieldMapping.field.get( srcObj );
				if ( bigIntegerVal != null ) {
					json_value = JSONNumber.BigInteger( bigIntegerVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_BIGDECIMAL:
				bigDecimalVal = (BigDecimal)fieldMapping.field.get( srcObj );
				if ( bigDecimalVal != null ) {
					json_value = JSONNumber.BigDecimal( bigDecimalVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_STRING:
				strVal = (String)fieldMapping.field.get( srcObj );
				if ( strVal != null ) {
					json_value = JSONString.String( strVal );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_BYTEARRAY:
				byteArray = (byte[])fieldMapping.field.get( srcObj );
				if ( byteArray != null ) {
					json_value = JSONString.String( byteArray );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_OBJECT:
				object = (Object)fieldMapping.field.get( srcObj );
				if ( object != null ) {
					json_value = toJSON( object );
				}
				else {
					json_value = JSONNull.Null;
				}
				json_struct.put( fieldMapping.name, json_value );
				break;
			case T_ARRAY:
				throw new UnsupportedOperationException( "Not implemented!" );
			}
		}

		return json_struct;
	}

}
