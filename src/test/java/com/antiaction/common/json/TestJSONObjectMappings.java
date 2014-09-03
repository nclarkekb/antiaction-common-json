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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.representation.TestJSONStructureMarshaller_Name.TestZeroConstructor;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 05/11/2013
 */
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
