/*
 * JSON library.
 * Copyright 2012-2013 Antiaction (http://antiaction.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antiaction.common.json.representation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONEncoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;
import com.antiaction.common.json.annotation.JSONName;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 13/01/2013
 */
@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Name {

	@Test
	public void test_jsonobjectmapper_toobject() {
		JSONCollection json_struct;
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
			JSONTextMarshaller json_textMarshaller = new JSONTextMarshaller();
			JSONTextUnmarshaller json_textUnmarshaller = new JSONTextUnmarshaller();

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();

			json_objectmappings.register( TestJsonName.class );

			TestJsonName obj = new TestJsonName();
			obj.i_value = 42;
			obj.s_value = "meaning of life";
			obj.i = 1234;
			obj.s = "one two three four";

			json_struct = json_objectmappings.getStructureMarshaller().toJSONStructure( obj );
			json_textMarshaller.toJSONText( json_struct, json_encoder, true, out );

			// debug
			//System.out.println( out.toString( "ISO-8859-1" ) );

			ByteArrayInputStream in = new ByteArrayInputStream( out.toByteArray() );

			json_struct = json_textUnmarshaller.toJSONStructure( in, json_decoder );
			json_object = json_struct.getObject();
			Assert.assertEquals( JSONNumber.Integer( 42 ), json_object.get( "integer" ) );
			Assert.assertEquals( JSONString.String( "meaning of life" ), json_object.get( "string" ) );
			Assert.assertEquals( JSONNumber.Integer( 1234 ), json_object.get( "s" ) );
			Assert.assertEquals( JSONString.String( "one two three four" ), json_object.get( "i" ) );

			TestJsonName result = json_objectmappings.getStructureUnmarshaller().toObject( json_struct, TestJsonName.class );

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
