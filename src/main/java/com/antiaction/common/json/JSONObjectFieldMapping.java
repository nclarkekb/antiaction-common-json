/*
 * Created on 23/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.lang.reflect.Field;

/**
 * A JSON objects field mapping description.
 *
 * @author Nicholas
 */
public class JSONObjectFieldMapping {

	/** Field name. */
	public String name;

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

	/** Reflection field. */
	public Field field;

	/** Is field nullable. */
	public boolean nullable;

	/** Allow null values in array. */
	public boolean nullValues;

}
