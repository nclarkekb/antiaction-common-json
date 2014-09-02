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

import java.lang.reflect.Field;

/**
 * A JSON objects field mapping description.
 *
 * @author Nicholas
 * Created on 23/11/2012
 */
public class JSONObjectFieldMapping {

	/** Field name. */
	public String fieldName;

	/** JSON name. */
	public String jsonName;

	/** JSON Java type identifier. */
	public int type;

	/** JSON Java array type identifier. */
	public int arrayType;

	/** Field class type name. */
	public String className;

	/** Field class. */
	public Class<?> clazz;

	/** Field object mapping, if object type. */
	public JSONObjectMapping objectMapping;

	/** Type of generic class parameters. */
	public Integer[] parametrizedObjectTypes;

	/** Parameterized class object mappings for generic class parameters. */
	public JSONObjectMapping[] parametrizedObjectMappings;

	/** Reflection field. */
	public Field field;

	/** Is field nullable. */
	public boolean nullable;

	/** Allow null values in array. */
	public boolean nullValues;

	/** Name of desired field data converter. */
	public String converterName;

	/** Id of desired field data converter. */
	public int converterId = -1;

}
