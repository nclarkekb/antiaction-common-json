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
public class TestJSONBoolean {

	@Test
	public void test_jsonboolean() {
		try {
			JSONBoolean json_boolean;

			json_boolean = JSONBoolean.False;
			Assert.assertNotNull( json_boolean );
			Assert.assertEquals( JSONConstants.VT_BOOLEAN, json_boolean.type );
			Assert.assertFalse( json_boolean.b );
			Assert.assertFalse( json_boolean.getBoolean() );

			Assert.assertEquals( "false", json_boolean.toString() );

			json_boolean = JSONBoolean.True;
			Assert.assertNotNull( json_boolean );
			Assert.assertEquals( JSONConstants.VT_BOOLEAN, json_boolean.type );
			Assert.assertTrue( json_boolean.b );
			Assert.assertTrue( json_boolean.getBoolean() );

			Assert.assertEquals( "true", json_boolean.toString() );

			json_boolean = JSONBoolean.Boolean( false );
			Assert.assertNotNull( json_boolean );
			Assert.assertEquals( JSONConstants.VT_BOOLEAN, json_boolean.type );
			Assert.assertFalse( json_boolean.b );
			Assert.assertFalse( json_boolean.getBoolean() );

			Assert.assertEquals( "false", json_boolean.toString() );

			json_boolean = JSONBoolean. Boolean( true );
			Assert.assertNotNull( json_boolean );
			Assert.assertEquals( JSONConstants.VT_BOOLEAN, json_boolean.type );
			Assert.assertTrue( json_boolean.b );
			Assert.assertTrue( json_boolean.getBoolean() );

			Assert.assertEquals( "true", json_boolean.toString() );

			/*
			 *
			 */

			JSONBoolean False = JSONBoolean.False;
			JSONBoolean True = JSONBoolean.True;

			Charset charset = Charset.forName("UTF-8");
	        JSONEncoder json_encoder = new JSONEncoderCharset( charset );

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JSONText json = new JSONText();

			JSONArray json_array = new JSONArray();
			json_array.add( False );
			json_array.add( True );

			json.encodeJSONtext( json_array, json_encoder, out );

			// debug
			//System.out.println( new String( out.toByteArray() )  );

			ByteArrayInputStream in = new ByteArrayInputStream( out.toByteArray() );
	        JSONDecoder json_decoder = new JSONDecoderCharset( charset );

			JSONStructure json_structure = json.decodeJSONtext( in, json_decoder );

			Assert.assertNotNull( json_structure );
			Assert.assertEquals( JSONConstants.VT_ARRAY, json_structure.type );
			json_array = (JSONArray)json_structure;
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( False, json_array.values.get( 0 ) );
			Assert.assertEquals( True, json_array.values.get( 1 ) );

			/*
			 *
			 */

			Assert.assertFalse( False.equals( null ) );
			Assert.assertFalse( True.equals( null ) );
			Assert.assertFalse( False.equals( "false" ) );
			Assert.assertFalse( True.equals( "true" ) );
			Assert.assertFalse( False.equals( True ) );
			Assert.assertFalse( True.equals( False ) );
			Assert.assertFalse( False.equals( new JSONBoolean( true ) ) );
			Assert.assertFalse( True.equals( new JSONBoolean( false ) ) );

			Assert.assertTrue( False.equals( new JSONBoolean( false ) ) );
			Assert.assertTrue( True.equals( new JSONBoolean( true ) ) );

			Assert.assertEquals( False.hashCode(), new JSONBoolean( false ).hashCode() );
			Assert.assertEquals( True.hashCode(), new JSONBoolean( true ).hashCode() );

		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

}
