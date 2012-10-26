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
import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONString {

	@Test
	public void test_jsonstring() {
		try {
			String eightbit;
			JSONString json_string;
			JSONString json_string2;

			StringBuilder sb = new StringBuilder();
			for ( int i=0; i<256; ++i ) {
				sb.append( (char)i );
			}
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

			json.encodeJSONtext( json_array, json_encoder, out );

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

			/*
			 *
			 */

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
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

}