/*
 * Created on 25/10/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMappings_Nullable {

	@Test
	public void test_jsonobjectmapper_register_nullable_invalid() {
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
	}

	public static class TestNullableField1Class {
		@JSONNullable
		boolean b1;
	}

	public static class TestNullableField2Class {
		@JSONNullable
		int i1;
	}

	public static class TestNullableField3Class {
		@JSONNullable
		long l1;
	}

	public static class TestNullableField4Class {
		@JSONNullable
		float f1;
	}

	public static class TestNullableField5Class {
		@JSONNullable
		double d1;
	}

}
