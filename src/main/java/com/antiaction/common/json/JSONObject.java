/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JSON Object implementation.
 *
 * @author Nicholas
 */
public class JSONObject extends JSONStructure {

	/** <code>Map</code> of JSON Object key/value pairs. */
	protected Map<JSONString, JSONValue> values = new HashMap<JSONString, JSONValue>();

	/**
	 * Construct a JSON OBject.
	 */
	public JSONObject() {
		type = JSONConstants.VT_OBJECT;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( '{' );
		Iterator<Entry<JSONString, JSONValue>> iter = values.entrySet().iterator();
		Entry<JSONString, JSONValue> entry;
		int i = 0;
		while ( iter.hasNext() ) {
			if ( i > 0 ) {
				encoder.write( ',' );
			}
			entry = iter.next();
			entry.getKey().encode( encoder );
			encoder.write( ':' );
			entry.getValue().encode( encoder );
			++i;
		}
		encoder.write( '}' );
	}

	@Override
	public void encode(JSONEncoder encoder, String indentation, String indent) throws IOException {
		encoder.write( "{\n" );
		Iterator<Entry<JSONString, JSONValue>> iter = values.entrySet().iterator();
		Entry<JSONString, JSONValue> entry;
		String innerIndentation = indentation + indent;
		int i = 0;
		while ( iter.hasNext() ) {
			if ( i > 0 ) {
				encoder.write( ",\n" );
			}
			entry = iter.next();
			encoder.write( innerIndentation );
			entry.getKey().encode( encoder, innerIndentation, indent );
			encoder.write( ": " );
			entry.getValue().encode( encoder, innerIndentation, indent );
			++i;
		}
		encoder.write( "\n" );
		encoder.write( indentation );
		encoder.write( "}" );
	}

	@Override 
	public JSONString put(String name, JSONValue value) {
		JSONString json_string = JSONString.String( name );
		values.put( json_string, value );
		return json_string;
	}

	@Override
	public void put(JSONString name, JSONValue value) {
		values.put(name, value);
	}

	@Override
	public JSONObject getObject() {
		return this;
	}

	@Override
	public JSONValue get(String key) {
		return values.get( JSONString.String( key ) );
	}

	@Override
	public JSONValue get(JSONString key) {
		return values.get( key );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( '{' );
		Iterator<Entry<JSONString, JSONValue>> iter = values.entrySet().iterator();
		Entry<JSONString, JSONValue> entry;
		int x = 0;
		while (iter.hasNext()) {
			entry = iter.next();
			if ( x > 0 ) {
				sb.append( ", " );
			}
			sb.append( entry.getKey().toString() );
			sb.append( ": " );
			sb.append( entry.getValue().toString() );
			++x;
		}
		sb.append( '}' );
		return sb.toString();
	}

}
