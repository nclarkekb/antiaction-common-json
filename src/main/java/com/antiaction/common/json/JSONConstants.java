/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

/**
 * JSON value types.
 *
 * @author Nicholas
 */
public class JSONConstants {

	/** JSON String value type. */
	public static final int VT_STRING = 1;

	/** JSON Number value type. */
	public static final int VT_NUMBER = 2;

	/** JSON Object value type. */
	public static final int VT_OBJECT = 3;

	/** JSON Array value type. */
	public static final int VT_ARRAY = 4;

	/** JSON Boolean value type. */
	public static final int VT_BOOLEAN = 5;

	/** JSON Null value type. */
	public static final int VT_NULL = 6;

	/**
	 * Constructor to prohibit instantiation.
	 */
	protected JSONConstants() {
	}

}
