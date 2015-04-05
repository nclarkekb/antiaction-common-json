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

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;
import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMappings_OverrideIgnore {

	@Test
	public void test_objectmappings_overrideignore() {
		JSONObjectMappings json_om;
		JSONObjectMapping objectMapping;
		try {
			json_om = new JSONObjectMappings();
			objectMapping = json_om.register( TestOverrideIgnore.class );
			Assert.assertEquals( 1, objectMapping.fieldMappingsMap.size() );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		try {
			json_om = new JSONObjectMappings();
			Set<String> overrideIgnoreSet = new HashSet<String>();
			json_om.overrideIgnoreMapSet.put( TestOverrideIgnore.class.getName(), overrideIgnoreSet );
			overrideIgnoreSet.add( "field1" );
			overrideIgnoreSet.add( "field2" );
			objectMapping = json_om.register( TestOverrideIgnore.class );
			Assert.assertEquals( 3, objectMapping.fieldMappingsMap.size() );
			Assert.assertEquals( true, objectMapping.fieldMappingsArr[ 0 ].nullable );
			Assert.assertEquals( true, objectMapping.fieldMappingsArr[ 1 ].nullable );
			Assert.assertEquals( false, objectMapping.fieldMappingsArr[ 2 ].nullable );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		try {
			json_om = new JSONObjectMappings();
			Set<String> overrideIgnoreSet = new HashSet<String>();
			json_om.overrideIgnoreMapSet.put( TestOverrideIgnore.class.getName(), overrideIgnoreSet );
			overrideIgnoreSet.add( "field1" );
			overrideIgnoreSet.add( "field2" );
			Set<String> forceNullableSet = new HashSet<String>();
			json_om.forceNullableMapSet.put( TestOverrideIgnore.class.getName(), forceNullableSet );
			forceNullableSet.add( "field3" );
			objectMapping = json_om.register( TestOverrideIgnore.class );
			Assert.assertEquals( 3, objectMapping.fieldMappingsMap.size() );
			Assert.assertEquals( true, objectMapping.fieldMappingsArr[ 0 ].nullable );
			Assert.assertEquals( true, objectMapping.fieldMappingsArr[ 1 ].nullable );
			Assert.assertEquals( true, objectMapping.fieldMappingsArr[ 2 ].nullable );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@JSON(ignore={"field1"}, nullable={"field1"})
	public static class TestOverrideIgnore {

		public String field1;

		@JSONIgnore
		@JSONNullable
		public String field2;

		public String field3;

	}

}
