/*
 * Created on 15/12/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.TestClassTypeModifiers.TestAbstractMemberClass;
import com.antiaction.common.json.TestClassTypeModifiers.TestEnum;
import com.antiaction.common.json.TestClassTypeModifiers.TestInterface;
import com.antiaction.common.json.TestClassTypeModifiers.TestMemberClass;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_InvalidField {

	@Test
	public void test_jsonobjectmapper_invalidfields() {
		JSONObjectMappings json_om = new JSONObjectMappings();
		try {
			json_om.register( InvalidField1.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( InvalidField2.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( InvalidField3.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( InvalidField4.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
		}
		try {
			json_om.register( ValidField1.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class InvalidField1 {
		TestEnum enumse;
	}

	public static class InvalidField2 {
		TestInterface interf;
	}

	public static class InvalidField3 {
		TestAbstractMemberClass tabstract;
	}

	public static class InvalidField4 {
		TestMemberClass memass;
	}

	public static class ValidField1 {
		TestClassTypeModifiers normass;
	}

}
