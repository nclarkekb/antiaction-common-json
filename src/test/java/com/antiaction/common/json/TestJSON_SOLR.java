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

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 22/01/2013
 */
@RunWith(JUnit4.class)
public class TestJSON_SOLR {

	@Ignore
	@Test
	public void test_json_solr() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("books.json");

		PushbackInputStream pbin;
		int encoding;

		JSONText json = new JSONText();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;
		JSONStructure json_struct;

		try {
			pbin = new PushbackInputStream( in, 4 );
			encoding = JSONEncoding.encoding( pbin );

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			json_objectmappings.register( Book[].class );

			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_struct = json.decodeJSONtext( pbin, json_decoder );

			Book[] books = json_objectmappings.getStructureUnmarshaller().toObject( json_struct, Book[].class );
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Book {
		String id;
	}

}
