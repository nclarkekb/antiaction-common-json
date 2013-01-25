/*
 * Created on 15/12/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Nullable {

	@Test
	public void test_jsonobjectmapper_register_nullable_invalid() {
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			json_om.register( TestNullableField1Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField2Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField3Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField4Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField5Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
	}

	public static class TestNullableField1Class {
		@JSONNullable
		boolean b1;
	}

	public static class TestNullableField2Class {
		@JSONNullable
		int i1;
	}

	public static class TestNullableField3Class {
		@JSONNullable
		long l1;
	}

	public static class TestNullableField4Class {
		@JSONNullable
		float f1;
	}

	public static class TestNullableField5Class {
		@JSONNullable
		double d1;
	}

	@Test
	public void test_jsonobjectmapper_toobject_nullable() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );

			json_om.register( TestJSONMapObjectNullable.class );
			json_om.register( TestJSONMapObject.class );

			JSONStructure json_struct;

			/*
			 * Nullable valid.
			 */

			TestJSONMapObjectNullable obj = new TestJSONMapObjectNullable();
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

			TestJSONMapObjectNullable obj3 = new TestJSONMapObjectNullable();
			obj.obj = obj3;
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

			json_struct = json_om.toJSON( obj );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			TestJSONMapObjectNullable result = json_om.toObject( json_struct, TestJSONMapObjectNullable.class );

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
			Assert.assertNull( result.obj.b2 );
			Assert.assertEquals( 4213, result.obj.i1 );
			Assert.assertNull( result.obj.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result.obj.l1 );
			Assert.assertNull( result.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.f1 );
			Assert.assertNull( result.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.d1 );
			Assert.assertNull( result.obj.d2 );
			Assert.assertNull( result.obj.bi );
			Assert.assertNull( result.obj.bd );
			Assert.assertNull( result.obj.s );
			Assert.assertNull( result.obj.b );
			Assert.assertNull( result.obj.obj );

			/*
			 * Non nullable valid.
			 */

			TestJSONMapObject obj2 = new TestJSONMapObject();
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
			obj2.obj = obj;

			json_struct = json_om.toJSON( obj2 );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			TestJSONMapObject result2 = json_om.toObject( json_struct, TestJSONMapObject.class );

			Assert.assertNotNull( result2 );
			Assert.assertEquals( false, result2.b1 );
			Assert.assertEquals( true, result2.b2 );
			Assert.assertEquals( 4213, result2.i1 );
			Assert.assertEquals( new Integer( 4321 ), result2.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result2.l1 );
			Assert.assertEquals( new Long( 43210987654321L * 2L ), result2.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result2.f1 );
			Assert.assertEquals( new Float( 5.0F ), result2.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result2.d1 );
			Assert.assertEquals( new Double( 5.0 ), result2.d2 );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ), result2.bi );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ), result2.bd );
			Assert.assertEquals( "JSON", result2.s );
			Assert.assertArrayEquals( "BYTES".getBytes(), result2.b );
			Assert.assertNotNull( result2.obj );
			Assert.assertEquals( true, result2.obj.b1 );
			Assert.assertEquals( false, result2.obj.b2 );
			Assert.assertEquals( 42, result2.obj.i1 );
			Assert.assertEquals( new Integer( 1234 ), result2.obj.i2 );
			Assert.assertEquals( 12345678901234L, result2.obj.l1 );
			Assert.assertEquals( new Long( 43210987654321L ), result2.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)result2.obj.f1 );
			Assert.assertEquals( new Float( 3.0F ), result2.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)result2.obj.d1 );
			Assert.assertEquals( new Double( 3.0 ), result2.obj.d2 );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), result2.obj.bi );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), result2.obj.bd );
			Assert.assertEquals( "json", result2.obj.s );
			Assert.assertArrayEquals( "bytes".getBytes(), result2.obj.b );
			Assert.assertNotNull( result2.obj.obj );
			Assert.assertEquals( false, result2.obj.obj.b1 );
			Assert.assertNull( result2.obj.obj.b2 );
			Assert.assertEquals( 4213, result2.obj.obj.i1 );
			Assert.assertNull( result2.obj.obj.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result2.obj.obj.l1 );
			Assert.assertNull( result2.obj.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result2.obj.obj.f1 );
			Assert.assertNull( result2.obj.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result2.obj.obj.d1 );
			Assert.assertNull( result2.obj.obj.d2 );
			Assert.assertNull( result2.obj.obj.bi );
			Assert.assertNull( result2.obj.obj.bd );
			Assert.assertNull( result2.obj.obj.s );
			Assert.assertNull( result2.obj.obj.b );
			Assert.assertNull( result2.obj.obj.obj );
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

	public static class TestJSONMapObjectNullable {

		protected boolean b1;

		protected int i1;

		protected long l1;

		protected float f1;

		protected double d1;

		@JSONNullable
		protected Boolean b2;

		@JSONNullable
		protected Integer i2;

		@JSONNullable
		protected Long l2;

		@JSONNullable
		protected Float f2;

		@JSONNullable
		protected Double d2;

		@JSONNullable
		protected BigInteger bi;

		@JSONNullable
		protected BigDecimal bd;

		@JSONNullable
		protected String s;

		@JSONNullable
		protected byte[] b;

		@JSONNullable
		protected TestJSONMapObjectNullable obj;

	}

	public static class TestJSONMapObject {

		protected boolean b1;

		protected int i1;

		protected long l1;

		protected float f1;

		protected double d1;

		protected Boolean b2;

		protected Integer i2;

		protected Long l2;

		protected Float f2;

		protected Double d2;

		protected BigInteger bi;

		protected BigDecimal bd;

		protected String s;

		protected byte[] b;

		protected TestJSONMapObjectNullable obj;

	}

	@Test
	public void test_jsonobjectmapper_toobject_nullable_annotation_value() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );

			json_om.register( TestJSONMapObjectNullableWithAV.class );
			json_om.register( TestJSONMapObjectWithAV.class );

			JSONStructure json_struct;

			/*
			 * Nullable valid.
			 */

			TestJSONMapObjectNullableWithAV obj = new TestJSONMapObjectNullableWithAV();
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

			TestJSONMapObjectNullableWithAV obj3 = new TestJSONMapObjectNullableWithAV();
			obj.obj = obj3;
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

			json_struct = json_om.toJSON( obj );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			TestJSONMapObjectNullableWithAV result = json_om.toObject( json_struct, TestJSONMapObjectNullableWithAV.class );

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
			Assert.assertNull( result.obj.b2 );
			Assert.assertEquals( 4213, result.obj.i1 );
			Assert.assertNull( result.obj.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result.obj.l1 );
			Assert.assertNull( result.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result.obj.f1 );
			Assert.assertNull( result.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result.obj.d1 );
			Assert.assertNull( result.obj.d2 );
			Assert.assertNull( result.obj.bi );
			Assert.assertNull( result.obj.bd );
			Assert.assertNull( result.obj.s );
			Assert.assertNull( result.obj.b );
			Assert.assertNull( result.obj.obj );

			/*
			 * Non nullable valid.
			 */

			TestJSONMapObjectWithAV obj2 = new TestJSONMapObjectWithAV();
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
			obj2.obj = obj;

			json_struct = json_om.toJSON( obj2 );

			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			System.out.println( new String( out.toByteArray() ) );

			TestJSONMapObjectWithAV result2 = json_om.toObject( json_struct, TestJSONMapObjectWithAV.class );

			Assert.assertNotNull( result2 );
			Assert.assertEquals( false, result2.b1 );
			Assert.assertEquals( true, result2.b2 );
			Assert.assertEquals( 4213, result2.i1 );
			Assert.assertEquals( new Integer( 4321 ), result2.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result2.l1 );
			Assert.assertEquals( new Long( 43210987654321L * 2L ), result2.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result2.f1 );
			Assert.assertEquals( new Float( 5.0F ), result2.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result2.d1 );
			Assert.assertEquals( new Double( 5.0 ), result2.d2 );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ), result2.bi );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ), result2.bd );
			Assert.assertEquals( "JSON", result2.s );
			Assert.assertArrayEquals( "BYTES".getBytes(), result2.b );
			Assert.assertNotNull( result2.obj );
			Assert.assertEquals( true, result2.obj.b1 );
			Assert.assertEquals( false, result2.obj.b2 );
			Assert.assertEquals( 42, result2.obj.i1 );
			Assert.assertEquals( new Integer( 1234 ), result2.obj.i2 );
			Assert.assertEquals( 12345678901234L, result2.obj.l1 );
			Assert.assertEquals( new Long( 43210987654321L ), result2.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)result2.obj.f1 );
			Assert.assertEquals( new Float( 3.0F ), result2.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)result2.obj.d1 );
			Assert.assertEquals( new Double( 3.0 ), result2.obj.d2 );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), result2.obj.bi );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), result2.obj.bd );
			Assert.assertEquals( "json", result2.obj.s );
			Assert.assertArrayEquals( "bytes".getBytes(), result2.obj.b );
			Assert.assertNotNull( result2.obj.obj );
			Assert.assertEquals( false, result2.obj.obj.b1 );
			Assert.assertNull( result2.obj.obj.b2 );
			Assert.assertEquals( 4213, result2.obj.obj.i1 );
			Assert.assertNull( result2.obj.obj.i2 );
			Assert.assertEquals( 12345678901234L * 2L, result2.obj.obj.l1 );
			Assert.assertNull( result2.obj.obj.l2 );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), (Float)result2.obj.obj.f1 );
			Assert.assertNull( result2.obj.obj.f2 );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), (Double)result2.obj.obj.d1 );
			Assert.assertNull( result2.obj.obj.d2 );
			Assert.assertNull( result2.obj.obj.bi );
			Assert.assertNull( result2.obj.obj.bd );
			Assert.assertNull( result2.obj.obj.s );
			Assert.assertNull( result2.obj.obj.b );
			Assert.assertNull( result2.obj.obj.obj );
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

	public static class TestJSONMapObjectNullableWithAV {

		@JSONNullable(false)
		protected boolean b1;

		@JSONNullable(false)
		protected int i1;

		@JSONNullable(false)
		protected long l1;

		@JSONNullable(false)
		protected float f1;

		@JSONNullable(false)
		protected double d1;

		@JSONNullable(true)
		protected Boolean b2;

		@JSONNullable(true)
		protected Integer i2;

		@JSONNullable(true)
		protected Long l2;

		@JSONNullable(true)
		protected Float f2;

		@JSONNullable(true)
		protected Double d2;

		@JSONNullable(true)
		protected BigInteger bi;

		@JSONNullable(true)
		protected BigDecimal bd;

		@JSONNullable(true)
		protected String s;

		@JSONNullable(true)
		protected byte[] b;

		@JSONNullable(true)
		protected TestJSONMapObjectNullableWithAV obj;

	}

	public static class TestJSONMapObjectWithAV {

		@JSONNullable(false)
		protected boolean b1;

		@JSONNullable(false)
		protected int i1;

		@JSONNullable(false)
		protected long l1;

		@JSONNullable(false)
		protected float f1;

		@JSONNullable(false)
		protected double d1;

		@JSONNullable(false)
		protected Boolean b2;

		@JSONNullable(false)
		protected Integer i2;

		@JSONNullable(false)
		protected Long l2;

		@JSONNullable(false)
		protected Float f2;

		@JSONNullable(false)
		protected Double d2;

		@JSONNullable(false)
		protected BigInteger bi;

		@JSONNullable(false)
		protected BigDecimal bd;

		@JSONNullable(false)
		protected String s;

		@JSONNullable(false)
		protected byte[] b;

		@JSONNullable(false)
		protected TestJSONMapObjectNullableWithAV obj;

	}

	@Test
	public void test_jsonboejctmapper_toobject_nullable_invalid() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONObjectMapper json_om = new JSONObjectMapper();
		JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
		try {
			json_om.register( TestJSONMapObjectNull.class );
			json_om.register( TestJSONMapObjectNullable.class );
			json_om.register( TestJSONMapObject.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		TestJSONMapObjectNull moldObject;

		try {
			/*
			 * DefaultAV.
			 */
			moldObject = get_toObject_MoldObject();
			moldObject.b1 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.b2 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.i1 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.i2 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.l1 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.l2 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.f1 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.f2 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.d1 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.d2 = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.bi = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.bd = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.s = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.b = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			moldObject = get_toObject_MoldObject();
			moldObject.obj = null;
			assert_toObject_exception( moldObject, out, json, json_encoder, json_om );

			String[] fields = { "b1", "i1", "l1", "f1", "d1", "b2", "i2", "l2", "f2", "d2", "bi", "bd", "s", "b", "obj" };
			for ( int i=0; i<fields.length; ++i ) {
				JSONStructure json_struct;
				try {
					json_struct = json_om.toJSON( moldObject );
					((JSONObject)json_struct).values.remove( JSONString.String( fields[ i ] ) );
					out.reset();
					json.encodeJSONtext( json_struct, json_encoder, true, out );
					// debug
					//System.out.println( new String( out.toByteArray() ) );
					json_om.toObject( json_struct, TestJSONMapObject.class );
					Assert.fail( "Exception expected!" );
				}
				catch (JSONException e) {
					// debug
					//e.printStackTrace();
				}
			}
			/*
			 * WithAV.
			 */
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

	public void assert_toObject_exception(TestJSONMapObjectNull moldObject, ByteArrayOutputStream out, JSONText json, JSONEncoder json_encoder, JSONObjectMapper json_om) throws IOException, InstantiationException, IllegalAccessException {
		JSONStructure json_struct = null;
		try {
			json_struct = json_om.toJSON( moldObject );
			out.reset();
			json.encodeJSONtext( json_struct, json_encoder, true, out );
			// debug
			//System.out.println( new String( out.toByteArray() ) );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_om.toObject( json_struct, TestJSONMapObject.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			// debug
			//e.printStackTrace();
		}
	}

	public TestJSONMapObjectNull get_toObject_MoldObject() {
		TestJSONMapObjectNull obj = new TestJSONMapObjectNull();
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
		TestJSONMapObjectNullable obj2 = new TestJSONMapObjectNullable();
		obj.obj = obj2;
		obj2.b1 = false;
		obj2.b2 = null;
		obj2.i1 = 4213;
		obj2.i2 = null;
		obj2.l1 = 12345678901234L * 2L;
		obj2.l2 = null;
		obj2.f1 = 1.0F / 5.0F;
		obj2.f2 = null;
		obj2.d1 = 1.0 / 5.0;
		obj2.d2 = null;
		obj2.bi = null;
		obj2.bd = null;
		obj2.s = null;
		obj2.b = null;
		return obj;
	}

	@JSON(nullable={"b1", "i1", "l1", "f1", "d1", "b2", "i2", "l2", "f2", "d2", "bi", "bd", "s", "b", "obj"})
	public static class TestJSONMapObjectNull {

		protected Boolean b1;

		protected Integer i1;

		protected Long l1;

		protected Float f1;

		protected Double d1;

		protected Boolean b2;

		protected Integer i2;

		protected Long l2;

		protected Float f2;

		protected Double d2;

		protected BigInteger bi;

		protected BigDecimal bd;

		protected String s;

		protected byte[] b;

		protected TestJSONMapObjectNullable obj;

	}

	@Test
	public void test_jsonobjectmapper_tojson_nullable_invalid() {
		JSONObjectMapper json_om = new JSONObjectMapper();
		TestJSONMapObject moldObject;
		TestJSONMapObjectWithAV moldObjectWithAV;
		try {
			/*
			 * DefaultAV.
			 */
			moldObject = get_toJSON_MoldObject();
			moldObject.b2 = null;
			assert_toJSON_exception( json_om, moldObject );
		
			moldObject = get_toJSON_MoldObject();
			moldObject.i2 = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.l2 = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.f2 = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.d2 = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.bi = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.bd = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.s = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.b = null;
			assert_toJSON_exception( json_om, moldObject );
			
			moldObject = get_toJSON_MoldObject();
			moldObject.obj = null;
			assert_toJSON_exception( json_om, moldObject );
			/*
			 * WithAV.
			 */
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.b2 = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
		
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.i2 = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.l2 = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.f2 = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.d2 = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.bi = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.bd = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.s = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.b = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
			
			moldObjectWithAV = get_toJSON_MoldObjectWithAV();
			moldObjectWithAV.obj = null;
			assert_toJSONWithAV_exception( json_om, moldObjectWithAV );
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public void assert_toJSON_exception(JSONObjectMapper json_om, TestJSONMapObject moldObject) throws IllegalArgumentException, IllegalAccessException {
		try {
			json_om.toJSON( moldObject );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			// debug
			//e.printStackTrace();
		}
	}

	public TestJSONMapObject get_toJSON_MoldObject() {
		TestJSONMapObject obj = new TestJSONMapObject();
		obj.b1 = false;
		obj.b2 = true;
		obj.i1 = 4213;
		obj.i2 = 4321;
		obj.l1 = 12345678901234L * 2L;
		obj.l2 = 43210987654321L * 2L;
		obj.f1 = 1.0F / 5.0F;
		obj.f2 = 5.0F;
		obj.d1 = 1.0 / 5.0;
		obj.d2 = 5.0;
		obj.bi = new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) );
		obj.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) );
		obj.s = "JSON";
		obj.b = "BYTES".getBytes();
		TestJSONMapObjectNullable obj2 = new TestJSONMapObjectNullable();
		obj.obj = obj2;
		obj2.b1 = true;
		obj2.b2 = false;
		obj2.i1 = 42;
		obj2.i2 = 1234;
		obj2.l1 = 12345678901234L;
		obj2.l2 = 43210987654321L;
		obj2.f1 = 1.0F / 3.0F;
		obj2.f2 = 3.0F;
		obj2.d1 = 1.0 / 3.0;
		obj2.d2 = 3.0;
		obj2.bi = new BigInteger( "123456789012345678901234567890123456789012" );
		obj2.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" );
		obj2.s = "json";
		obj2.b = "bytes".getBytes();
		return obj;
	}

	public void assert_toJSONWithAV_exception(JSONObjectMapper json_om, TestJSONMapObjectWithAV moldObject) throws IllegalArgumentException, IllegalAccessException {
		try {
			json_om.toJSON( moldObject );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			// debug
			//e.printStackTrace();
		}
	}

	public TestJSONMapObjectWithAV get_toJSON_MoldObjectWithAV() {
		TestJSONMapObjectWithAV obj = new TestJSONMapObjectWithAV();
		obj.b1 = false;
		obj.b2 = true;
		obj.i1 = 4213;
		obj.i2 = 4321;
		obj.l1 = 12345678901234L * 2L;
		obj.l2 = 43210987654321L * 2L;
		obj.f1 = 1.0F / 5.0F;
		obj.f2 = 5.0F;
		obj.d1 = 1.0 / 5.0;
		obj.d2 = 5.0;
		obj.bi = new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) );
		obj.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) );
		obj.s = "JSON";
		obj.b = "BYTES".getBytes();
		TestJSONMapObjectNullableWithAV obj2 = new TestJSONMapObjectNullableWithAV();
		obj.obj = obj2;
		obj2.b1 = true;
		obj2.b2 = false;
		obj2.i1 = 42;
		obj2.i2 = 1234;
		obj2.l1 = 12345678901234L;
		obj2.l2 = 43210987654321L;
		obj2.f1 = 1.0F / 3.0F;
		obj2.f2 = 3.0F;
		obj2.d1 = 1.0 / 3.0;
		obj2.d2 = 3.0;
		obj2.bi = new BigInteger( "123456789012345678901234567890123456789012" );
		obj2.bd = new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" );
		obj2.s = "json";
		obj2.b = "bytes".getBytes();
		return obj;
	}

}
