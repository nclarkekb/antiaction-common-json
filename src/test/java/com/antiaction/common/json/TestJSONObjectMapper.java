/*
 * Created on 11/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper {

	@Test
	public void test_jsonobjectmapper() {
		/*
		System.out.println( String.class.isPrimitive() + " - " + String.class.getName() );
		System.out.println( boolean.class.isPrimitive() + " - " + boolean.class.getName() );
		System.out.println( Boolean.class.isPrimitive() + " - " + Boolean.class.getName() );
		System.out.println( int.class.isPrimitive() + " - " + int.class.getName() );
		System.out.println( Integer.class.isPrimitive() + " - " + Integer.class.getName() );
		System.out.println( long.class.isPrimitive() + " - " + long.class.getName() );
		System.out.println( Long.class.isPrimitive() + " - " + Long.class.getName() );
		System.out.println( float.class.isPrimitive() + " - " + float.class.getName() );
		System.out.println( Float.class.isPrimitive() + " - " + Float.class.getName() );
		System.out.println( double.class.isPrimitive() + " - " + double.class.getName() );
		System.out.println( Double.class.isPrimitive() + " - " + Double.class.getName() );
		System.out.println( BigInteger.class.isPrimitive() + " - " + BigInteger.class.getName() );
		System.out.println( BigDecimal.class.isPrimitive() + " - " + BigDecimal.class.getName() );
		System.out.println( byte[].class.isPrimitive() + " - " + byte[].class.getName() );
		*/

		JSONStructure json_struct;
		JSONArray json_array;
		JSONObject json_object;
		JSONObject json_object2;

		try {
			String text;
			byte[] bytes;
			PushbackInputStream pbin;
			int encoding;

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONDecoder json_decoder;
			JSONText json = new JSONText();

			JSONObjectMapper json_om = new JSONObjectMapper();

			json_om.register( SFSResult.class );
			json_om.register( SFSResult.class );

			SFSResult result;

			/*
			 * 1.
			 */

			text = "{\"success\":1,\"username\":{\"frequency\":0,\"appears\":0},\"email\":{\"frequency\":0,\"appears\":0},\"ip\":{\"frequency\":0,\"appears\":0}}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			result = json_om.toObject( json_struct, SFSResult.class );
			Assert.assertNotNull( result );
			Assert.assertTrue( result.success );
			Assert.assertNull( result.error );
			Assert.assertNotNull( result.email );
			Assert.assertEquals( 0, result.email.appears );
			Assert.assertEquals( 0, result.email.frequency );
			Assert.assertNull( result.email.lastseen );
			Assert.assertNull( result.email.confidence );
			Assert.assertNotNull( result.ip );
			Assert.assertEquals( 0, result.ip.appears );
			Assert.assertEquals( 0, result.ip.frequency );
			Assert.assertNull( result.ip.lastseen );
			Assert.assertNull( result.ip.confidence );
			Assert.assertNotNull( result.username );
			Assert.assertEquals( 0, result.username.appears );
			Assert.assertEquals( 0, result.username.frequency );
			Assert.assertNull( result.username.lastseen );
			Assert.assertNull( result.username.confidence );

			/*
			 * 2.
			 */

			text = "{\"success\":1,\"username\":{\"frequency\":0,\"appears\":0},\"email\":{\"lastseen\":\"2012-11-15 20:30:38\",\"frequency\":166,\"appears\":1,\"confidence\":99.1},\"ip\":{\"lastseen\":\"2012-11-15 20:30:38\",\"frequency\":54,\"appears\":1,\"confidence\":97.3}}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			result = json_om.toObject( json_struct, SFSResult.class );
			Assert.assertNotNull( result );
			Assert.assertTrue( result.success );
			Assert.assertNull( result.error );
			Assert.assertNotNull( result.email );
			Assert.assertEquals( 1, result.email.appears );
			Assert.assertEquals( 166, result.email.frequency );
			Assert.assertEquals( "2012-11-15 20:30:38", result.email.lastseen );
			Assert.assertEquals( new Double(99.1), result.email.confidence );
			Assert.assertNotNull( result.ip );
			Assert.assertEquals( 1, result.ip.appears );
			Assert.assertEquals( 54, result.ip.frequency );
			Assert.assertEquals( "2012-11-15 20:30:38", result.ip.lastseen );
			Assert.assertEquals( new Double(97.3), result.ip.confidence );
			Assert.assertNotNull( result.username );
			Assert.assertEquals( 0, result.username.appears );
			Assert.assertEquals( 0, result.username.frequency );
			Assert.assertNull( result.username.lastseen );
			Assert.assertNull( result.username.confidence );

			/*
			 * 3.
			 */

			text = "{\"success\":0,\"error\":\"rate limit exceeded\"}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			result = json_om.toObject( json_struct, SFSResult.class );
			Assert.assertNotNull( result );
			Assert.assertFalse( result.success );
			Assert.assertEquals( "rate limit exceeded", result.error );
			Assert.assertNull( result.email );
			Assert.assertNull( result.ip );
			Assert.assertNull( result.username );

			try {
				json_om.register( boolean.class );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
			}
			try {
				json_om.register( byte[].class );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
			}

			json_om.register( TestClass.class );
			JSONObjectMapping objMapping = JSONObjectMapper.classMappings.get( TestClass.class.getName() );
			Assert.assertEquals( 0, objMapping.fieldMappingsList.size() );

			/*
			 * Types.
			 */

			TestTypesClass testTypes;

			try {
				testTypes = json_om.toObject( json_struct, TestTypesClass.class );
			}
			catch (IllegalArgumentException e) {
			}

			json_om.register( TestTypesClass.class );

			text = "{"
					+ "\"b1\":true, \"b2\":false,"
					+ "\"i1\":1234, \"i2\":42,"
					+ "\"l1\":12345678901234, \"l2\":43210987654321,"
					+ "\"f1\":" + new Float( 1.0F / 3.0F ).toString() + ", \"f2\":" + new Float( 3.0F ).toString() + ","
					+ "\"d1\":" + new Double( 1.0 / 3.0 ).toString() + ", \"d2\":" + new Double( 3.0 ).toString() + ","
					+ "\"bi\":" + new BigInteger( "123456789012345678901234567890123456789012" ).toString() + ","
					+ "\"bd\":" + new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).toString() + ","
					+ "\"s\":\"mapping\","
					+ "\"b\":\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007\""
					+ "}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			testTypes = json_om.toObject( json_struct, TestTypesClass.class );
			Assert.assertTrue( testTypes.b1 );
			Assert.assertFalse( testTypes.b2 );
			Assert.assertEquals( 1234, testTypes.i1 );
			Assert.assertEquals( new Integer( 42 ), testTypes.i2 );
			Assert.assertEquals( 12345678901234L, testTypes.l1 );
			Assert.assertEquals( new Long( 43210987654321L ), testTypes.l2 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)testTypes.f1 );
			Assert.assertEquals( new Float( 3.0F ), testTypes.f2 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)testTypes.d1 );
			Assert.assertEquals( new Double( 3.0 ), testTypes.d2 );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), testTypes.bi );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), testTypes.bd );
			Assert.assertEquals( "mapping", testTypes.s );
			byte[] eb = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };
			Assert.assertArrayEquals( eb, testTypes.b );
			/*
			 */
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

	@JSON(ignore={"$jacocoData"})
	public static class SFSResult {

		public boolean success;

		@JSONNullable
		public String error;

		@JSONNullable
		public SPSFieldResult username;

		@JSONNullable
		public SPSFieldResult email;

		@JSONNullable
		public SPSFieldResult ip;

	}

	@JSON(ignore={"$jacocoData"})
	public static class SPSFieldResult {

		@JSONNullable
		public String lastseen;

		public int frequency;

		public int appears;

		@JSONNullable
		public Double confidence;

	}

	@JSON(ignore={"$jacocoData", "jsonignore"})
	public static class TestClass {

		public transient String transientModifier = null;

		@JSONIgnore
		public String ignoreAnnotation = null;

		public String jsonignore = null;

		public static String staticModifiers = null;

	}

	public static class TestTypesClass {

		public boolean b1;

		public Boolean b2;

		public int i1;

		public Integer i2;

		public long l1;

		public Long l2;

		public float f1;

		public Float f2;

		public double d1;

		public Double d2;

		public BigInteger bi;

		public BigDecimal bd;

		public String s;

		public byte[] b;

	}

}
