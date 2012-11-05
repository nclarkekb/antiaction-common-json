/*
 * Created on 02/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSON {

	@Test
	public void test_json() {
		JSONStructure json_struct;
		JSONObject json_object;
		JSONObject json_object2;
		JSONArray json_array;

		try {
			String text;
			byte[] bytes;
			PushbackInputStream pbin;
			int encoding;

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONDecoder json_decoder;
			JSONText json = new JSONText();

			/*
			 * stopforumspam.com example.
			 */

			text = "{\"success\":true,\"email\":{\"lastseen\":\"2009-06-25 00:24:29\",\"frequency\":2,\"appears\":true},\"username\":{\"frequency\":0,\"appears\":false}}";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			json_object = (JSONObject)json_struct;
			Assert.assertEquals( JSONBoolean.True, json_object.get( "success" ) );
			json_object2 = (JSONObject)json_object.get( "email" );
			Assert.assertEquals( JSONString.String( "2009-06-25 00:24:29" ), json_object2.get( "lastseen" ) );
			Assert.assertEquals( JSONNumber.Integer( 2 ), json_object2.get( "frequency" ) );
			Assert.assertEquals( JSONBoolean.True, json_object2.get( "appears" ) );
			json_object2 = (JSONObject)json_object.get( "username" );
			Assert.assertEquals( JSONNumber.Integer( 0 ), json_object2.get( "frequency" ) );
			Assert.assertEquals( JSONBoolean.False, json_object2.get( "appears" ) );

			/*
			 * SOLR example.
			 */

			text = ""
					+ "[\n"
					+ "  {\"id\" : \"TestDoc1\", \"title\" : \"test1\"},\n"
					+ "  {\"id\" : \"TestDoc2\", \"title\" : \"another test\"}\n"
					+ "]\n";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );

			json_array = (JSONArray)json_struct;
			json_object = (JSONObject)json_array.get( 0 );
			Assert.assertEquals( JSONString.String( "TestDoc1" ), json_object.get( "id") );
			Assert.assertEquals( JSONString.String( "test1" ), json_object.get( "title" ) ); 
			json_object = (JSONObject)json_array.get( 1 );
			Assert.assertEquals( JSONString.String( "TestDoc2" ), json_object.get( "id" )); 
			Assert.assertEquals( JSONString.String( "another test"), json_object.get( "title" ) ); 

			/*
			 * SOLR example.
			 */

			text = ""
					+ "{\n"
					+ "  \"add\": {\"doc\": {\"id\" : \"TestDoc1\", \"title\" : \"test1\"} },\n"
					+ "  \"add\": {\"doc\": {\"id\" : \"TestDoc2\", \"title\" : \"another test\"} }\n"
					+ "}\n";
			bytes = text.getBytes( "UTF-8" );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bytes ), 4 );
			encoding = JSONEncoding.encoding( pbin );
			Assert.assertEquals( JSONEncoding.E_UTF8, encoding );
			json_decoder = json_encoding.getJSONDecoder( encoding );
			Assert.assertNotNull( json_decoder );
			json_struct = json.decodeJSONtext( pbin, json_decoder );
			Assert.assertNotNull( json_struct );
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

	}

}
