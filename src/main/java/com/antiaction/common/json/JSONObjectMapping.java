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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A JSON object mapping description.
 *
 * @author Nicholas
 * Created on 22/11/2012
 */
public class JSONObjectMapping {

	/** Object mapping type. */
	public static final int OMT_OBJECT = 1;
	/** Array mapping type. */
	public static final int OMT_ARRAY = 2;

	/** Mapping type, object or array. */
	public int type;

	/*
	 * Object.
	 */

	/** Field names to ignore when mapping objects. */
	public Set<String> ignore = new HashSet<String>();

	/** Field names which can be null. */
	public Set<String> nullableSet = new TreeSet<String>();

	/** Field names which can have null values. */
	public Set<String> nullValuesSet = new TreeSet<String>();

	/** Map of mapped fields. */
	public Map<String, JSONObjectFieldMapping> fieldMappingsMap = new TreeMap<String, JSONObjectFieldMapping>();

	/** List of mapped fields. */
	public List<JSONObjectFieldMapping> fieldMappingsList = new LinkedList<JSONObjectFieldMapping>();

	/** Array of mapped fields. */
	public JSONObjectFieldMapping[] fieldMappingsArr;

	/** Boolean indicating if one or more field mapping(s) requires a converter. */
	public boolean converters;

	/*
	 * Array.
	 */

	/** JSON Java array type identifier. */
	public int arrayType;

	/** Field class type name. */
	public String className;

	/** Field class. */
	public Class<?> clazz;

	/** Field object mapping. */
	public JSONObjectFieldMapping fieldMapping;

	/** Object mapping, if array is an object type. */
	public JSONObjectMapping objectMapping;

	private JSONObjectMapping() {
	}

	public static JSONObjectMapping getObjectMapping() {
		JSONObjectMapping om = new JSONObjectMapping();
		om.type = OMT_OBJECT;
		om.ignore = new HashSet<String>();
		om.nullableSet = new TreeSet<String>();
		om.nullValuesSet = new TreeSet<String>();
		om.fieldMappingsMap = new TreeMap<String, JSONObjectFieldMapping>();
		om.fieldMappingsList = new LinkedList<JSONObjectFieldMapping>();
		return om;
	}

	public static JSONObjectMapping getArrayMapping() {
		JSONObjectMapping om = new JSONObjectMapping();
		om.type = OMT_ARRAY;
		return om;
	}

}
