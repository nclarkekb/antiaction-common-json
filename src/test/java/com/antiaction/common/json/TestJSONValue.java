/*
 * Created on 07/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.json;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONValue {

	@Test
	public void test_jsonvalue() {
		JSONValue value;

		value = new JSONValue() {
		};
		Assert.assertEquals( 0, value.type );

		try {
			value.encode( null );
			Assert.fail( "Exception expected !" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			value.encode( null, null, null );
			Assert.fail( "Exception expected !" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (UnsupportedOperationException e) {
		}

		value = JSONNull.Null;
		Assert.assertEquals( JSONConstants.VT_NULL, value.type );

		value = JSONBoolean.False;
		Assert.assertEquals( JSONConstants.VT_BOOLEAN, value.type );

		value = JSONBoolean.True;
		Assert.assertEquals( JSONConstants.VT_BOOLEAN, value.type );

		value = new JSONString( "name" );
		Assert.assertEquals( JSONConstants.VT_STRING, value.type );

		value = JSONNumber.Integer( 42 );
		Assert.assertEquals( JSONConstants.VT_NUMBER, value.type );

		value = new JSONArray();
		Assert.assertEquals( JSONConstants.VT_ARRAY, value.type );

		value = new JSONObject();
		Assert.assertEquals( JSONConstants.VT_OBJECT, value.type );
	}

}
