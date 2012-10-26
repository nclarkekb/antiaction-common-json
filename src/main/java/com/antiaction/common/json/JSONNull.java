/*
 * Created on 18/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;

public class JSONNull extends JSONValue {

	public static final JSONNull Null = new JSONNull();

	public static final byte[] nullBytes = "null".getBytes();

	protected JSONNull() {
		type = JSONConstants.VT_NULL;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( nullBytes );
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
    public boolean equals(Object obj) {
        if ( obj == null || !(obj instanceof JSONNull) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return nullBytes.hashCode();
    }

}