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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONConverter;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONName;
import com.antiaction.common.json.annotation.JSONNullValues;
import com.antiaction.common.json.annotation.JSONNullable;
import com.antiaction.common.json.representation.JSONStructureMarshaller;
import com.antiaction.common.json.representation.JSONStructureUnmarshaller;
import com.antiaction.common.json.representation.JSONTextMarshaller;
import com.antiaction.common.json.representation.JSONTextUnmarshaller;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 13/11/2012
 */
public class JSONObjectMappings {

	protected final Class<?>[] zeroArgsParameterTypes = new Class[ 0 ];

	public final Map<String, JSONObjectMapping> classMappings = new TreeMap<String, JSONObjectMapping>();

	protected final Map<String, Integer> converterNameIdMap = new TreeMap<String, Integer>();

	protected int converterIds = 0;

	protected final JSONTextMarshaller textMarshaller;

	protected final JSONTextUnmarshaller textUnmarshaller;

	protected final JSONStructureMarshaller structureMarshaller;

	protected final JSONStructureUnmarshaller structureUnmarshaller;

	protected final JSONStreamMarshaller streamMarshaller;

	protected final JSONStreamUnmarshaller streamUnmarshaller;

	public JSONObjectMappings() {
		textMarshaller = new JSONTextMarshaller();
		textUnmarshaller = new JSONTextUnmarshaller();
		structureMarshaller = new JSONStructureMarshaller( this );
		structureUnmarshaller = new JSONStructureUnmarshaller( this );
		streamMarshaller = new JSONStreamMarshaller( this );
		streamUnmarshaller = new JSONStreamUnmarshaller( this );
	}

	public JSONTextMarshaller getTextMarshaller() {
		return textMarshaller;
	}

	public JSONTextUnmarshaller getTextUnmarshaller() {
		return textUnmarshaller;
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

	public JSONObjectMapping register(Class<?> clazz) throws JSONException {
		JSONObjectMapping objectMapping = classMappings.get( clazz.getName() );
		if ( objectMapping == null ) {
			int classTypeMask = ClassTypeModifiers.getClassTypeModifiersMask( clazz );
			if ( (classTypeMask & JSONObjectMappingConstants.CLASS_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
				throw new JSONException( "Unsupported class type." );
			}
			classTypeMask &= JSONObjectMappingConstants.CLASS_VALID_TYPE_MODIFIERS_MASK;
			if ( (classTypeMask == JSONObjectMappingConstants.VALID_CLASS) || (classTypeMask == JSONObjectMappingConstants.VALID_MEMBER_CLASS) ) {
				objectMapping = mapClass( clazz );
			}
			else if ( classTypeMask == JSONObjectMappingConstants.VALID_ARRAY_CLASS ) {
				objectMapping = mapArray( clazz );
			}
			else  {
				throw new JSONException( "Unsupported class type." );
			}
		}
		return objectMapping;
	}

	protected JSONObjectMapping mapArray(Class<?> clazz) throws JSONException {
		int level;
		Class<?> fieldType = null;
		JSONObjectMapping fieldObjectMapping = null;

		JSONObjectMapping objectMapping = JSONObjectMapping.getArrayMapping();
		classMappings.put( clazz.getName(), objectMapping );

		try {
			String arrayTypeName = clazz.getName();
			// debug
			//System.out.println( typeName );
			Integer arrayType = JSONObjectMappingConstants.arrayPrimitiveTypeMappings.get( arrayTypeName );
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
			}
			objectMapping.arrayType = arrayType;
			objectMapping.className = arrayTypeName;
			objectMapping.clazz = fieldType;
			objectMapping.objectMapping = fieldObjectMapping;
			// TODO FieldMapping need to work for top level arrays.
			objectMapping.fieldMapping = new JSONObjectFieldMapping();
			objectMapping.fieldMapping.arrayType = arrayType;
			objectMapping.fieldMapping.className = arrayTypeName;
			objectMapping.fieldMapping.clazz = fieldType;
		} catch (ClassNotFoundException e) {
			new JSONException( e );
		}
		return objectMapping;
	}

	protected JSONObjectMapping mapClass(Class<?> clazz) throws JSONException {
		JSONObjectFieldMapping json_fm;
		JSON json;
		JSONIgnore ignore;

		JSONObjectMapping objectMapping = JSONObjectMapping.getObjectMapping();
		classMappings.put( clazz.getName(), objectMapping );

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
				objectMapping.ignore.add( ignores[ i ] );
			}
			String [] nullables = json.nullable();
			for ( int i=0; i<nullables.length; ++i ) {
				objectMapping.nullableSet.add( nullables[ i ] );
			}
			String[] nullValues = json.nullValues();
			for ( int i=0; i<nullValues.length; ++i ) {
				objectMapping.nullValuesSet.add( nullValues[ i ] );
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
		Integer[] parametrizedObjectTypes; 
		JSONObjectMapping[] parametrizedObjectMappings;
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
				bIgnore = objectMapping.ignore.contains( field.getName() );
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

					type = JSONObjectMappingConstants.primitiveTypeMappings.get( fieldTypeName );
					fieldObjectMapping = null;
					parametrizedObjectTypes = null;
					parametrizedObjectMappings = null;
					if ( type == null ) {
						if ( (classTypeMask & JSONObjectMappingConstants.FIELD_INVALID_TYPE_MODIFIERS_MASK) != 0 ) {
							// Check the interface itself.
							int colType = ClassTypeModifiers.getCollectionInterfaceType( fieldType );
							switch ( colType ) {
							case ClassTypeModifiers.COLTYPE_LIST:
								throw new JSONException( "Unsupported collection interface field type. (" + fieldType.getName() + ")" );
							case ClassTypeModifiers.COLTYPE_MAP:
								throw new JSONException( "Unsupported collection interface field type. (" + fieldType.getName() + ")" );
							case ClassTypeModifiers.COLTYPE_SET:
								throw new JSONException( "Unsupported collection interface field type. (" + fieldType.getName() + ")" );
							default:
								// Check extended interfaces.
								colType = ClassTypeModifiers.getCollectionType( fieldType );
								switch ( colType ) {
								case ClassTypeModifiers.COLTYPE_LIST:
									throw new JSONException( "Unsupported collection interface field type. (" + List.class.getName() + " .. " + fieldType.getName() + ")" );
								case ClassTypeModifiers.COLTYPE_MAP:
									throw new JSONException( "Unsupported collection interface field type. (" + Map.class.getName() + " .. " + fieldType.getName() + ")" );
								case ClassTypeModifiers.COLTYPE_SET:
									throw new JSONException( "Unsupported collection interface field type. (" + Set.class.getName() + " .. " + fieldType.getName() + ")" );
								default:
									throw new JSONException( "Unsupported field class type." );
								}
							}
						}
						classTypeMask &= JSONObjectMappingConstants.FIELD_VALID_TYPE_MODIFIERS_MASK;
						if ( (classTypeMask == JSONObjectMappingConstants.VALID_CLASS) || (classTypeMask == JSONObjectMappingConstants.VALID_MEMBER_CLASS) ) {
							Type genericType = field.getGenericType();
							// debug
							System.out.println( "GT: " + genericType + " * " + genericType.getClass() );

							if ( genericType instanceof Class ) {
								int colType = ClassTypeModifiers.getCollectionType( (Class<?>)genericType );
								switch ( colType ) {
								case ClassTypeModifiers.COLTYPE_LIST:
								case ClassTypeModifiers.COLTYPE_MAP:
								case ClassTypeModifiers.COLTYPE_SET:
									throw new JSONException( "Collection must have parametrized type(s). (" + fieldType.getName() + ")" );
								default:
									type = JSONObjectMappingConstants.T_OBJECT;
									// Cache
									fieldObjectMapping = classMappings.get( fieldTypeName );
									if ( fieldObjectMapping == null ) {
										fieldObjectMapping = mapClass( Class.forName( fieldTypeName, true, clazz.getClassLoader() ) );
									}
									break;
								}
							}
							else if ( genericType instanceof ParameterizedType ) {
								ParameterizedType parameterizedType = (ParameterizedType)genericType;
								Type[] typeArguments = parameterizedType.getActualTypeArguments();
								boolean bWildCardUsed = false;
								for ( Type typeArgument : typeArguments ) {
									if ( typeArgument instanceof WildcardType ) {
										bWildCardUsed = true;
									}
								}
								if ( bWildCardUsed ) {
									throw new JSONException( "Unsupported use of wildcard parameterized types." );
								}
								parametrizedObjectTypes = new Integer[ typeArguments.length ];
								parametrizedObjectMappings = new JSONObjectMapping[ typeArguments.length ];
								for ( int j=0; j<typeArguments.length; ++j ) {
									Type typeArgument = typeArguments[ j ];
									Class<?> parameterizedTypeClass = ((Class<?>)typeArgument);
									// debug
									System.out.println( parameterizedTypeClass );
									parametrizedObjectTypes[ j ] = JSONObjectMappingConstants.primitiveTypeMappings.get( parameterizedTypeClass.getName() );
									if ( parametrizedObjectTypes[ j ] == null ) {
										parametrizedObjectMappings[ j ] = classMappings.get( parameterizedTypeClass.getName() );
										if ( parametrizedObjectMappings[ j ] == null ) {
											parametrizedObjectMappings[ j ] = mapClass( parameterizedTypeClass );
										}
									}
								}
								int colType = ClassTypeModifiers.getCollectionType( (Class<?>)parameterizedType.getRawType() );
								switch ( colType ) {
								case ClassTypeModifiers.COLTYPE_LIST:
									type = JSONObjectMappingConstants.T_LIST;
									break;
								case ClassTypeModifiers.COLTYPE_MAP:
									type = JSONObjectMappingConstants.T_MAP;
									break;
								case ClassTypeModifiers.COLTYPE_SET:
									type = JSONObjectMappingConstants.T_SET;
									break;
								default:
									throw new JSONException( "Unsupported generic class. " + field.getClass() );
								}
							}
							else if ( genericType instanceof TypeVariable ) {
								throw new JSONException( "Unable to determine type of generic field '" + field.getName() + "'" );
							}
						}
						else if ( classTypeMask == JSONObjectMappingConstants.VALID_ARRAY_CLASS ) {
							type = JSONObjectMappingConstants.T_ARRAY;
							arrayType = JSONObjectMappingConstants.arrayPrimitiveTypeMappings.get( fieldTypeName );
							if ( arrayType == null ) {
								level = 0;
								while ( level < fieldTypeName.length() && fieldTypeName.charAt( level ) == '[' ) {
									++level;
								}
								// [L<class>;
								if ( level < fieldTypeName.length() ) {
									if ( level == 1 ) {
										if ( fieldTypeName.charAt( level ) == 'L' && fieldTypeName.endsWith( ";" ) ) {
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
									else {
										throw new JSONException( "Unsupported multi-dimensional array type '" + fieldTypeName + "'." );
									}
								}
								else {
									throw new JSONException( "Invalid array type '" + fieldTypeName + "'." );
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
						json_fm.parametrizedObjectTypes = parametrizedObjectTypes;
						json_fm.parametrizedObjectMappings = parametrizedObjectMappings;
						json_fm.field = clazz.getDeclaredField( json_fm.fieldName );
						json_fm.field.setAccessible( true );

						bNullable = objectMapping.nullableSet.contains( json_fm.fieldName );
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
						bNullValues = objectMapping.nullValuesSet.contains( json_fm.fieldName );
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
							objectMapping.converters = true;
						}
						jsonName = field.getAnnotation( JSONName.class );
						if ( jsonName != null ) {
							json_fm.jsonName = jsonName.value();
						}
						else {
							json_fm.jsonName = json_fm.fieldName;
						}

						objectMapping.fieldMappingsMap.put( json_fm.jsonName, json_fm );
						objectMapping.fieldMappingsList.add( json_fm );
					}
				}
			}
			objectMapping.fieldMappingsArr = objectMapping.fieldMappingsList.toArray( new JSONObjectFieldMapping[ 0 ] );
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
		return objectMapping;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, JSONObjectMapping>> iter = classMappings.entrySet().iterator();
		Entry<String, JSONObjectMapping> entry;
		JSONObjectMapping objectMapping;
		while ( iter.hasNext() ) {
			entry = iter.next();
			sb.append( "class: " );
			sb.append( entry.getKey() );
			sb.append( "\n" );
			objectMapping = entry.getValue();
			objectMapping.toString( sb );
		}
		return sb.toString();
	}

}
