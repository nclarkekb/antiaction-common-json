/*
 * Created on 11/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	public void test_jsonobjectmapper_toobject() {
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
		//JSONArray json_array;
		//JSONObject json_object;
		//JSONObject json_object2;

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
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
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

		private boolean b1;

		private int i1;

		private long l1;

		private float f1;

		private double d1;

		@JSONNullable
		private Boolean b2;

		@JSONNullable
		private Integer i2;

		@JSONNullable
		private Long l2;

		@JSONNullable
		private Float f2;

		@JSONNullable
		private Double d2;

		@JSONNullable
		private BigInteger bi;

		@JSONNullable
		private BigDecimal bd;

		@JSONNullable
		private String s;

		@JSONNullable
		private byte[] b;

	}

	@Test
	public void test_jsonobjectmapper_tojson() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			try {
				json_om.toJSON( new TestJSONMapObject() );
				Assert.fail( "Exception expected!" );
			}
			catch (IllegalArgumentException e) {
			}

			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );

			json_om.register( TestJSONMapObject.class );

			TestJSONMapObject obj = new TestJSONMapObject();
			obj.b1 = true;
			obj.b2 = false;
			obj.i1 = 42;
			obj.i2 = 1234;
			obj.l1 = 12345678901234L;
			obj.l2 = 43210987654321L;
			obj.f1 = 1.0F / 3.0F;
			obj.f2 = 3.0F;
			obj.d1 = 1.0 / 3.0;
			obj.d2 = 3.0;
			obj.bi = new BigInteger( "123456789012345678901234567890123456789012" );
			obj.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" );
			obj.s = "json";
			obj.b = "bytes".getBytes();
			TestJSONMapObject obj2 = new TestJSONMapObject();
			obj.obj = obj2;
			obj2.b1 = false;
			obj2.b2 = true;
			obj2.i1 = 4213;
			obj2.i2 = 4321;
			obj2.l1 = 12345678901234L * 2L;
			obj2.l2 = 43210987654321L * 2L;
			obj2.f1 = 1.0F / 5.0F;
			obj2.f2 = 5.0F;
			obj2.d1 = 1.0 / 5.0;
			obj2.d2 = 5.0;
			obj2.bi = new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) );
			obj2.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) );
			obj2.s = "JSON";
			obj2.b = "BYTES".getBytes();
			TestJSONMapObject obj3 = new TestJSONMapObject();
			obj2.obj = obj3;
			obj3.b1 = false;
			obj3.b2 = null;
			obj3.i1 = 4213;
			obj3.i2 = null;
			obj3.l1 = 12345678901234L * 2L;
			obj3.l2 = null;
			obj3.f1 = 1.0F / 5.0F;
			obj3.f2 = null;
			obj3.d1 = 1.0 / 5.0;
			obj3.d2 = null;
			obj3.bi = null;
			obj3.bd = null;
			obj3.s = null;
			obj3.b = null;

			JSONStructure json_struct = json_om.toJSON( obj );
			TestJSONMapObject result;

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, false, out );

			System.out.println( new String( out.toByteArray() ) );

			result = json_om.toObject( json_struct, TestJSONMapObject.class );
			assert_jsonobjectmapper_tojson_result( result );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			result = json_om.toObject( json_struct, TestJSONMapObject.class );
			assert_jsonobjectmapper_tojson_result( result );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (InstantiationException e) {
				e.printStackTrace();
				Assert.fail( "Unexpected exception!" );
		}
	}

	public void assert_jsonobjectmapper_tojson_result(TestJSONMapObject result) {
		Assert.assertNotNull( result );
		Assert.assertEquals( true, result.b1 );
		Assert.assertEquals( false, result.b2 );
		Assert.assertEquals( 42, result.i1 );
		Assert.assertEquals( new Integer( 1234 ), result.i2 );
		Assert.assertEquals( 12345678901234L, result.l1 );
		Assert.assertEquals( new Long( 43210987654321L ), result.l2 );
		Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)result.f1 );
		Assert.assertEquals( new Float( 3.0F ), result.f2 );
		Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)result.d1 );
		Assert.assertEquals( new Double( 3.0 ), result.d2 );
		Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), result.bi );
		Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), result.bd );
		Assert.assertEquals( "json", result.s );
		Assert.assertArrayEquals( "bytes".getBytes(), result.b );
		Assert.assertNotNull( result.obj );
		Assert.assertEquals( false, result.obj.b1 );
		Assert.assertEquals( true, result.obj.b2 );
		Assert.assertEquals( 4213, result.obj.i1 );
		Assert.assertEquals( new Integer( 4321 ), result.obj.i2 );
		Assert.assertEquals( 12345678901234L * 2L, result.obj.l1 );
		Assert.assertEquals( new Long( 43210987654321L * 2L ), result.obj.l2 );
		Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.f1 );
		Assert.assertEquals( new Float( 5.0F ), result.obj.f2 );
		Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.d1 );
		Assert.assertEquals( new Double( 5.0 ), result.obj.d2 );
		Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ), result.obj.bi );
		Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ), result.obj.bd );
		Assert.assertEquals( "JSON", result.obj.s );
		Assert.assertArrayEquals( "BYTES".getBytes(), result.obj.b );
		Assert.assertNotNull( result.obj.obj );
		Assert.assertEquals( false, result.obj.obj.b1 );
		Assert.assertNull( result.obj.obj.b2 );
		Assert.assertEquals( 4213, result.obj.obj.i1 );
		Assert.assertNull( result.obj.obj.i2 );
		Assert.assertEquals( 12345678901234L * 2L, result.obj.obj.l1 );
		Assert.assertNull( result.obj.obj.l2 );
		Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.obj.f1 );
		Assert.assertNull( result.obj.obj.f2 );
		Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.obj.d1 );
		Assert.assertNull( result.obj.obj.d2 );
		Assert.assertNull( result.obj.obj.bi );
		Assert.assertNull( result.obj.obj.bd );
		Assert.assertNull( result.obj.obj.s );
		Assert.assertNull( result.obj.obj.b );
		Assert.assertNull( result.obj.obj.obj );
	}

	public static class TestJSONMapObject {

		private boolean b1;

		@JSONNullable
		private Boolean b2;

		private int i1;

		@JSONNullable
		private Integer i2;

		private long l1;

		@JSONNullable
		private Long l2;

		private float f1;

		@JSONNullable
		private Float f2;

		private double d1;

		@JSONNullable
		private Double d2;

		@JSONNullable
		private BigInteger bi;

		@JSONNullable
		private BigDecimal bd;

		@JSONNullable
		private String s;

		@JSONNullable
		private byte[] b;

		@JSONNullable
		private TestJSONMapObject obj;

	}

	@Test
	public void test_jsonobjectmapper_array() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );

			json_om.register( TestJSONMapObjectArrays.class );

			TestJSONMapObjectArrays obj = new TestJSONMapObjectArrays();
			obj.b1_arr = new boolean[] { true, false };
			obj.i1_arr = new int[] { 42, 4213 };
			obj.l1_arr = new long[] { 12345678901234L, 12345678901234L * 2L };
			obj.f1_arr = new float[] { 1.0F / 3.0F, 1.0F / 5.0F };
			obj.d1_arr = new double[] { 1.0 / 3.0, 1.0 / 5.0 };
			obj.b2_arr = new Boolean[] { false, true };
			obj.i2_arr = new Integer[] { 1234, 4321 };
			obj.l2_arr = new Long[] { 43210987654321L, 43210987654321L * 2L };
			obj.f2_arr = new Float[] { 3.0F, 5.0F };
			obj.d2_arr = new Double[] { 3.0, 5.0 };
			obj.bi_arr = new BigInteger[] { new BigInteger( "123456789012345678901234567890123456789012" ), new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ) };
			obj.bd_arr = new BigDecimal[] { new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ) };
			obj.s_arr = new String[] { "json", "JSON" };
			//obj.b = "bytes".getBytes();
			TestJSONMapObjectArrays obj2 = new TestJSONMapObjectArrays();
			obj.obj_arr = new TestJSONMapObjectArrays[] { obj2 };
			obj2.b1_arr = new boolean[] {};
			obj2.i1_arr = new int[] {};
			obj2.l1_arr = new long[] {};
			obj2.f1_arr = new float[] {};
			obj2.d1_arr = new double[] {};
			obj2.b2_arr = new Boolean[] {};
			obj2.i2_arr = new Integer[] {};
			obj2.l2_arr = new Long[] {};
			obj2.f2_arr = new Float[] {};
			obj2.d2_arr = new Double[] {};
			obj2.bi_arr = new BigInteger[] {};
			obj2.bd_arr = new BigDecimal[] {};
			obj2.s_arr = new String[] {};
			//obj2.b = "BYTES".getBytes();
			obj2.obj_arr = new TestJSONMapObjectArrays[] { new TestJSONMapObjectArrays() };

			JSONStructure json_struct = json_om.toJSON( obj );

			TestJSONMapObjectArrays result;

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, false, out );

			System.out.println( new String( out.toByteArray() ) );

			result = json_om.toObject( json_struct, TestJSONMapObjectArrays.class );
			assert_jsonobjectmapper_array_result( result );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			result = json_om.toObject( json_struct, TestJSONMapObjectArrays.class );
			assert_jsonobjectmapper_array_result( result );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
		catch (InstantiationException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );		
		}
	}

	public static class TestJSONMapObjectArrays {

		@JSONNullable
		public boolean[] b1_arr;

		@JSONNullable
		public int[] i1_arr;

		@JSONNullable
		public long[] l1_arr;

		@JSONNullable
		public float[] f1_arr;

		@JSONNullable
		public double[] d1_arr;

		@JSONNullable
		public Boolean[] b2_arr;

		@JSONNullable
		public Integer[] i2_arr;

		@JSONNullable
		public Long[] l2_arr;

		@JSONNullable
		public Float[] f2_arr;

		@JSONNullable
		public Double[] d2_arr;

		@JSONNullable
		public BigInteger[] bi_arr;

		@JSONNullable
		public BigDecimal[] bd_arr;

		@JSONNullable
		public String[] s_arr;

		@JSONNullable
		public TestJSONMapObjectArrays[] obj_arr;

	}

	public void assert_jsonobjectmapper_array_result(TestJSONMapObjectArrays result) {
		boolean[] b1_arr = new boolean[] { true, false };
		int[] i1_arr = new int[] { 42, 4213 };
		long[] l1_arr = new long[] { 12345678901234L, 12345678901234L * 2L };
		float[] f1_arr = new float[] { 1.0F / 3.0F, 1.0F / 5.0F };
		double[] d1_arr = new double[] { 1.0 / 3.0, 1.0 / 5.0 };
		Boolean[] b2_arr = new Boolean[] { false, true };
		Integer[] i2_arr = new Integer[] { 1234, 4321 };
		Long[] l2_arr = new Long[] { 43210987654321L, 43210987654321L * 2L };
		Float[] f2_arr = new Float[] { 3.0F, 5.0F };
		Double[] d2_arr = new Double[] { 3.0, 5.0 };
		BigInteger[] bi_arr = new BigInteger[] { new BigInteger( "123456789012345678901234567890123456789012" ), new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ) };
		BigDecimal[] bd_arr = new BigDecimal[] { new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ) };
		String[] s_arr = new String[] { "json", "JSON" };

		Assert.assertNotNull( result );
		assertArrayEquals( b1_arr, result.b1_arr );
		Assert.assertArrayEquals( i1_arr, result.i1_arr );
		Assert.assertArrayEquals( l1_arr, result.l1_arr );
		assertArrayEquals( f1_arr, result.f1_arr );
		assertArrayEquals( d1_arr, result.d1_arr );
		Assert.assertArrayEquals( b2_arr, result.b2_arr );
		Assert.assertArrayEquals( i2_arr, result.i2_arr );
		Assert.assertArrayEquals( l2_arr, result.l2_arr );
		Assert.assertArrayEquals( f2_arr, result.f2_arr );
		Assert.assertArrayEquals( d2_arr, result.d2_arr );
		Assert.assertArrayEquals( bi_arr, result.bi_arr );
		Assert.assertArrayEquals( bd_arr, result.bd_arr );
		Assert.assertArrayEquals( s_arr, result.s_arr );

		b1_arr = new boolean[] {};
		i1_arr = new int[] {};
		l1_arr = new long[] {};
		f1_arr = new float[] {};
		d1_arr = new double[] {};
		b2_arr = new Boolean[] {};
		i2_arr = new Integer[] {};
		l2_arr = new Long[] {};
		f2_arr = new Float[] {};
		d2_arr = new Double[] {};
		bi_arr = new BigInteger[] {};
		bd_arr = new BigDecimal[] {};
		s_arr = new String[] {};

		Assert.assertNotNull( result.obj_arr );
		Assert.assertEquals( 1, result.obj_arr.length );
		result = result.obj_arr[ 0 ];

		assertArrayEquals( b1_arr, result.b1_arr );
		Assert.assertArrayEquals( i1_arr, result.i1_arr );
		Assert.assertArrayEquals( l1_arr, result.l1_arr );
		assertArrayEquals( f1_arr, result.f1_arr );
		assertArrayEquals( d1_arr, result.d1_arr );
		Assert.assertArrayEquals( b2_arr, result.b2_arr );
		Assert.assertArrayEquals( i2_arr, result.i2_arr );
		Assert.assertArrayEquals( l2_arr, result.l2_arr );
		Assert.assertArrayEquals( f2_arr, result.f2_arr );
		Assert.assertArrayEquals( d2_arr, result.d2_arr );
		Assert.assertArrayEquals( bi_arr, result.bi_arr );
		Assert.assertArrayEquals( bd_arr, result.bd_arr );
		Assert.assertArrayEquals( s_arr, result.s_arr );

		Assert.assertNotNull( result.obj_arr );
		Assert.assertEquals( 1, result.obj_arr.length );
		result = result.obj_arr[ 0 ];

		Assert.assertNull( result.b1_arr );
		Assert.assertNull( result.i1_arr );
		Assert.assertNull( result.l1_arr );
		Assert.assertNull( result.f1_arr );
		Assert.assertNull( result.d1_arr );
		Assert.assertNull( result.b2_arr );
		Assert.assertNull( result.i2_arr );
		Assert.assertNull( result.l2_arr );
		Assert.assertNull( result.f2_arr );
		Assert.assertNull( result.d2_arr );
		Assert.assertNull( result.bi_arr );
		Assert.assertNull( result.bd_arr );
		Assert.assertNull( result.s_arr );
		Assert.assertNull( result.obj_arr );
	}

	public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( expecteds[ i ], actuals[ i ] );
		}
	}

	public static void assertArrayEquals(float[] expecteds, float[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Float)expecteds[ i ], (Float)actuals[ i ] );
		}
	}

	public static void assertArrayEquals(double[] expecteds, double[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Double)expecteds[ i ], (Double)actuals[ i ] );
		}
	}

}
