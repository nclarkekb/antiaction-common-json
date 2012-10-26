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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONNull {

	@Test
	public void test_jsonnull() {
		try {
			JSONNull json_null;

			json_null = JSONNull.Null;
			Assert.assertNotNull( json_null );
			Assert.assertEquals( JSONConstants.VT_NULL, json_null.type );

			Assert.assertEquals( "null", json_null.toString() );

			json_null = new JSONNull();
			Assert.assertNotNull( json_null );
			Assert.assertEquals( JSONConstants.VT_NULL, json_null.type );

			Assert.assertEquals( "null", json_null.toString() );

			/*
			 *
			 */

			Charset charset = Charset.forName("UTF-8");
	        JSONEncoder json_encoder = new JSONEncoderCharset( charset );

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JSONText json = new JSONText();

			JSONArray json_array = new JSONArray();
			json_array.add( json_null );

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
			Assert.assertEquals( json_null, json_array.values.get( 0 ) );

			/*
			 *
			 */

			Assert.assertFalse( json_null.equals( null ) );
			Assert.assertFalse( json_null.equals( "null" ) );
			Assert.assertTrue( json_null.equals( json_null ) );

			JSONNull json_null2 = new JSONNull();
			Assert.assertTrue( json_null.equals( json_null2 ) );
			Assert.assertTrue( json_null2.equals( json_null ) );
			Assert.assertTrue( json_null2.equals( json_null2 ) );
			Assert.assertEquals( json_null.hashCode(), json_null2.hashCode() );

			/*
			 *
			 */

		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

}