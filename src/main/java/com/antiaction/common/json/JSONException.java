/*
 * Created on 26/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

/**
 * JSON Exception implementation.
 *
 * @author Nicholas
 */
public class JSONException extends Exception {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -5102288985719103747L;

	public JSONException() {
		super();
	}

	public JSONException(String message) {
		super( message );
	}

	public JSONException(Throwable t) {
		super( t );
	}

	public JSONException(String message, Throwable t) {
		super( message, t );
	}

}
