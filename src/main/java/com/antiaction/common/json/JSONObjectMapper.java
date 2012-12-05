/*
 * Created on 13/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Field;
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

	// TODO maybe better not static.
	protected static Map<String, JSONObjectMapping> classMappings = new TreeMap<String, JSONObjectMapping>();

	protected static Map<String, Integer> typeMappings = new TreeMap<String, Integer>();

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
	}

	public static final int INVALID_CLASS_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ANNOTATION
			| ClassTypeModifiers.CT_ANONYMOUSCLASS
			| ClassTypeModifiers.CT_ARRAY
			| ClassTypeModifiers.CT_ENUM
			| ClassTypeModifiers.CT_INTERFACE
			| ClassTypeModifiers.CT_LOCALCLASS
			| ClassTypeModifiers.CT_PRIMITIVE
			| ClassTypeModifiers.CM_ABSTRACT
			| ClassTypeModifiers.CM_NATIVE;

	public static final int VALID_CLASS_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_MEMBERCLASS
			| ClassTypeModifiers.CT_CLASS
			| ClassTypeModifiers.CM_STATIC;

	public static final int VALID_CLASS = ClassTypeModifiers.CT_CLASS;

	public static final int VALID_MEMBER_CLASS =  ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_STATIC;

	// ClassNotFoundException, SecurityException, NoSuchFieldException
	public void register(Class<?> clazz) throws JSONException {
		int classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( clazz );
		if ( (classTypeMask & INVALID_CLASS_TYPE_MODIFIERS_MASK) != 0 ) {
			throw new JSONException( "Unsupported class type." );
		}
		classTypeMask &= VALID_CLASS_TYPE_MODIFIERS_MASK;
		if ( (classTypeMask != VALID_CLASS) && (classTypeMask != VALID_MEMBER_CLASS) ) {
			throw new JSONException( "Unsupported class type." );
		}
		if ( !classMappings.containsKey( clazz.getName() ) ) {
			mapClass( clazz );
		}
	}

	public static final int IGNORE_FIELD_TYPE_MODIFIER = ClassTypeModifiers.CM_STATIC
			| ClassTypeModifiers.CM_TRANSIENT;

	public static final int INVALID_FIELD_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ANNOTATION
			| ClassTypeModifiers.CT_ANONYMOUSCLASS
			| ClassTypeModifiers.CT_ARRAY
			| ClassTypeModifiers.CT_ENUM
			| ClassTypeModifiers.CT_INTERFACE
			| ClassTypeModifiers.CT_LOCALCLASS
			| ClassTypeModifiers.CT_PRIMITIVE
			| ClassTypeModifiers.CM_ABSTRACT;

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
			Integer type;
			Class<?> fieldType = null;
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
					//System.out.println( field.getName() + " - " + ClassTypeModifiers.toString( fieldModsMask ) );
					bIgnore = (fieldModsMask & IGNORE_FIELD_TYPE_MODIFIER) != 0;
				}
				if ( !bIgnore ) {
					fieldType = field.getType();
					classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( fieldType );
					// debug
					//System.out.println( fieldType.getName() + " " + ClassTypeModifiers.toString( classTypeMask ) );

					type = typeMappings.get( fieldType.getName() );
					fieldObjectMapping = null;
					if ( type == null ) {
						if ( (classTypeMask & INVALID_FIELD_TYPE_MODIFIERS_MASK) != 0 ) {
							throw new JSONException( "Unsupported field class type." );
						}
						classTypeMask &= VALID_CLASS_TYPE_MODIFIERS_MASK;
						if ( (classTypeMask != VALID_CLASS) && (classTypeMask != VALID_MEMBER_CLASS) ) {
							throw new JSONException( "Unsupported field class type." );
						}
						type = T_OBJECT;
						fieldObjectMapping = classMappings.get( fieldType.getName() );
						if ( fieldObjectMapping == null ) {
							fieldObjectMapping = mapClass( Class.forName( fieldType.getName(), true, clazz.getClassLoader() ) );
						}
					}
					// debug
					//System.out.println( type );

					if ( type != null ) {
						json_fm = new JSONObjectFieldMapping();
						json_fm.name = field.getName();
						json_fm.type = type;
						json_fm.className = fieldType.getName();
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

	public <T> T toObject(JSONStructure json_struct, Class<T> clazz) throws InstantiationException, IllegalAccessException {
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

		JSONObjectMapping json_om = classMappings.get( clazz.getName() );
		if ( json_om == null ) {
			throw new IllegalArgumentException( "Class not registered." );
		}

		JSONObject json_object = json_struct.getObject();
		T dstObj = clazz.newInstance();

		Iterator<JSONObjectFieldMapping> fieldMappingsIter = json_om.fieldMappingsList.iterator();
		JSONObjectFieldMapping fieldMapping;
		JSONValue json_value;
		while ( fieldMappingsIter.hasNext() ) {
			fieldMapping = fieldMappingsIter.next();
			json_value = json_object.get( fieldMapping.name );
			if ( json_value != null ) {
				switch ( fieldMapping.type ) {
				case T_PRIMITIVE_BOOLEAN:
					booleanVal = json_value.getBoolean();
					fieldMapping.field.setBoolean( dstObj, booleanVal );
					break;
				case T_PRIMITIVE_INTEGER:
					intVal = json_value.getInteger();
					fieldMapping.field.setInt( dstObj, intVal );
					break;
				case T_PRIMITIVE_LONG:
					longVal = json_value.getLong();
					fieldMapping.field.setLong( dstObj, longVal );
					break;
				case T_PRIMITIVE_FLOAT:
					floatVal = json_value.getFloat();
					fieldMapping.field.setFloat( dstObj, floatVal );
					break;
				case T_PRIMITIVE_DOUBLE:
					doubleVal = json_value.getDouble();
					fieldMapping.field.setDouble( dstObj, doubleVal );
					break;
				case T_BOOLEAN:
					booleanVal = json_value.getBoolean();
					fieldMapping.field.set( dstObj, booleanVal );
					break;
				case T_INTEGER:
					intVal = json_value.getInteger();
					fieldMapping.field.set( dstObj, intVal );
					break;
				case T_LONG:
					longVal = json_value.getLong();
					fieldMapping.field.set( dstObj, longVal );
					break;
				case T_FLOAT:
					floatVal = json_value.getFloat();
					fieldMapping.field.set( dstObj, floatVal );
					break;
				case T_DOUBLE:
					doubleVal = json_value.getDouble();
					fieldMapping.field.set( dstObj, doubleVal );
					break;
				case T_BIGINTEGER:
					bigIntegerVal = json_value.getBigInteger();
					fieldMapping.field.set( dstObj, bigIntegerVal );
					break;
				case T_BIGDECIMAL:
					bigDecimalVal = json_value.getBigDecimal();
					fieldMapping.field.set( dstObj, bigDecimalVal );
					break;
				case T_STRING:
					strVal = json_value.getString();
					fieldMapping.field.set( dstObj, strVal );
					break;
				case T_BYTEARRAY:
					byteArray = json_value.getBytes();
					fieldMapping.field.set( dstObj, byteArray );
					break;
				case T_OBJECT:
					object = toObject( json_value.getObject(), fieldMapping.clazz );
					fieldMapping.field.set( dstObj, object );
					break;
				}
			}
			else {
			}
		}

		return dstObj;
	}

	/*
	public JSONStructure toJSON() {
		return null;
	}
	*/

}
