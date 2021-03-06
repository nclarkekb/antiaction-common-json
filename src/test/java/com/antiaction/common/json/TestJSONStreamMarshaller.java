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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONNullable;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 21/10/2013
 */
@RunWith(JUnit4.class)
public class TestJSONStreamMarshaller {

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

		try {
			String text;
			byte[] bytes;
			PushbackInputStream pbin;
			int encoding;

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONDecoder json_decoder;

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();

			json_objectmappings.register( SFSResult.class );
			json_objectmappings.register( SFSResult.class );

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

			result = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, SFSResult.class );
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

			result = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, SFSResult.class );
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

			result = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, SFSResult.class );
			Assert.assertNotNull( result );
			Assert.assertFalse( result.success );
			Assert.assertEquals( "rate limit exceeded", result.error );
			Assert.assertNull( result.email );
			Assert.assertNull( result.ip );
			Assert.assertNull( result.username );

			try {
				json_objectmappings.register( boolean.class );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
				Assert.assertEquals( "Unsupported class type.", e.getMessage() );
			}

			json_objectmappings.register( TestClass.class );
			JSONObjectMapping objMapping = json_objectmappings.classMappings.get( TestClass.class.getName() );
			Assert.assertEquals( 0, objMapping.fieldMappingsList.size() );

			/*
			 * Types.
			 */

			TestTypesClass testTypes;

			try {
				testTypes = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, TestTypesClass.class );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
				Assert.assertEquals( "Class '" + TestTypesClass.class.getName() + "' not registered.", e.getMessage() );
			}

			json_objectmappings.register( TestTypesClass.class );

			text = "{"
					+ "\"b1\":true, \"b2\":false,"
					+ "\"bb1\":-128, \"bb2\":127,"
					+ "\"c1\":72, \"c2\":1234,"
					+ "\"i1\":1234, \"i2\":42,"
					+ "\"l1\":12345678901234, \"l2\":43210987654321,"
					+ "\"f1\":" + new Float( 1.0F / 3.0F ).toString() + ", \"f2\":" + new Float( 3.0F ).toString() + ","
					+ "\"d1\":" + new Double( 1.0 / 3.0 ).toString() + ", \"d2\":" + new Double( 3.0 ).toString() + ","
					+ "\"dt\":" + ctm + ","
					+ "\"ts\":" + ctm + ","
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

			testTypes = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, TestTypesClass.class );
			Assert.assertTrue( testTypes.b1 );
			Assert.assertFalse( testTypes.b2 );
			Assert.assertEquals( -128, testTypes.bb1 );
			Assert.assertEquals( new Byte( (byte)127 ), testTypes.bb2 );
			Assert.assertEquals( 72, testTypes.c1 );
			Assert.assertEquals( new Character( (char)1234 ), testTypes.c2 );
			Assert.assertEquals( 1234, testTypes.i1 );
			Assert.assertEquals( new Integer( 42 ), testTypes.i2 );
			Assert.assertEquals( 12345678901234L, testTypes.l1 );
			Assert.assertEquals( new Long( 43210987654321L ), testTypes.l2 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)testTypes.f1 );
			Assert.assertEquals( new Float( 3.0F ), testTypes.f2 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)testTypes.d1 );
			Assert.assertEquals( new Double( 3.0 ), testTypes.d2 );
			Assert.assertEquals( new Date( ctm ), testTypes.dt );
			Assert.assertEquals( new Timestamp( ctm ), testTypes.ts );
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

		private byte bb1;

		private char c1;

		private int i1;

		private long l1;

		private float f1;

		private double d1;

		@JSONNullable
		private Boolean b2;

		@JSONNullable
		private Byte bb2;

		@JSONNullable
		private Character c2;

		@JSONNullable
		private Integer i2;

		@JSONNullable
		private Long l2;

		@JSONNullable
		private Float f2;

		@JSONNullable
		private Double d2;

		@JSONNullable
		private Date dt;

		@JSONNullable
		private Timestamp ts;

		@JSONNullable
		private BigInteger bi;

		@JSONNullable
		private BigDecimal bd;

		@JSONNullable
		private String s;

		@JSONNullable
		private byte[] b;

	}

	public final long ctm = System.currentTimeMillis();

	@Test
	public void test_jsonobjectmapper_tojson() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMappings json_objectmappings = new JSONObjectMappings();
		try {
			try {
				json_objectmappings.getStructureMarshaller().toJSONStructure( new TestJSONMapObject() );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
				Assert.assertEquals( "Class '" + TestJSONMapObject.class.getName() + "' not registered.", e.getMessage() );
			}

			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
			JSONDecoder json_decoder = json_encoding.getJSONDecoder( JSONEncoding.E_UTF8 );

			json_objectmappings.register( TestJSONMapObject.class );

			TestJSONMapObject obj = new TestJSONMapObject();
			obj.b1 = true;
			obj.b2 = false;
			obj.bb1 = 0;
			obj.bb2 = 1;
			obj.c1 = 0;
			obj.c2 = 'A';
			obj.i1 = 42;
			obj.i2 = 1234;
			obj.l1 = 12345678901234L;
			obj.l2 = 43210987654321L;
			obj.f1 = 1.0F / 3.0F;
			obj.f2 = 3.0F;
			obj.dt = new Date( ctm );
			obj.ts = new Timestamp( ctm );
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
			obj2.bb1 = -128;
			obj2.bb2 = 127;
			obj2.c1 = 'H';
			obj2.c2 = 1234;
			obj2.i1 = 4213;
			obj2.i2 = 4321;
			obj2.l1 = 12345678901234L * 2L;
			obj2.l2 = 43210987654321L * 2L;
			obj2.f1 = 1.0F / 5.0F;
			obj2.f2 = 5.0F;
			obj2.d1 = 1.0 / 5.0;
			obj2.d2 = 5.0;
			obj2.dt = new Date( 1000 );
			obj2.ts = new Timestamp( 1000 );
			obj2.bi = new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) );
			obj2.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) );
			obj2.s = "JSON";
			obj2.b = "BYTES".getBytes();
			TestJSONMapObject obj3 = new TestJSONMapObject();
			obj2.obj = obj3;
			obj3.b1 = false;
			obj3.b2 = null;
			obj3.bb1 = 0;
			obj3.bb2 = null;
			obj3.c1 = 0;
			obj3.c2 = null;
			obj3.i1 = 4213;
			obj3.i2 = null;
			obj3.l1 = 12345678901234L * 2L;
			obj3.l2 = null;
			obj3.f1 = 1.0F / 5.0F;
			obj3.f2 = null;
			obj3.d1 = 1.0 / 5.0;
			obj3.d2 = null;
			obj3.dt = null;
			obj3.ts = null;
			obj3.bi = null;
			obj3.bd = null;
			obj3.s = null;
			obj3.b = null;

			out.reset();
			json_objectmappings.getStreamMarshaller().toJSONText( obj, json_encoder, false, out );
			TestJSONMapObject result;

			byte[] json_compact = out.toByteArray();
			// debug
			//System.out.println( new String( json_compact ) );

			in = new ByteArrayInputStream( json_compact );

			result = json_objectmappings.getStreamUnmarshaller().toObject( in, json_decoder, TestJSONMapObject.class );
			assert_jsonobjectmapper_tojson_result( result );

			out.reset();
			json_objectmappings.getStreamMarshaller().toJSONText( obj, json_encoder, true, out );

			byte[] json_pretty = out.toByteArray();
			// debug
			//System.out.println( new String( json_pretty ) );

			in = new ByteArrayInputStream( json_pretty );

			result = json_objectmappings.getStreamUnmarshaller().toObject( in, json_decoder, TestJSONMapObject.class );
			assert_jsonobjectmapper_tojson_result( result );

            Assert.assertThat( json_compact.length, is( not( equalTo( json_pretty.length ) ) ) );

            json_pretty = TestHelpers.filterWhitespaces( json_pretty );

            Assert.assertEquals( json_compact.length, json_pretty.length );
            Assert.assertArrayEquals( json_compact, json_pretty );
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
	}

	public void assert_jsonobjectmapper_tojson_result(TestJSONMapObject result) {
		Assert.assertNotNull( result );
		Assert.assertEquals( true, result.b1 );
		Assert.assertEquals( false, result.b2 );
		Assert.assertEquals( 0, result.bb1 );
		Assert.assertEquals( new Byte( (byte)1 ), result.bb2 );
		Assert.assertEquals( 0, result.c1 );
		Assert.assertEquals( new Character( 'A' ), result.c2 );
		Assert.assertEquals( 42, result.i1 );
		Assert.assertEquals( new Integer( 1234 ), result.i2 );
		Assert.assertEquals( 12345678901234L, result.l1 );
		Assert.assertEquals( new Long( 43210987654321L ), result.l2 );
		Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)result.f1 );
		Assert.assertEquals( new Float( 3.0F ), result.f2 );
		Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)result.d1 );
		Assert.assertEquals( new Double( 3.0 ), result.d2 );
		Assert.assertEquals( new Date( ctm ), result.dt );
		Assert.assertEquals( new Timestamp( ctm ), result.ts );
		Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), result.bi );
		Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), result.bd );
		Assert.assertEquals( "json", result.s );
		Assert.assertArrayEquals( "bytes".getBytes(), result.b );
		Assert.assertNotNull( result.obj );
		Assert.assertEquals( false, result.obj.b1 );
		Assert.assertEquals( true, result.obj.b2 );
		Assert.assertEquals( -128, result.obj.bb1 );
		Assert.assertEquals( new Byte( (byte)127 ), result.obj.bb2 );
		Assert.assertEquals( 'H', result.obj.c1 );
		Assert.assertEquals( new Character( (char)1234 ), result.obj.c2 );
		Assert.assertEquals( 4213, result.obj.i1 );
		Assert.assertEquals( new Integer( 4321 ), result.obj.i2 );
		Assert.assertEquals( 12345678901234L * 2L, result.obj.l1 );
		Assert.assertEquals( new Long( 43210987654321L * 2L ), result.obj.l2 );
		Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.f1 );
		Assert.assertEquals( new Float( 5.0F ), result.obj.f2 );
		Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.d1 );
		Assert.assertEquals( new Double( 5.0 ), result.obj.d2 );
		Assert.assertEquals( new Date( 1000 ), result.obj.dt );
		Assert.assertEquals( new Timestamp( 1000 ), result.obj.ts );
		Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ), result.obj.bi );
		Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ), result.obj.bd );
		Assert.assertEquals( "JSON", result.obj.s );
		Assert.assertArrayEquals( "BYTES".getBytes(), result.obj.b );
		Assert.assertNotNull( result.obj.obj );
		Assert.assertEquals( false, result.obj.obj.b1 );
		Assert.assertNull( result.obj.obj.b2 );
		Assert.assertEquals( 0, result.obj.obj.bb1 );
		Assert.assertNull( result.obj.obj.bb2 );
		Assert.assertEquals( 0, result.obj.obj.c1 );
		Assert.assertNull( result.obj.obj.c2 );
		Assert.assertEquals( 4213, result.obj.obj.i1 );
		Assert.assertNull( result.obj.obj.i2 );
		Assert.assertEquals( 12345678901234L * 2L, result.obj.obj.l1 );
		Assert.assertNull( result.obj.obj.l2 );
		Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.obj.f1 );
		Assert.assertNull( result.obj.obj.f2 );
		Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.obj.d1 );
		Assert.assertNull( result.obj.obj.d2 );
		Assert.assertNull( result.obj.obj.dt );
		Assert.assertNull( result.obj.obj.ts );
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

		private byte bb1;

		@JSONNullable
		private Byte bb2;

		private char c1;

		@JSONNullable
		private Character c2;

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
		private Date dt;

		@JSONNullable
		private Timestamp ts;

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

	/*
	@Test
	@Ignore
	public void test_jsonom_toarray() {
		JSONObject json_object = new JSONObject();
		JSONArray json_array = new JSONArray();
		json_object.put( "list", json_array );
		json_array.add( JSONNumber.Integer( 1 ) );
		json_array.add( JSONNumber.Integer( 2 ) );
		json_array.add( JSONNumber.Integer( 3 ) );

		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
		JSONDecoder json_decoder = json_encoding.getJSONDecoder( JSONEncoding.E_UTF8 );

		try {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			JSONObjectMappings json_om = new JSONObjectMappings();

			json_om.getTextMarshaller().toJSONText( json_object, json_encoder, false, bOut );

			// debug
			System.out.println( new String( bOut.toByteArray(), "UTF-8" ) );

			json_om.register( TestJSONMapArrayField.class );

			ByteArrayInputStream bIn = new ByteArrayInputStream( bOut.toByteArray() );

			TestJSONMapArrayField mapArray = json_om.getStreamUnmarshaller().toObject( bIn, json_decoder, TestJSONMapArrayField.class );

			// debug
			System.out.println( mapArray.list );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class TestJSONMapArrayField {
		public Integer[] list;
	}
	*/

}
