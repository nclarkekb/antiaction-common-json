/*
 * Created on 07/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONNumber {

	@Test
	public void test_jsonnumber() {
		try {
			JSONNumber json_number;

			json_number = new JSONNumber( "Hello" );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( "Hello".getBytes(), json_number.numberBytes );
			Assert.assertEquals( "Hello", json_number.toString() );

			json_number = JSONNumber.Integer( 42 );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNotNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( "42".getBytes(), json_number.numberBytes );
			Assert.assertEquals( "42", json_number.toString() );

			json_number = JSONNumber.Long( 12345678901234L );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNotNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( "12345678901234".getBytes(), json_number.numberBytes );
			Assert.assertEquals( "12345678901234", json_number.toString() );

			json_number = JSONNumber.Float( 1.0F / 3.0F );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNotNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( new Float( 1.0F / 3.0F ).toString().getBytes(), json_number.numberBytes );
			Assert.assertEquals( new Float( 1.0F / 3.0F ).toString(), json_number.toString() );

			json_number = JSONNumber.Double( 1.0 / 3.0 );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNotNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( new Double( 1.0 / 3.0 ).toString().getBytes(), json_number.numberBytes );
			Assert.assertEquals( new Double( 1.0 / 3.0 ).toString(), json_number.toString() );

			json_number = JSONNumber.BigInteger( new BigInteger( "123456789012345678901234567890123456789012" ) );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNotNull( json_number.bigIntegerVal );
			Assert.assertNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( new BigInteger( "123456789012345678901234567890123456789012" ).toString().getBytes(), json_number.numberBytes );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).toString(), json_number.toString() );

			json_number = JSONNumber.BigDecimal( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) );
			Assert.assertNotNull( json_number );
			Assert.assertEquals( JSONConstants.VT_NUMBER, json_number.type );
			Assert.assertNull( json_number.intVal );
			Assert.assertNull( json_number.longVal );
			Assert.assertNull( json_number.floatVal );
			Assert.assertNull( json_number.doubleVal );
			Assert.assertNull( json_number.bigIntegerVal );
			Assert.assertNotNull( json_number.bigDecimalVal );
			Assert.assertArrayEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).toString().getBytes(), json_number.numberBytes );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).toString(), json_number.toString() );

			/*
			 *
			 */

			JSONNumber json_number_int = JSONNumber.Integer( 42 );
			JSONNumber json_number_long = JSONNumber.Long( 12345678901234L );
			JSONNumber json_number_float = JSONNumber.Float( 1.0F / 3.0F );
			JSONNumber json_number_double = JSONNumber.Double( 1.0 / 3.0 );
			JSONNumber json_number_bigint = JSONNumber.BigInteger( new BigInteger( "123456789012345678901234567890123456789012" ) );
			JSONNumber json_number_bigdec = JSONNumber.BigDecimal( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) );

			Charset charset = Charset.forName("UTF-8");
			JSONEncoder json_encoder = new JSONEncoderCharset( charset );

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JSONText json = new JSONText();

			JSONArray json_array = new JSONArray();
			json_array.add( json_number_int );
			json_array.add( json_number_long );
			json_array.add( json_number_float );
			json_array.add( json_number_double );
			json_array.add( json_number_bigint );
			json_array.add( json_number_bigdec );

			json.encodeJSONtext( json_array, json_encoder, out );

			// debug
			System.out.println( new String( out.toByteArray() )  );

			ByteArrayInputStream in = new ByteArrayInputStream( out.toByteArray() );
			JSONDecoder json_decoder = new JSONDecoderCharset( charset );

			JSONStructure json_structure = json.decodeJSONtext( in, json_decoder );

			Assert.assertNotNull( json_structure );
			Assert.assertEquals( JSONConstants.VT_ARRAY, json_structure.type );
			json_array = (JSONArray)json_structure;
			Assert.assertEquals( 6, json_array.values.size() );
			Assert.assertEquals( json_number_int, json_array.values.get( 0 ) );
			Assert.assertEquals( json_number_long, json_array.values.get( 1 ) );
			Assert.assertEquals( json_number_float, json_array.values.get( 2 ) );
			Assert.assertEquals( json_number_double, json_array.values.get( 3 ) );
			Assert.assertEquals( json_number_bigint, json_array.values.get( 4 ) );
			Assert.assertEquals( json_number_bigdec, json_array.values.get( 5 ) );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

	@Test
	public void test_jsonnumber_equals_hashcode() {
		JSONNumber json_number_int = JSONNumber.Integer( 42 );
		JSONNumber json_number_long = JSONNumber.Long( 12345678901234L );
		JSONNumber json_number_float = JSONNumber.Float( 1.0F / 3.0F );
		JSONNumber json_number_double = JSONNumber.Double( 1.0 / 3.0 );
		JSONNumber json_number_bigint = JSONNumber.BigInteger( new BigInteger( "123456789012345678901234567890123456789012" ) );
		JSONNumber json_number_bigdec = JSONNumber.BigDecimal( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) );

		Assert.assertFalse( json_number_int.equals( null ) );
		Assert.assertFalse( json_number_long.equals( null ) );
		Assert.assertFalse( json_number_float.equals( null ) );
		Assert.assertFalse( json_number_double.equals( null ) );
		Assert.assertFalse( json_number_bigint.equals( null ) );
		Assert.assertFalse( json_number_bigdec.equals( null ) );

		Assert.assertFalse( json_number_int.equals( "42" ) );
		Assert.assertFalse( json_number_long.equals( "12345678901234L" ) );
		Assert.assertFalse( json_number_float.equals( "1.0F / 3.0F" ) );
		Assert.assertFalse( json_number_double.equals( "1.0 / 3.0" ) );
		Assert.assertFalse( json_number_bigint.equals( "123456789012345678901234567890123456789012" ) );
		Assert.assertFalse( json_number_bigdec.equals( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) );

		Assert.assertFalse( json_number_int.equals( json_number_long ) );
		Assert.assertFalse( json_number_int.equals( json_number_float ) );
		Assert.assertFalse( json_number_int.equals( json_number_double ) );
		Assert.assertFalse( json_number_int.equals( json_number_bigint ) );
		Assert.assertFalse( json_number_int.equals( json_number_bigdec ) );

		Assert.assertFalse( json_number_long.equals( json_number_int ) );
		Assert.assertFalse( json_number_long.equals( json_number_float ) );
		Assert.assertFalse( json_number_long.equals( json_number_double ) );
		Assert.assertFalse( json_number_long.equals( json_number_bigint ) );
		Assert.assertFalse( json_number_long.equals( json_number_bigdec ) );

		Assert.assertFalse( json_number_float.equals( json_number_int ) );
		Assert.assertFalse( json_number_float.equals( json_number_long ) );
		Assert.assertFalse( json_number_float.equals( json_number_double ) );
		Assert.assertFalse( json_number_float.equals( json_number_bigint ) );
		Assert.assertFalse( json_number_float.equals( json_number_bigdec ) );

		Assert.assertFalse( json_number_double.equals( json_number_int ) );
		Assert.assertFalse( json_number_double.equals( json_number_long ) );
		Assert.assertFalse( json_number_double.equals( json_number_float ) );
		Assert.assertFalse( json_number_double.equals( json_number_bigint ) );
		Assert.assertFalse( json_number_double.equals( json_number_bigdec ) );

		Assert.assertFalse( json_number_bigint.equals( json_number_int ) );
		Assert.assertFalse( json_number_bigint.equals( json_number_long ) );
		Assert.assertFalse( json_number_bigint.equals( json_number_float ) );
		Assert.assertFalse( json_number_bigint.equals( json_number_double ) );
		Assert.assertFalse( json_number_bigint.equals( json_number_bigdec ) );

		Assert.assertFalse( json_number_bigdec.equals( json_number_int ) );
		Assert.assertFalse( json_number_bigdec.equals( json_number_long ) );
		Assert.assertFalse( json_number_bigdec.equals( json_number_float ) );
		Assert.assertFalse( json_number_bigdec.equals( json_number_double ) );
		Assert.assertFalse( json_number_bigdec.equals( json_number_bigint ) );

		Assert.assertTrue( json_number_int.equals( JSONNumber.Integer( 42 ) ) );
		Assert.assertTrue( json_number_long.equals( JSONNumber.Long( 12345678901234L ) ) );
		Assert.assertTrue( json_number_float.equals( JSONNumber.Float( 1.0F / 3.0F ) ) );
		Assert.assertTrue( json_number_double.equals( JSONNumber.Double( 1.0 / 3.0 ) ) );
		Assert.assertTrue( json_number_bigint.equals( JSONNumber.BigInteger( new BigInteger( "123456789012345678901234567890123456789012" ) ) ) );
		Assert.assertTrue( json_number_bigdec.equals( JSONNumber.BigDecimal( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) ) ) );

		Assert.assertEquals( json_number_int.hashCode(), JSONNumber.Integer( 42 ).hashCode() );
		Assert.assertEquals( json_number_long.hashCode(), JSONNumber.Long( 12345678901234L ).hashCode() );
		Assert.assertEquals( json_number_float.hashCode(), JSONNumber.Float( 1.0F / 3.0F ).hashCode() );
		Assert.assertEquals( json_number_double.hashCode(), JSONNumber.Double( 1.0 / 3.0 ).hashCode() );
		Assert.assertEquals( json_number_bigint.hashCode(), JSONNumber.BigInteger( new BigInteger( "123456789012345678901234567890123456789012" ) ).hashCode() );
		Assert.assertEquals( json_number_bigdec.hashCode(), JSONNumber.BigDecimal( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ) ).hashCode() );
	}

}
