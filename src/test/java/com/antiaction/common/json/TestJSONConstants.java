/*
 * Created on 07/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONConstants {

	@Test
	public void test_jsonconstants() {
		JSONConstants json_constants = new JSONConstants();
		Assert.assertNotNull( json_constants );
	}

}
