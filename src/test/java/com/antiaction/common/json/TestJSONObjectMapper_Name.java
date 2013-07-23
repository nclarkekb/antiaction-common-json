/*
 * Created on 13/01/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONName;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Name {

	@Test
	public void test_jsonobjectmapper_toobject() {
		JSONStructure json_struct;
		//JSONArray json_array;
		JSONObject json_object;
		//JSONObject json_object2;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			/*
			String text;
			byte[] bytes;
			PushbackInputStream pbin;
			int encoding;
			*/

			JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
			JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
			JSONDecoder json_decoder = json_encoding.getJSONDecoder( JSONEncoding.E_UTF8 );
			JSONText json = new JSONText();

			JSONObjectMapper json_om = new JSONObjectMapper();

			/*
			 * Missing zero argument constructor.
			 */

			try {
				json_om.register( TestZeroConstructor.class );
			}
			catch (JSONException e) {
				// debug
				//System.out.println( e.getMessage() );
				//System.out.println( e.getMessage().indexOf( " does not have a zero argument contructor!" ) );
                Assert.assertThat( e.getMessage().indexOf( " does not have a zero argument constructor!" ), is( not( equalTo( -1 ) ) ) );
			}

			json_om.register( TestJsonName.class );

			TestJsonName obj = new TestJsonName();
			obj.i_value = 42;
			obj.s_value = "meaning of life";
			obj.i = 1234;
			obj.s = "one two three four";

			json_struct = json_om.toJSON( obj );
			json.encodeJSONtext( json_struct, json_encoder, true, out );

			// debug
			//System.out.println( out.toString( "ISO-8859-1" ) );

			ByteArrayInputStream in = new ByteArrayInputStream( out.toByteArray() );

			json_struct = json.decodeJSONtext( in, json_decoder );
			json_object = json_struct.getObject();
			Assert.assertEquals( JSONNumber.Integer( 42 ), json_object.get( "integer" ) );
			Assert.assertEquals( JSONString.String( "meaning of life" ), json_object.get( "string" ) );
			Assert.assertEquals( JSONNumber.Integer( 1234 ), json_object.get( "s" ) );
			Assert.assertEquals( JSONString.String( "one two three four" ), json_object.get( "i" ) );

			TestJsonName result = json_om.toObject( json_struct, TestJsonName.class );

			Assert.assertEquals( 42, result.i_value );
			Assert.assertEquals( "meaning of life", result.s_value );
			Assert.assertEquals( 1234, result.i );
			Assert.assertEquals( "one two three four", result.s );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
	}

	public static class TestZeroConstructor {

		public String str;

		public TestZeroConstructor(String str) {
			this.str = str;
		}

	}

	public static class TestJsonName {

		@JSONName("integer")
		public int i_value;

		@JSONName("string")
		public String s_value;

		@JSONName("s")
		public int i;

		@JSONName("i")
		public String s;

	}

}
