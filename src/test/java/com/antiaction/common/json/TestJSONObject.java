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
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONObject {

	@Test
	public void test_jsonobject() {
		JSONObject json_object = new JSONObject();
		json_object.put( new JSONString( "name" ), JSONNull.Null );

		try {
			json_object.add( new JSONString( "name" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_object.get( 42 );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}


	@Test
	public void test_jsonobject_large() {
		try {
			SecureRandom random = new SecureRandom();
			byte[] bytes;

			Map<String, byte[]> inObjects = new HashMap<String, byte[]>();
			for ( int i=0; i<8; ++i ) {
				bytes = new byte[ 16384 ];
				random.nextBytes( bytes );
				inObjects.put( UUID.randomUUID().toString(), bytes );
			}

			JSONStructure json_struct = new JSONObject();

			Iterator<Entry<String, byte[]>> objectIter = inObjects.entrySet().iterator();
			Entry<String, byte[]> objectEntry;
			while ( objectIter.hasNext() ) {
				objectEntry = objectIter.next();
				json_struct.put( objectEntry.getKey(), JSONString.String( objectEntry.getValue() ) );
			}

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
			int encoding;
			JSONDecoder json_decoder;
			JSONText json_text = new JSONText();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PushbackInputStream in;

			out.reset();
			json_text.encodeJSONtext( json_struct, json_encoder, out );

			in = new PushbackInputStream( new ByteArrayInputStream( out.toByteArray() ), 4 );
			encoding = JSONEncoding.encoding( in );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = json_text.decodeJSONtext( in, json_decoder );
			in.close();

			objectIter = inObjects.entrySet().iterator();
			while ( objectIter.hasNext() ) {
				objectEntry = objectIter.next();
				Assert.assertArrayEquals( inObjects.get( objectEntry.getKey() ), json_struct.get( objectEntry.getKey() ).getBytes() );
				// debug
				//System.out.println( json_struct.get( objectEntry.getKey() ).getBytes().length );
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@Test
	public void test_jsonobject_supported_unsupported() {
		JSONStructure json_struct = new JSONObject();
		JSONObject json_object = json_struct.getObject();
		Assert.assertEquals( json_struct, json_object );

		try {
			json_struct.getArray();
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
