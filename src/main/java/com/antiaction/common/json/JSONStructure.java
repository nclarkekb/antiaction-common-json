/*
 * Created on 02/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

public abstract class JSONStructure extends JSONValue {

	public void add(JSONValue value) {
		throw new UnsupportedOperationException();
	}

	public JSONString put(String name, JSONValue value) {
		throw new UnsupportedOperationException();
	}

	public void put(JSONString name, JSONValue value) {
		throw new UnsupportedOperationException();
	}

	public JSONValue get(int index) {
		throw new UnsupportedOperationException();
	}

	public JSONValue get(String key) {
		throw new UnsupportedOperationException();
	}

	public JSONValue get(JSONString key) {
		throw new UnsupportedOperationException();
	}

}
