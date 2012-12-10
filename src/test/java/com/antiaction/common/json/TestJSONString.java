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
import java.io.PushbackInputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONString {

	@Test
	public void test_jsonstring() {
		StringBuilder sb = new StringBuilder();
		for ( int i=0; i<256; ++i ) {
			sb.append( (char)i );
		}
		test_jsonstring_params( sb );
		sb.setLength( 0 );
		for ( int i=0; i<32; ++i ) {
			sb.append( (char)i );
		}
		test_jsonstring_params( sb );
	}

	public void test_jsonstring_params(StringBuilder sb) {
		String eightbit;
		JSONString json_string;
		JSONString json_string2;

		try {
			eightbit = sb.toString();
			json_string = JSONString.String( sb.toString() );
			json_string2 = JSONString.String( sb.toString() );

			Assert.assertEquals( eightbit, json_string.getString() );
			Assert.assertEquals( eightbit, json_string2.getString() );

			Assert.assertEquals( '"' + eightbit + '"', json_string.toString() );
			Assert.assertEquals( '"' + eightbit + '"', json_string2.toString() );

			/*
			 *
			 */

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JSONText json = new JSONText();

			Charset charset = Charset.forName("UTF-8");
			JSONEncoder json_encoder = new JSONEncoderCharset( charset );

			JSONArray json_array = new JSONArray();
			json_array.add( json_string );

			json.encodeJSONtext( json_array, json_encoder, false, out );

			// debug
			//System.out.println( new String( out.toByteArray() )  );

			ByteArrayInputStream in = new ByteArrayInputStream( out.toByteArray() );
			JSONDecoder json_decoder = new JSONDecoderCharset( charset );

			JSONStructure json_structure = json.decodeJSONtext( in, json_decoder );

			Assert.assertNotNull( json_structure );
			Assert.assertEquals( JSONConstants.VT_ARRAY, json_structure.type );
			json_array = (JSONArray)json_structure;
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( json_string, json_array.values.get( 0 ) );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

	@Test
	public void test_jsonstring_bytearray() {
		Charset charset;

		charset = Charset.forName("UTF-8");
		test_jsonstring_bytearray_params( charset, JSONEncoding.E_UTF8 );

		charset = Charset.forName("UTF-16");
		test_jsonstring_bytearray_params( charset, JSONEncoding.E_UTF16BE );

		charset = Charset.forName("UTF-16BE");
		test_jsonstring_bytearray_params( charset, JSONEncoding.E_UTF16BE );

		charset = Charset.forName("UTF-16LE");
		test_jsonstring_bytearray_params( charset, JSONEncoding.E_UTF16LE );
	}

	public void test_jsonstring_bytearray_params(Charset charset, int expected_encoding) {
		JSONStructure json_struct;
		byte[] bytes;

		try {
			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONEncoder json_encoder = new JSONEncoderCharset( charset );
			int encoding;
			JSONDecoder json_decoder;
			JSONText json_text = new JSONText();
			PushbackInputStream in;

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			StringBuilder sb = new StringBuilder();

			/*
			 * Valid byte array.
			 */

			sb.setLength( 0 );
			out.reset();
			for ( int i=0; i<256; ++i ) {
				sb.append( (char)i );
				out.write( i );
			}
			bytes = out.toByteArray();

			json_struct = new JSONArray();
			json_struct.add( JSONString.String( sb.toString() ) );

			out.reset();
			json_text.encodeJSONtext( json_struct, json_encoder, false, out );

			in = new PushbackInputStream( new ByteArrayInputStream( out.toByteArray() ), 4 );
			encoding = JSONEncoding.encoding( in );
			Assert.assertEquals( expected_encoding, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = json_text.decodeJSONtext( in, json_decoder );
			in.close();

			Assert.assertArrayEquals( bytes, json_struct.get( 0 ).getBytes() );
			Assert.assertArrayEquals( bytes, json_struct.get( 0 ).getBytes() );

			/*
			 * Invalid byte array.
			 */

			sb.setLength( 0 );
			for ( int i=0; i<257; ++i ) {
				sb.append( (char)i );
			}

			json_struct = new JSONArray();
			json_struct.add( JSONString.String( sb.toString() ) );

			out.reset();
			json_text.encodeJSONtext( json_struct, json_encoder, false, out );

			in = new PushbackInputStream( new ByteArrayInputStream( out.toByteArray() ), 4 );
			encoding = JSONEncoding.encoding( in );
			Assert.assertEquals( expected_encoding, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = json_text.decodeJSONtext( in, json_decoder );
			in.close();

			try {
				json_struct.get( 0 ).getBytes();
				Assert.fail( "Exception expected!" );
			}
			catch (NumberFormatException e) {
			}
			try {
				json_struct.get( 0 ).getBytes();
				Assert.fail( "Exception expected!" );
			}
			catch (NumberFormatException e) {
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@Test
	public void test_jsonstring_unsupported() {
		JSONString json_string = JSONString.String( "JSON" );
		try {
			json_string.getArray();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getObject();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getBoolean();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getLong();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getFloat();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getDouble();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getBigInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_string.getBigDecimal();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void test_jsonnumber_equals_hashcode() {
		StringBuilder sb = new StringBuilder();
		for ( int i=0; i<256; ++i ) {
			sb.append( (char)i );
		}
		String eightbit = sb.toString();
		JSONString json_string = JSONString.String( sb.toString() );
		JSONString json_string2 = JSONString.String( sb.toString() );

		Assert.assertTrue( json_string.equals( json_string2 ) );
		Assert.assertTrue( json_string2.equals( json_string ) );
		Assert.assertEquals( json_string.hashCode(), json_string2.hashCode() );
		Assert.assertEquals( json_string2.hashCode(), json_string.hashCode() );

		Assert.assertEquals( eightbit, json_string.getString() );
		Assert.assertEquals( eightbit, json_string2.getString() );

		Assert.assertFalse( json_string.equals( null ) );
		Assert.assertFalse( json_string2.equals( null ) );
		Assert.assertFalse( json_string.equals( "string" ) );
		Assert.assertFalse( json_string2.equals( "string" ) );

		json_string2 = JSONString.String( "test" );
		Assert.assertFalse( json_string.equals( json_string2 ) );
		Assert.assertFalse( json_string2.equals( json_string ) );

		Assert.assertEquals( eightbit, json_string.getString() );
		Assert.assertEquals( "test", json_string2.getString() );

		json_string = JSONString.String( "test" );
		Assert.assertTrue( json_string.equals( json_string2 ) );
		Assert.assertTrue( json_string2.equals( json_string ) );
		Assert.assertEquals( json_string.hashCode(), json_string2.hashCode() );
		Assert.assertEquals( json_string2.hashCode(), json_string.hashCode() );

		Assert.assertEquals( "test", json_string.getString() );
		Assert.assertEquals( "test", json_string2.getString() );

		json_string2 = JSONString.String( "Test" );
		Assert.assertFalse( json_string.equals( json_string2 ) );
		Assert.assertFalse( json_string2.equals( json_string ) );

		Assert.assertEquals( "test", json_string.getString() );
		Assert.assertEquals( "Test", json_string2.getString() );
	}

}
