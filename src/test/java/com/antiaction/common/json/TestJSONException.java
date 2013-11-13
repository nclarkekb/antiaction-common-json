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

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 01/12/2012
 */
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
