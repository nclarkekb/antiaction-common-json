/*
 * Created on 27/07/2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json.representation;

public class PackageAccess {

	public static void setObjectValues(JSONCollection json_struct, JSONString key, JSONValue value) {
		((JSONObject)json_struct).values.put( key, value );
	}

}
