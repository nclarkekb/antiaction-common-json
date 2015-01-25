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

@RunWith(JUnit4.class)
public class TestJSONObjectMappingConstants {

	@Test
	public void test_objectmappingconstants() {
		JSONObjectMappingConstants constants = new JSONObjectMappingConstants();
		Assert.assertNotNull( constants );
		Assert.assertEquals( "T_PRIMITIVE_BOOLEAN", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_PRIMITIVE_BOOLEAN ) );
		Assert.assertEquals( "T_PRIMITIVE_INTEGER", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_PRIMITIVE_INTEGER ) );
		Assert.assertEquals( "T_PRIMITIVE_LONG", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_PRIMITIVE_LONG ) );
		Assert.assertEquals( "T_PRIMITIVE_FLOAT", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_PRIMITIVE_FLOAT ) );
		Assert.assertEquals( "T_PRIMITIVE_DOUBLE", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_PRIMITIVE_DOUBLE ) );
		Assert.assertEquals( "T_OBJECT", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_OBJECT ) );
		Assert.assertEquals( "T_BOOLEAN", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_BOOLEAN ) );
		Assert.assertEquals( "T_INTEGER", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_INTEGER ) );
		Assert.assertEquals( "T_LONG", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_LONG ) );
		Assert.assertEquals( "T_FLOAT", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_FLOAT ) );
		Assert.assertEquals( "T_DOUBLE", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_DOUBLE ) );
		Assert.assertEquals( "T_BIGINTEGER", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_BIGINTEGER ) );
		Assert.assertEquals( "T_BIGDECIMAL", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_BIGDECIMAL ) );
		Assert.assertEquals( "T_STRING", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_STRING ) );
		Assert.assertEquals( "T_BYTEARRAY", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_BYTEARRAY ) );
		Assert.assertEquals( "T_ARRAY", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_ARRAY ) );
		Assert.assertEquals( "T_LIST", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_LIST ) );
		Assert.assertEquals( "T_MAP", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_MAP ) );
		Assert.assertEquals( "T_SET", JSONObjectMappingConstants.typeString( JSONObjectMappingConstants.T_SET ) );
		Assert.assertEquals( "Unknown", JSONObjectMappingConstants.typeString( 42 ) );
		Assert.assertEquals( "null", JSONObjectMappingConstants.typeString( null ) );
	}

}
