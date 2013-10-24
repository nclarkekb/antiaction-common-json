/*
 * Created on 13/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONConverter;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONName;
import com.antiaction.common.json.annotation.JSONNullValues;
import com.antiaction.common.json.annotation.JSONNullable;

public class JSONObjectMappings {

	protected final Class<?>[] zeroArgsParameterTypes = new Class[ 0 ];

	protected final Map<String, JSONObjectMapping> classMappings = new TreeMap<String, JSONObjectMapping>();

	protected final Map<String, Integer> converterNameIdMap = new TreeMap<String, Integer>();

	protected int converterIds = 0;

	protected final JSONStructureMarshaller structureMarshaller;

	protected final JSONStructureUnmarshaller structureUnmarshaller;

	protected final JSONStreamMarshaller streamMarshaller;

	protected final JSONStreamUnmarshaller streamUnmarshaller;

	public JSONObjectMappings() {
		structureMarshaller = new JSONStructureMarshaller( this );
		structureUnmarshaller = new JSONStructureUnmarshaller( this );
		streamMarshaller = new JSONStreamMarshaller( this );
		streamUnmarshaller = new JSONStreamUnmarshaller( this );
	}

	public JSONStructureMarshaller getStructureMarshaller() {
		return structureMarshaller;
	}

	public JSONStructureUnmarshaller getStructureUnmarshaller() {
		return structureUnmarshaller;
	}

	public JSONStreamMarshaller getStreamMarshaller() {
		return streamMarshaller;
	}

	public JSONStreamUnmarshaller getStreamUnmarshaller() {
		return streamUnmarshaller;
	}

	public int getConverterNameId(String converterName) throws JSONException {
		Integer id =  converterNameIdMap.get( converterName );
		if ( id == null ) {
			throw new JSONException( "Unknown conveter name: " + converterName );
		}
		return id;
	}

	public int getConverters() {
		return converterIds;
	}

	public void register(Class<?> clazz) throws JSONException {
		if ( !classMappings.containsKey( clazz.getName() ) ) {
			int classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( clazz );
			if ( (classTypeMask & JSONObjectMappingConstants.CLASS_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
				throw new JSONException( "Unsupported class type." );
			}
			classTypeMask &= JSONObjectMappingConstants.CLASS_VALID_TYPE_MODIFIERS_MASK;
			if ( (classTypeMask == JSONObjectMappingConstants.VALID_CLASS) || (classTypeMask == JSONObjectMappingConstants.VALID_MEMBER_CLASS) ) {
				mapClass( clazz );
			}
			else if ( classTypeMask == JSONObjectMappingConstants.VALID_ARRAY_CLASS ) {
				mapArray( clazz );
			}
			else  {
				throw new JSONException( "Unsupported class type." );
			}
		}
	}

	protected JSONObjectMapping mapArray(Class<?> clazz) throws JSONException {
		int level;
		Class<?> fieldType = null;
		JSONObjectMapping fieldObjectMapping;

		JSONObjectMapping json_om = JSONObjectMapping.getArrayMapping();
		classMappings.put( clazz.getName(), json_om );

		try {
			String arrayTypeName = clazz.getName();
			// debug
			//System.out.println( typeName );
			Integer arrayType = JSONObjectMappingConstants.arrayTypeMappings.get( arrayTypeName );
			// debug
			//System.out.println( arrayType );
			if ( arrayType == null ) {
				level = 0;
				while ( level < arrayTypeName.length() && arrayTypeName.charAt( level ) == '[' ) {
					++level;
				}
				// [L<class>;
				if ( level == 1 && level < arrayTypeName.length() && arrayTypeName.charAt( level ) == 'L' && arrayTypeName.endsWith( ";" ) ) {
					arrayType = JSONObjectMappingConstants.T_OBJECT;
					arrayTypeName = arrayTypeName.substring( level + 1, arrayTypeName.length() - 1 );
					fieldType = Class.forName( arrayTypeName, true, clazz.getClassLoader() );
					// Cache
					fieldObjectMapping = classMappings.get( arrayTypeName );
					if ( fieldObjectMapping == null ) {
						fieldObjectMapping = mapClass( fieldType );
					}
				}
				else {
					throw new JSONException( "Unsupported array type '" + arrayTypeName + "'." );
				}
				json_om.arrayType = arrayType;
				json_om.className = arrayTypeName;
				json_om.clazz = fieldType;
				json_om.objectMapping = fieldObjectMapping;
			}
		} catch (ClassNotFoundException e) {
			new JSONException( e );
		}
		return json_om;
	}

	protected JSONObjectMapping mapClass(Class<?> clazz) throws JSONException {
		JSONObjectFieldMapping json_fm;
		JSON json;
		JSONIgnore ignore;

		JSONObjectMapping json_om = JSONObjectMapping.getObjectMapping();
		classMappings.put( clazz.getName(), json_om );

		Constructor<?> constructor = null;
		try {
			constructor = clazz.getConstructor( zeroArgsParameterTypes );
		}
		catch (NoSuchMethodException e) {
		}
		if ( constructor == null ) {
			throw new JSONException( clazz.getName() + " does not have a zero argument constructor!" );
		}

		json = clazz.getAnnotation( JSON.class );
		if ( json != null ) {
			String[] ignores = json.ignore();
			for ( int i=0; i<ignores.length; ++i) {
				json_om.ignore.add( ignores[ i ] );
			}
			String [] nullables = json.nullable();
			for ( int i=0; i<nullables.length; ++i ) {
				json_om.nullableSet.add( nullables[ i ] );
			}
			String[] nullValues = json.nullValues();
			for ( int i=0; i<nullValues.length; ++i ) {
				json_om.nullValuesSet.add( nullValues[ i ] );
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
		int level;
		JSONNullable nullable;
		boolean bNullable;
		JSONNullValues nullValues;
		boolean bNullValues;
		JSONConverter converter;
		JSONName jsonName;
		try {
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
					bIgnore = (fieldModsMask & JSONObjectMappingConstants.FIELD_IGNORE_TYPE_MODIFIER) != 0;
				}
				if ( !bIgnore ) {
					fieldType = field.getType();
					fieldTypeName = fieldType.getName();
					classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( fieldType );
					// debug
					//System.out.println( fieldTypeName + " " + ClassTypeModifiers.toString( classTypeMask ) );

					type = JSONObjectMappingConstants.typeMappings.get( fieldTypeName );
					fieldObjectMapping = null;
					if ( type == null ) {
						if ( (classTypeMask & JSONObjectMappingConstants.FIELD_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
							throw new JSONException( "Unsupported field class type." );
						}
						classTypeMask &= JSONObjectMappingConstants.FIELD_VALID_TYPE_MODIFIERS_MASK;
						if ( (classTypeMask == JSONObjectMappingConstants.VALID_CLASS) || (classTypeMask == JSONObjectMappingConstants.VALID_MEMBER_CLASS) ) {
							Type genericType = field.getGenericType();
							//System.out.println( "GT: " + genericType + " " + genericType.getClass() );

							type = JSONObjectMappingConstants.T_OBJECT;
							// Cache
							fieldObjectMapping = classMappings.get( fieldTypeName );
							if ( fieldObjectMapping == null ) {
								fieldObjectMapping = mapClass( Class.forName( fieldTypeName, true, clazz.getClassLoader() ) );
							}

							if( genericType instanceof ParameterizedType ) {
								ParameterizedType parameterizedType = (ParameterizedType)genericType;
								Type[] typeArguments = parameterizedType.getActualTypeArguments();
								for ( Type typeArgument : typeArguments ) {
									Class<?> classType = ((Class<?>)typeArgument);
									//System.out.println( "Field " + field.getName() + " has a parameterized type of " + classType.getSimpleName() );
								}
							}
						}
						else if ( classTypeMask == JSONObjectMappingConstants.VALID_ARRAY_CLASS ) {
							type = JSONObjectMappingConstants.T_ARRAY;
							arrayType = JSONObjectMappingConstants.arrayTypeMappings.get( fieldTypeName );
							if ( arrayType == null ) {
								level = 0;
								while ( level < fieldTypeName.length() && fieldTypeName.charAt( level ) == '[' ) {
									++level;
								}
								// [L<class>;
								if ( level == 1 && level < fieldTypeName.length() && fieldTypeName.charAt( level ) == 'L' && fieldTypeName.endsWith( ";" ) ) {
									arrayType = JSONObjectMappingConstants.T_OBJECT;
									fieldTypeName = fieldTypeName.substring( level + 1, fieldTypeName.length() - 1 );
									fieldType = Class.forName( fieldTypeName, true, clazz.getClassLoader() );
									// Cache
									fieldObjectMapping = classMappings.get( fieldTypeName );
									if ( fieldObjectMapping == null ) {
										fieldObjectMapping = mapClass( fieldType );
									}
								}
								else {
									throw new JSONException( "Unsupported array type '" + fieldTypeName + "'." );
								}
							}
						}
						else {
							throw new JSONException( "Unsupported field class type." );
						}
					}
					// debug
					//System.out.println( type );

					if ( type != null ) {
						json_fm = new JSONObjectFieldMapping();
						json_fm.fieldName = field.getName();
						json_fm.type = type;
						json_fm.arrayType = arrayType;
						json_fm.className = fieldTypeName;
						json_fm.clazz = fieldType;
						json_fm.objectMapping = fieldObjectMapping;
						json_fm.field = clazz.getDeclaredField( json_fm.fieldName );
						json_fm.field.setAccessible( true );

						bNullable = json_om.nullableSet.contains( json_fm.fieldName );
						if ( !bNullable ) {
							nullable = field.getAnnotation( JSONNullable.class );
							if ( nullable != null ) {
								bNullable = nullable.value();
							}
						}
						if ( bNullable ) {
							if ( json_fm.type < JSONObjectMappingConstants.T_OBJECT ) {
								throw new JSONException( "Primitive types can not be nullable." );
							}
							json_fm.nullable = true;
						}
						bNullValues = json_om.nullValuesSet.contains( json_fm.fieldName );
						if ( !bNullValues) {
							nullValues = field.getAnnotation( JSONNullValues.class );
							if ( nullValues != null ) {
								bNullValues = nullValues.value();
							}
						}
						if ( bNullValues ) {
							if ( json_fm.type >= JSONObjectMappingConstants.T_ARRAY && json_fm.arrayType < JSONObjectMappingConstants.T_OBJECT ) {
								throw new JSONException( "Array of primitive type can not have null values." );
							}
							json_fm.nullValues = true;
						}
						converter = field.getAnnotation( JSONConverter.class );
						if ( converter != null ) {
							json_fm.converterName = converter.name();
							Integer converterId = converterNameIdMap.get( json_fm.converterName );
							if ( converterId == null ) {
								converterId = converterIds++;
								converterNameIdMap.put( json_fm.converterName, converterId );
							}
							json_fm.converterId = converterId;
							json_om.converters = true;
						}
						jsonName = field.getAnnotation( JSONName.class );
						if ( jsonName != null ) {
							json_fm.jsonName = jsonName.value();
						}
						else {
							json_fm.jsonName = json_fm.fieldName;
						}

						json_om.fieldMappingsMap.put( json_fm.jsonName, json_fm );
						json_om.fieldMappingsList.add( json_fm );
					}
				}
			}
			json_om.fieldMappingsArr = json_om.fieldMappingsList.toArray( new JSONObjectFieldMapping[ 0 ] );
		}
		catch (ClassNotFoundException e) {
			throw new JSONException( e );
		}
		catch (NoSuchFieldException e) {
			throw new JSONException( e );
		}
		catch (SecurityException e) {
			throw new JSONException( e );
		}
		return json_om;
	}

}
