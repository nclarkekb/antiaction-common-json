/*
 * Created on 19/10/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONArray {

	@Test
	public void test_jsonarray() {
		JSONArray json_array = new JSONArray();
		json_array.add( new JSONString( "name" ) );
		json_array.add( JSONNull.Null );

		try {
			json_array.put( "name", JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.put( new JSONString( "name" ), JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( "42" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( JSONString.String( "42" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

}
