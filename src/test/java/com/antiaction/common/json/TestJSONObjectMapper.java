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

			json_om.register( SFFResult.class );
			json_om.register( SFFResult.class );

			text = "{\"success\":1,\"username\":{\"frequency\":0,\"appears\":0},\"email\":{\"frequency\":0,\"appears\":0},\"ip\":{\"frequency\":0,\"appears\":0}}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			text = "{\"success\":1,\"username\":{\"frequency\":0,\"appears\":0},\"email\":{\"lastseen\":\"2012-11-15 20:30:38\",\"frequency\":166,\"appears\":1,\"confidence\":99.1},\"ip\":{\"lastseen\":\"2012-11-15 20:30:38\",\"frequency\":54,\"appears\":1,\"confidence\":97.3}}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			try {
				json_om.register( boolean.class );
				Assert.fail( "Exception expected!" );
			}
			catch (IllegalArgumentException e) {
			}
			try {
				json_om.register( byte[].class );
				Assert.fail( "Exception expected!" );
			}
			catch (IllegalArgumentException e) {
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

	@JSON(ignore={"$jacocoData"})
	public static class SFFResult {

		public boolean success;

		@JSONNullable
		public String error;

		@JSONNullable
		public Result username;

		@JSONNullable
		public Result email;

		@JSONNullable
		public Result ip;

	}

	@JSON(ignore={"$jacocoData"})
	public static class Result {

		@JSONNullable
		public String lastseen;

		public int frequency;

		public int appears;

		@JSONNullable
		public Double confidence;

	}

}
