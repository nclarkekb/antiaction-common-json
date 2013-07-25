/*
 * Created on 24/07/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class JSONObjectMappingConstants {

	/*
	 * Type.
	 */

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
	public static final int T_ARRAY = 200;

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

	/*
	 * Class.
	 */

	public static final int CLASS_INVALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ANNOTATION
			| ClassTypeModifiers.CT_ANONYMOUSCLASS
			| ClassTypeModifiers.CT_ENUM
			| ClassTypeModifiers.CT_INTERFACE
			| ClassTypeModifiers.CT_LOCALCLASS
			| ClassTypeModifiers.CT_PRIMITIVE
			| ClassTypeModifiers.CM_NATIVE;

	public static final int CLASS_VALID_TYPE_MODIFIERS_MASK = ClassTypeModifiers.CT_ARRAY
			| ClassTypeModifiers.CT_MEMBERCLASS
			| ClassTypeModifiers.CT_CLASS
			| ClassTypeModifiers.CM_ABSTRACT
			| ClassTypeModifiers.CM_FINAL
			| ClassTypeModifiers.CM_STATIC;

	public static final int VALID_CLASS = ClassTypeModifiers.CT_CLASS;

	public static final int VALID_MEMBER_CLASS = ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_STATIC;

	public static final int VALID_ARRAY_CLASS = ClassTypeModifiers.CT_ARRAY | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_FINAL;

	/*
	 * Field.
	 */

	public static final int FIELD_IGNORE_TYPE_MODIFIER = ClassTypeModifiers.CM_STATIC
			| ClassTypeModifiers.CM_TRANSIENT;

	// TODO support List/Set interface but required impl class annotation.
	// TODO byte array mapping
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
			| ClassTypeModifiers.CM_FINAL
			| ClassTypeModifiers.CM_STATIC;

}
