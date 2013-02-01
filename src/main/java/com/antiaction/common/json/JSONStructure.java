/*
 * Created on 02/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

/**
 * JSON Structure abstraction. Object and Array are the only current top level
 * JSON structures.
 *
 * @author Nicholas
 */
public abstract class JSONStructure extends JSONValue {

	// TODO
	public JSONObject addObject() {
		throw new UnsupportedOperationException();
	}

	// TODO
	public JSONObject addObject(String name) {
		throw new UnsupportedOperationException();
	}

	// TODO
	public JSONObject addObject(JSONString name) {
		throw new UnsupportedOperationException();
	}

	// TODO
	public JSONArray addArray() {
		throw new UnsupportedOperationException();
	}

	// TODO
	public JSONArray addArray(String name) {
		throw new UnsupportedOperationException();
	}

	// TODO
	public JSONArray addArray(JSONString name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add a JSON value to an array.
	 * @param value JSON value
	 */
	public void add(JSONValue value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add a JSON key/value pair to an object.
	 * @param name string key
	 * @param value JSON value
	 * @return the JSON String instance for the key
	 */
	public JSONString put(String name, JSONValue value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add a JSON key/value pair to an object.
	 * @param name JSON String key
	 * @param value JSON value
	 */
	public void put(JSONString name, JSONValue value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the JSON value in the array at the given index.
	 * @param index index in array
	 * @return JSON value in the array at the given index
	 */
	public JSONValue get(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the JSON value in the array associated with the given key.
	 * @param key string key
	 * @return JSON value in the array associated with the given key
	 */
	public JSONValue get(String key) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Get the JSON value in the array associated with the given key.
	 * @param key JSON String key
	 * @return JSON value in the array associated with the given key
	 */
	public JSONValue get(JSONString key) {
		throw new UnsupportedOperationException();
	}

}
