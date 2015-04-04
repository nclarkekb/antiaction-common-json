package com.antiaction.common.json;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;
import com.antiaction.common.json.annotation.JSONIgnore;

@RunWith(JUnit4.class)
public class TestJSONObjectMappings_OverrideIgnore {

	@Test
	public void test_objectmappings_overrideignore() {
		JSONObjectMappings json_om;
		JSONObjectMapping objectMapping;
		try {
			json_om = new JSONObjectMappings();
			objectMapping = json_om.register( TestOverrideIgnore.class );
			Assert.assertEquals( 1, objectMapping.fieldMappingsMap.size() );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		try {
			json_om = new JSONObjectMappings();
			Set<String> set = new HashSet<String>();
			json_om.overrideIgnoreMapSet.put( TestOverrideIgnore.class.getName(), set );
			set.add( "field1" );
			set.add( "field2" );
			objectMapping = json_om.register( TestOverrideIgnore.class );
			Assert.assertEquals( 3, objectMapping.fieldMappingsMap.size() );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@JSON(ignore={"field1"})
	public static class TestOverrideIgnore {

		public String field1;

		@JSONIgnore
		public String field2;

		public String field3;

	}

}
