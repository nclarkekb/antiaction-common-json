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
public class TestJSONObject {

	@Test
	public void test_jsonobject() {
		JSONObject json_object = new JSONObject();
		json_object.put( new JSONString( "name" ), JSONNull.Null );

		try {
			json_object.add( new JSONString( "name" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_object.get( 42 );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

}
