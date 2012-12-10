/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Array implementation.
 *
 * @author Nicholas
 */
public class JSONArray extends JSONStructure {

	/** <code>List</code> of JSON Array values. */
	protected List<JSONValue> values = new ArrayList<JSONValue>();

	/**
	 * Construct a JSON Array.
	 */
	public JSONArray() {
		type = JSONConstants.VT_ARRAY;
	}

	@Override
	public void add(JSONValue value) {
		values.add(value);
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( '[' );
		for (int i=0; i<values.size(); ++i) {
			if ( i > 0 ) {
				encoder.write( ',' );
			}
			values.get( i ).encode( encoder );
		}
		encoder.write( ']' );
	}

	public void encode(JSONEncoder encoder, String indentation, String indent) throws IOException {
		encoder.write( "[\n" );
		String innerIndentation = indentation + indent;
		for (int i=0; i<values.size(); ++i) {
			if ( i > 0 ) {
				encoder.write( ", " );
			}
			encoder.write( innerIndentation );
			values.get( i ).encode( encoder, innerIndentation, indent );
		}
		encoder.write( "\n" );
		encoder.write( indentation );
		encoder.write( ']' );
	}

	@Override
	public JSONArray getArray() {
		return this;
	}

	@Override
	public JSONValue get(int index) {
		return values.get( index );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( '[' );
		for (int i=0; i<values.size(); ++i) {
			if ( i > 0) {
				sb.append( ", " );
			}
			sb.append( values.get( i ).toString() );
		}
		sb.append( ']' );
		return sb.toString();
	}

}
