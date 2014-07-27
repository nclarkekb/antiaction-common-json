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

package com.antiaction.common.json.representation;

import java.io.IOException;
import java.io.OutputStream;

import com.antiaction.common.json.JSONEncoder;
import com.antiaction.common.json.JSONException;

/**
 * Serialize JSON structure into a JSON text string.
 *
 * @author Nicholas
 * Created on 06/08/2012
 */
public class JSONTextMarshaller {

	public void toJSONText(JSONCollection json_structure, JSONEncoder encoder, boolean bPretty, OutputStream out) throws IOException, JSONException {
		encoder.init( out );
		if ( json_structure != null ) {
			if ( bPretty ) {
				json_structure.encode( encoder, "", "  " );
			}
			else {
				json_structure.encode( encoder );
			}
		}
		else {
			throw new JSONException( "Invalid JSON structure!" );
		}
		encoder.close();
	}

}
