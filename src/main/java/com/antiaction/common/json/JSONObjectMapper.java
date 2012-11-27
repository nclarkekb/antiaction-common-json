/*
 * Created on 13/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONNullable;

public class JSONObjectMapper {

	public static final int CT_ANNOTATION = 1 << 0;
	public static final int CT_ANONYMOUSCLASS = 1 << 1;
	public static final int CT_ARRAY = 1 << 2;
	public static final int CT_ENUM = 1 << 3;
	public static final int CT_INTERFACE = 1 << 4;
	public static final int CT_LOCALCLASS = 1 << 5;
	public static final int CT_MEMBERCLASS = 1 << 6;
	public static final int CT_PRIMITIVE = 1 << 7;
	public static final int CT_SYNTHETIC = 1 << 8;
	public static final int CT_CLASS = 1 << 9;

	public static final int CT_ABSTRACT = 1 << 16;
	public static final int CT_FINAL = 1 << 17;
	public static final int CT_NATIVE = 1 << 18;
	public static final int CT_PRIVATE = 1 << 19;
	public static final int CT_PROTECTED = 1 << 20;
	public static final int CT_PUBLIC = 1 << 21;
	public static final int CT_STATIC = 1 << 22;
	public static final int CT_STRICT = 1 << 23;
	public static final int CT_SYNCHRONIZED = 1 << 24;
	public static final int CT_TRANSIENT = 1 << 25;
	public static final int CT_VOLATILE = 1 << 26;

	public static final int T_BOOLEAN = 1;
	public static final int T_INTEGER = 2;
	public static final int T_LONG = 3;
	public static final int T_FLOAT = 4;
	public static final int T_DOUBLE = 5;
	public static final int T_BIGINTEGER = 6;
	public static final int T_BIGDECIMAL = 7;
	public static final int T_STRING = 8;
	public static final int T_BYTEARRAY = 9;
	public static final int T_OBJECT = 10;

	protected static Map<String, JSONObjectMapping> classMappings = new TreeMap<String, JSONObjectMapping>();

	protected static Map<String, Integer> typeMappings = new TreeMap<String, Integer>();

	static {
		typeMappings.put( boolean.class.getName(), T_BOOLEAN );
		typeMappings.put( int.class.getName(), T_INTEGER );
		typeMappings.put( long.class.getName(), T_LONG );
		typeMappings.put( float.class.getName(), T_FLOAT );
		typeMappings.put( double.class.getName(), T_DOUBLE );
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

	// ClassNotFoundException, SecurityException, NoSuchFieldException
	public void register(Class<?> clazz) throws JSONException {
		int classTypeMask = classTypeModifiersMask( clazz );
		if ( (classTypeMask & (CT_CLASS | CT_MEMBERCLASS)) == 0 ) {
			throw new JSONException( "Unsupported class type." );
		}
		if ( !classMappings.containsKey( clazz.getName() ) ) {
			mapClass( clazz );
		}
	}

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
			int classTypeMask;
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
					Class<?> fieldType = field.getType();

					/*
					System.out.println( fieldType.getName() );
					System.out.println( fieldType.isAnnotation() );
					System.out.println( fieldType.isAnonymousClass() );
					System.out.println( fieldType.isArray() );
					System.out.println( fieldType.isEnum() );
					System.out.println( fieldType.isInterface() );
					System.out.println( fieldType.isLocalClass() );
					System.out.println( fieldType.isMemberClass() );
					System.out.println( fieldType.isPrimitive() );
					System.out.println( fieldType.isSynthetic() );
					*/

					type = typeMappings.get( fieldType.getName() );
					fieldObjectMapping = null;
					if ( type == null ) {
						classTypeMask = classTypeModifiersMask( fieldType );
						// debug
						//System.out.println( classTypeMask );
						if ( (classTypeMask & (CT_CLASS | CT_MEMBERCLASS)) == 0 ) {
							throw new JSONException( "Unsupported field type." );
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
						json_fm.field = clazz.getField( json_fm.name );
						json_om.fieldMappingsMap.put( json_fm.name, json_fm );
						json_om.fieldMappingsList.add( json_fm );

						nullable = field.getAnnotation( JSONNullable.class );
						if ( nullable != null ) {
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

	public static int classTypeModifiersMask(Class<?> clazz) {
		 //isAnnotationPresent(Class<? extends Annotation> annotationClass)
		 //isAssignableFrom(Class<?> cls)
		 //isInstance(Object obj)
		int mask = 0;
		/*
		 * Type.
		 */
		if ( clazz.isAnnotation() ) {
			mask |= CT_ANNOTATION;
		}
		if ( clazz.isAnonymousClass() ) {
			mask |= CT_ANONYMOUSCLASS;
		}
		if ( clazz.isArray() ) {
			mask |= CT_ARRAY;
		}
		if ( clazz.isEnum() ) {
			mask |= CT_ENUM;
		}
		if ( clazz.isInterface() ) {
			mask |= CT_INTERFACE;
		}
		if ( clazz.isLocalClass() ) {
			mask |= CT_LOCALCLASS;
		}
		if ( clazz.isMemberClass() ) {
			mask |= CT_MEMBERCLASS;
		}
		if ( clazz.isPrimitive() ) {
			mask |= CT_PRIMITIVE;
		}
		if ( clazz.isSynthetic() ) {
			mask |= CT_SYNTHETIC;
		}
		/*
		 * Class.
		 */
		if ( (mask & (CT_ANNOTATION | CT_ANONYMOUSCLASS | CT_ARRAY | CT_ENUM | CT_INTERFACE | CT_LOCALCLASS | CT_MEMBERCLASS | CT_PRIMITIVE)) == 0 ) {
			mask |= CT_CLASS;
		}
		/*
		 * Modifiers.
		 */
		int mod = clazz.getModifiers();
		if ( Modifier.isAbstract( mod ) ) {
			mask |= CT_ABSTRACT;
		}
		if ( Modifier.isFinal( mod ) ) {
			mask |= CT_FINAL;
		}
		if ( Modifier.isInterface( mod ) ) {
			mask |= CT_INTERFACE;
		}
		if ( Modifier.isNative( mod ) ) {
			mask |= CT_NATIVE;
		}
		if ( Modifier.isPrivate( mod) ) {
			mask |= CT_PRIVATE;
		}
		if ( Modifier.isProtected( mod ) ) {
			mask |= CT_PROTECTED;
		}
		if ( Modifier.isPublic( mod) ) {
			mask |= CT_PUBLIC;
		}
		if ( Modifier.isStatic( mod ) ) {
			mask |= CT_STATIC;
		}
		if ( Modifier.isStrict( mod ) ) {
			mask |= CT_STRICT;
		}
		if ( Modifier.isSynchronized( mod ) ) {
			mask |= CT_SYNCHRONIZED;
		}
		if ( Modifier.isTransient( mod ) ) {
			mask |= CT_TRANSIENT;
		}
		if ( Modifier.isVolatile( mod ) ) {
			mask |= CT_VOLATILE;
		}
		return mask;
	}

	public <T> T toObject(JSONStructure json_struct, Class<T> clazz) throws InstantiationException, IllegalAccessException {
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
				case T_BOOLEAN:
					Boolean booleanVal = json_value.getBoolean();
					fieldMapping.field.setBoolean( dstObj, booleanVal );
					break;
				case T_INTEGER:
					Integer intVal = json_value.getInteger();
					fieldMapping.field.setInt( dstObj, intVal );
					break;
				case T_LONG:
					Long longVal = json_value.getLong();
					fieldMapping.field.setLong( dstObj, longVal );
					break;
				case T_FLOAT:
					Float floatVal = json_value.getFloat();
					fieldMapping.field.setFloat( dstObj, floatVal );
					break;
				case T_DOUBLE:
					Double doubleVal = json_value.getDouble();
					fieldMapping.field.set( dstObj, doubleVal );
					break;
				case T_BIGINTEGER:
					BigInteger bigIntegerVal = json_value.getBigInteger();
					fieldMapping.field.set( dstObj, bigIntegerVal );
					break;
				case T_BIGDECIMAL:
					BigDecimal bigDecimalVal = json_value.getBigDecimal();
					fieldMapping.field.set( dstObj, bigDecimalVal );
					break;
				case T_STRING:
					String strVal = json_value.getString();
					fieldMapping.field.set( dstObj, strVal );
					break;
				case T_BYTEARRAY:
					byte[] byteArray = json_value.getBytes();
					fieldMapping.field.set( dstObj, byteArray );
					break;
				case T_OBJECT:
					Object object = toObject( json_value.getObject(), fieldMapping.clazz );
					fieldMapping.field.set( dstObj, object );
					break;
				}
			}
			else {
			}
		}

		return dstObj;
	}

}
