/*
 * Created on 19/10/2012
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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONArray {

	@Test
	public void test_jsonarray() {
		JSONArray json_array = new JSONArray();
		json_array.add( new JSONString( "name" ) );
		json_array.add( JSONNull.Null );

		try {
			json_array.put( "name", JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.put( new JSONString( "name" ), JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( "42" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( JSONString.String( "42" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void test_jsonarray_large() {
		Charset charset;

		charset = Charset.forName("UTF-8");
		test_jsonarray_large_params( charset, JSONEncoding.E_UTF8 );

		charset = Charset.forName("UTF-16");
		test_jsonarray_large_params( charset, JSONEncoding.E_UTF16BE );

		charset = Charset.forName("UTF-16BE");
		test_jsonarray_large_params( charset, JSONEncoding.E_UTF16BE );

		charset = Charset.forName("UTF-16LE");
		test_jsonarray_large_params( charset, JSONEncoding.E_UTF16LE );
	}

	public void test_jsonarray_large_params(Charset charset, int expected_encoding) {
		try {
			SecureRandom random = new SecureRandom();
			byte[] bytes;

			List<byte[]> inArrays = new ArrayList<byte[]>();
			for ( int i=0; i<8; ++i ) {
				bytes = new byte[ 16384 ];
				random.nextBytes( bytes );
				inArrays.add( bytes );
			}

			JSONStructure json_struct = new JSONArray();
			for ( int i=0; i<inArrays.size(); ++i ) {
				json_struct.add( JSONString.String( inArrays.get( i ) ) );
			}

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONEncoder json_encoder = new JSONEncoderCharset( charset );
			int encoding;
			JSONDecoder json_decoder;
			JSONText json_text = new JSONText();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PushbackInputStream in;

			// debug
			//System.out.println( 16384 * 8 );

			out.reset();
			json_text.encodeJSONtext( json_struct, json_encoder, false, out );

			// debug
			//System.out.println( new String( out.toByteArray() ) );
			//System.out.println( out.size() );

			in = new PushbackInputStream( new ByteArrayInputStream( out.toByteArray() ), 4 );
			encoding = JSONEncoding.encoding( in );
			Assert.assertEquals( expected_encoding, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = json_text.decodeJSONtext( in, json_decoder );
			in.close();

			for ( int i=0; i<inArrays.size(); ++i ) {
				Assert.assertArrayEquals( inArrays.get( i ), json_struct.get( i ).getBytes() );
				// debug
				//System.out.println( json_struct.get( i ).getBytes().length );
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@Test
	public void test_jsonarray_supported_unsupporteed() {
		JSONStructure json_struct = new JSONArray();
		JSONArray json_array = json_struct.getArray();
		Assert.assertEquals( json_struct, json_array );

		try {
			json_struct.getObject();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBoolean();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getString();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBytes();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getLong();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getFloat();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getDouble();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBigInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBigDecimal();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

}
