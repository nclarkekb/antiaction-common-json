/*
 * Created on 02/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.util.Arrays;

public class JSONBoolean extends JSONValue {

	public static final JSONBoolean False = new JSONBoolean( false );

	protected static final byte[] falseBytes = "false".getBytes();

	protected static int false_hashCode = Arrays.deepHashCode( new Object[] { falseBytes } );

	public static final JSONBoolean True = new JSONBoolean( true );

	protected static final byte[] trueBytes = "true".getBytes();

	protected static int true_hashCode = Arrays.deepHashCode( new Object[] { trueBytes } );

	public static final JSONBoolean Boolean(boolean b) {
		return new JSONBoolean( b );
	}

	protected boolean b;

	public JSONBoolean(boolean b) {
		type = JSONConstants.VT_BOOLEAN;
		this.b = b;
	}

	public boolean getBoolean() {
		return b;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		if ( b ) {
			encoder.write( trueBytes );
		}
		else {
			encoder.write( falseBytes );
		}
	}

	@Override
	public String toString() {
		if ( b ) {
			return "true";
		}
		else {
			return "false";
		}
	}

	@Override
    public boolean equals(Object obj) {
        if ( obj == null || !(obj instanceof JSONBoolean) ) {
            return false;
        }
        JSONBoolean json_booleanObj = (JSONBoolean)obj;
        if ( b != json_booleanObj.b ) {
            return false;
        }
        return true;
    }

	@Override
    public int hashCode() {
    	if ( b ) {
    		return true_hashCode;
    	}
        return false_hashCode;
    }

}
