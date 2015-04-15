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

package com.antiaction.common.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONNullValues;
import com.antiaction.common.json.annotation.JSONNullable;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 25/10/2013
 * FIXME Support byte/char/Byte/Character array tests
 */
@RunWith(JUnit4.class)
public class TestJSONObjectMappings_Nullable {

	@Test
	public void test_jsonobjectmappings_register_nullable_invalid() {
		JSONObjectMappings json_om = new JSONObjectMappings();
		try {
			json_om.register( TestNullableField1Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField2Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField3Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField4Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField5Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField6Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestNullableField7Class.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestJSONMapObjectArraysNullValues_Invalid1.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestJSONMapObjectArraysNullValues_Invalid2.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestJSONMapObjectArraysNullValues_Invalid3.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestJSONMapObjectArraysNullValues_Invalid4.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( TestJSONMapObjectArraysNullValues_Invalid5.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
	}

	public static class TestNullableField1Class {
		@JSONNullable
		boolean b1;
	}

	public static class TestNullableField2Class {
		@JSONNullable
		byte bb1;
	}

	public static class TestNullableField3Class {
		@JSONNullable
		char c1;
	}

	public static class TestNullableField4Class {
		@JSONNullable
		int i1;
	}

	public static class TestNullableField5Class {
		@JSONNullable
		long l1;
	}

	public static class TestNullableField6Class {
		@JSONNullable
		float f1;
	}

	public static class TestNullableField7Class {
		@JSONNullable
		double d1;
	}

	public static class TestJSONMapObjectArraysNullValues_Invalid1 {
		@JSONNullable
		@JSONNullValues(true)
		public boolean[] b1_arr;
	}

	public static class TestJSONMapObjectArraysNullValues_Invalid2 {
		@JSONNullable
		@JSONNullValues(true)
		public int[] i1_arr;
	}

	public static class TestJSONMapObjectArraysNullValues_Invalid3 {
		@JSONNullable
		@JSONNullValues(true)
		public long[] l1_arr;
	}

	public static class TestJSONMapObjectArraysNullValues_Invalid4 {
		@JSONNullable
		@JSONNullValues(true)
		public float[] f1_arr;
	}

	public static class TestJSONMapObjectArraysNullValues_Invalid5 {
		@JSONNullable
		@JSONNullValues(true)
		public double[] d1_arr;
	}

}
