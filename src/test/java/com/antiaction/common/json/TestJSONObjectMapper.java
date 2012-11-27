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

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
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
			Assert.assertEquals( 99.1, result.email.confidence );
			Assert.assertNotNull( result.ip );
			Assert.assertEquals( 1, result.ip.appears );
			Assert.assertEquals( 54, result.ip.frequency );
			Assert.assertEquals( "2012-11-15 20:30:38", result.ip.lastseen );
			Assert.assertEquals( 97.3, result.ip.confidence );
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

	@Test
	public void test_jsonobjectmapper_classtypemask() {
		Assert.assertEquals( JSONObjectMapper.CT_ANNOTATION | JSONObjectMapper.CT_INTERFACE | JSONObjectMapper.CT_ABSTRACT | JSONObjectMapper.CT_PUBLIC, JSONObjectMapper.classTypeModifiersMask( JSON.class ) );

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
			}
		};
		Assert.assertEquals( JSONObjectMapper.CT_ANONYMOUSCLASS, JSONObjectMapper.classTypeModifiersMask( runnable.getClass() ) );

		Assert.assertEquals( JSONObjectMapper.CT_ARRAY | JSONObjectMapper.CT_ABSTRACT | JSONObjectMapper.CT_FINAL | JSONObjectMapper.CT_PUBLIC, JSONObjectMapper.classTypeModifiersMask( byte[].class ) );

		Assert.assertEquals( JSONObjectMapper.CT_ENUM | JSONObjectMapper.CT_MEMBERCLASS | JSONObjectMapper.CT_FINAL | JSONObjectMapper.CT_PUBLIC | JSONObjectMapper.CT_STATIC, JSONObjectMapper.classTypeModifiersMask( TestEnum.ENUM.getClass() ) );

		class runnable2 implements Runnable {
			@Override
			public void run() {
			}
		};
		Assert.assertEquals( JSONObjectMapper.CT_LOCALCLASS, JSONObjectMapper.classTypeModifiersMask( runnable2.class ) );

		Assert.assertEquals( JSONObjectMapper.CT_INTERFACE | JSONObjectMapper.CT_MEMBERCLASS | JSONObjectMapper.CT_ABSTRACT | JSONObjectMapper.CT_PUBLIC | JSONObjectMapper.CT_STATIC, JSONObjectMapper.classTypeModifiersMask( TestInterface.class ) );

		Assert.assertEquals( JSONObjectMapper.CT_PRIMITIVE | JSONObjectMapper.CT_ABSTRACT | JSONObjectMapper.CT_FINAL | JSONObjectMapper.CT_PUBLIC, JSONObjectMapper.classTypeModifiersMask( boolean.class ) );

		Assert.assertEquals( JSONObjectMapper.CT_MEMBERCLASS | JSONObjectMapper.CT_ABSTRACT | JSONObjectMapper.CT_PUBLIC, JSONObjectMapper.classTypeModifiersMask( TestAbstractClass.class ) );

		Assert.assertEquals( JSONObjectMapper.CT_MEMBERCLASS | JSONObjectMapper.CT_PUBLIC | JSONObjectMapper.CT_STATIC, JSONObjectMapper.classTypeModifiersMask( TestStaticClass.class ) );

		Assert.assertEquals( JSONObjectMapper.CT_CLASS | JSONObjectMapper.CT_PUBLIC, JSONObjectMapper.classTypeModifiersMask( this.getClass() ) );
	}

	public enum TestEnum {
		ENUM();
	}

	public interface TestInterface {
	}

	public abstract class TestAbstractClass {
	}

	public static class TestStaticClass {
	}

}
