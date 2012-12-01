/*
 * Created on 01/12/2012
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
public class TestJSONException {

	@Test
	public void test_jsonexception() {
		JSONException json_exception;

		Exception e1 = new IllegalArgumentException();
		Exception e2 = new UnsupportedOperationException();

		json_exception = new JSONException();
		Assert.assertNull( json_exception.getMessage() );
		Assert.assertNull( json_exception.getCause() );

		json_exception.initCause( e2 );
		Assert.assertEquals( e2, json_exception.getCause() );

		json_exception = new JSONException( "the message" );
		Assert.assertEquals( "the message", json_exception.getMessage() );
		Assert.assertNull( json_exception.getCause() );

		json_exception.initCause( e2 );
		Assert.assertEquals( e2, json_exception.getCause() );

		json_exception = new JSONException( e1 );
		Assert.assertEquals( e1.toString(), json_exception.getMessage() );
		Assert.assertEquals( e1, json_exception.getCause() );

		try {
			json_exception.initCause( e2 );
			Assert.fail( "Exception expected!" );
		}
		catch (IllegalStateException e) {
		}
		Assert.assertEquals( e1.toString(), json_exception.getMessage() );
		Assert.assertEquals( e1, json_exception.getCause() );

		json_exception = new JSONException( "the message", e1 );
		Assert.assertEquals( "the message", json_exception.getMessage() );
		Assert.assertEquals( e1, json_exception.getCause() );

		try {
			json_exception.initCause( e2 );
			Assert.fail( "Exception expected!" );
		}
		catch (IllegalStateException e) {
		}
		Assert.assertEquals( "the message", json_exception.getMessage() );
		Assert.assertEquals( e1, json_exception.getCause() );
	}

}
