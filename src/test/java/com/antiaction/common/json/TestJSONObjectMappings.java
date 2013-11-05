/*
 * Created on 05/11/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.TestJSONObjectMapper_Name.TestZeroConstructor;

@RunWith(JUnit4.class)
public class TestJSONObjectMappings {

	@Test
	public  void test_jsonobjectmappings() {
		JSONObjectMappings json_objectmappings = new JSONObjectMappings();

		/*
		 * Missing zero argument constructor.
		 */

		try {
			json_objectmappings.register( TestZeroConstructor.class );
		}
		catch (JSONException e) {
			// debug
			//System.out.println( e.getMessage() );
			//System.out.println( e.getMessage().indexOf( " does not have a zero argument contructor!" ) );
            Assert.assertThat( e.getMessage().indexOf( " does not have a zero argument constructor!" ), is( not( equalTo( -1 ) ) ) );
		}

	}

}
