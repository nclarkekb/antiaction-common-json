/*
 * Created on 01/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;

public abstract class JSONValue {

	public int type;

	public void encode(JSONEncoder encoder) throws IOException {
		throw new UnsupportedOperationException("Unimplemented");
	}

}
