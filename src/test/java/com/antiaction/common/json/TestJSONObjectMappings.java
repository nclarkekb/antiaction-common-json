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

import com.antiaction.common.json.TestJSONObjectMappings_ParametrizedCollectionFields.TestJSONMapValidList;
import com.antiaction.common.json.TestJSONObjectMappings_ParametrizedCollectionFields.TestJSONMapValidMap;
import com.antiaction.common.json.TestJSONObjectMappings_ParametrizedCollectionFields.TestJSONMapValidSet;
import com.antiaction.common.json.TestJSONStreamMarshaller.SFSResult;
import com.antiaction.common.json.TestJSONStreamMarshaller.TestJSONMapObject;
import com.antiaction.common.json.TestJSONStreamMarshaller.TestTypesClass;
import com.antiaction.common.json.integration.TestJSONMarshallingTopLevelArray.Book;
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

	@Test
	public void test_jsonobjectmappings_tostring() {
		StringBuilder sb = new StringBuilder();
		try {
			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			JSONObjectMapping objectMapping1 = json_objectmappings.register( SFSResult.class );
			JSONObjectMapping objectMapping2 = json_objectmappings.register( TestTypesClass.class );
			JSONObjectMapping objectMapping3 = json_objectmappings.register( TestJSONMapObject.class );
			JSONObjectMapping objectMapping4 = json_objectmappings.register( Book[].class );
			JSONObjectMapping objectMapping5 = json_objectmappings.register( Book[].class );
			JSONObjectMapping objectMapping6 = json_objectmappings.register( TestJSONMapValidList.class );
			JSONObjectMapping objectMapping7 = json_objectmappings.register( TestJSONMapValidMap.class );
			JSONObjectMapping objectMapping8 = json_objectmappings.register( TestJSONMapValidSet.class );

			String objectMappingToString = json_objectmappings.toString();

			assertObjectMapping( objectMapping1, sb, objectMappingToString );
			assertObjectMapping( objectMapping2, sb, objectMappingToString );
			assertObjectMapping( objectMapping3, sb, objectMappingToString );
			assertObjectMapping( objectMapping4, sb, objectMappingToString );
			assertObjectMapping( objectMapping5, sb, objectMappingToString );
			assertObjectMapping( objectMapping6, sb, objectMappingToString );
			assertObjectMapping( objectMapping7, sb, objectMappingToString );
			assertObjectMapping( objectMapping8, sb, objectMappingToString );

			//System.out.println( json_objectmappings.toString() );

			//byte[] bytes = json_objectmappings.toString().getBytes( "UTF-8" );
			//TestHelpers.saveFile( "objectmappings.txt", bytes );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		/*
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		*/
	}

	public void assertObjectMapping(JSONObjectMapping objectMapping, StringBuilder sb, String objectMappingToString) {
		sb.setLength( 0 );
		objectMapping.toString( sb );
		String toString = sb.toString();
		Assert.assertEquals( toString, objectMapping.toString() );
		Assert.assertNotEquals( -1, objectMappingToString.indexOf( toString ) );
		assertObjectFieldMappings( objectMapping, toString );
	}

	public void assertObjectFieldMappings(JSONObjectMapping objectMapping, String objectMappingToString) {
		StringBuilder sb = new StringBuilder();
		JSONObjectFieldMapping fieldMapping = objectMapping.fieldMapping;
		if ( fieldMapping != null ) {
			sb.setLength( 0 );
			fieldMapping.toString( sb );
			String toString = sb.toString();
			Assert.assertEquals( toString, fieldMapping.toString() );
			Assert.assertNotEquals( -1, objectMappingToString.indexOf( toString ) );
		}
		JSONObjectFieldMapping[] fieldMappingsArr = objectMapping.fieldMappingsArr;
		if ( fieldMappingsArr != null && fieldMappingsArr.length > 0 ) {
			for ( int i=0; i<fieldMappingsArr.length; ++i ) {
				fieldMapping = fieldMappingsArr[ i ];
				sb.setLength( 0 );
				fieldMapping.toString( sb );
				String toString = sb.toString();
				Assert.assertEquals( toString, fieldMapping.toString() );
				Assert.assertNotEquals( -1, objectMappingToString.indexOf( toString ) );
			}
		}
	}

}
