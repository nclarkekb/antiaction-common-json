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
 * Created on 07/09/2012
 */
@RunWith(JUnit4.class)
public class TestJSONStructure {

	@Test
	public void test_jsonstructure() {
		JSONStructure json_structure = new JSONStructure() {
		};

		try {
			json_structure.addObject();
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.addObject( "name" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.addObject( JSONString.String( "name" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.addArray();
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.addArray( "name" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.addArray( JSONString.String( "name" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.add( JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.put( "name", JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.put( JSONString.String( "null" ), JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.get( 42 );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.get( "42" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_structure.get( JSONString.String( "42" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

}
