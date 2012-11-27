/*
 * Created on 22/11/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A JSON object mapping description.
 *
 * @author Nicholas
 */
public class JSONObjectMapping {

	/** Field names to ignore when mapping objects. */
	public Set<String> ignore = new HashSet<String>();

	/** Map of mapped fields. */
	public Map<String, JSONObjectFieldMapping> fieldMappingsMap = new TreeMap<String, JSONObjectFieldMapping>();

	/** List of mapped fields. */
	public List<JSONObjectFieldMapping> fieldMappingsList = new LinkedList<JSONObjectFieldMapping>();

}
