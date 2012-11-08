/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JSONArray extends JSONStructure {

	protected List<JSONValue> values = new LinkedList<JSONValue>();

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
