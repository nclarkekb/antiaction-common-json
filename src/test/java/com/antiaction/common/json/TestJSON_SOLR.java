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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.representation.JSONCollection;
import com.antiaction.common.json.representation.JSONTextUnmarshaller;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 22/01/2013
 */
@RunWith(JUnit4.class)
public class TestJSON_SOLR {

	@Test
	public void test_json_solr() {
		File file = TestHelpers.getTestResourceFile( "books.json" );
		// debug
		//System.out.println( file.getPath() );
		FileInputStream fis = null;

		PushbackInputStream pbin;
		int encoding;

		JSONTextUnmarshaller textUnmarshaller = new JSONTextUnmarshaller();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;
		JSONCollection json_struct;

		try {
	        fis = new FileInputStream( file );
			pbin = new PushbackInputStream( fis, 4 );
			encoding = JSONEncoding.encoding( pbin );

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			json_objectmappings.register( Book[].class );

			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = textUnmarshaller.toJSONStructure( pbin, json_decoder );

			Book[] books = json_objectmappings.getStructureUnmarshaller().toObject( json_struct, Book[].class );
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if ( fis != null ) {
				try {
					fis.close();
				}
				catch (IOException e) {
				}
			}
		}
	}

	public static class Book {
		String id;
	}

}
