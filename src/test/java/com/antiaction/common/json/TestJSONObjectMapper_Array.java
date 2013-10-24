/*
 * Created on 23/02/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Array {

	@Test
	public void test_jsonobjectmapper_array() {
		boolean[] b1_arr = new boolean[] { true, false };
		int[] i1_arr = new int[] { 42, 4213 };
		long[] l1_arr = new long[] { 12345678901234L, 12345678901234L * 2L };
		float[] f1_arr = new float[] { 1.0F / 3.0F, 1.0F / 5.0F };
		double[] d1_arr = new double[] { 1.0 / 3.0, 1.0 / 5.0 };
		Boolean[] b2_arr = new Boolean[] { false, true };
		Integer[] i2_arr = new Integer[] { 1234, 4321 };
		Long[] l2_arr = new Long[] { 43210987654321L, 43210987654321L * 2L };
		Float[] f2_arr = new Float[] { 3.0F, 5.0F };
		Double[] d2_arr = new Double[] { 3.0, 5.0 };
		BigInteger[] bi_arr = new BigInteger[] { new BigInteger( "123456789012345678901234567890123456789012" ), new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ) };
		BigDecimal[] bd_arr = new BigDecimal[] { new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ) };
		String[] s_arr = new String[] { "json", "JSON" };

		JSONStructure json_struct;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();
		JSONStructureMarshaller marshaller = json_objectmappings.getStructureMarshaller();
		JSONStructureUnmarshaller unmarshaller = json_objectmappings.getStructureUnmarshaller();
		try {
			json_objectmappings.register( b1_arr.getClass() );
			json_objectmappings.register( i1_arr.getClass() );
			json_objectmappings.register( l1_arr.getClass() );
			json_objectmappings.register( f1_arr.getClass() );
			json_objectmappings.register( d1_arr.getClass() );
			json_objectmappings.register( b2_arr.getClass() );
			json_objectmappings.register( i2_arr.getClass() );
			json_objectmappings.register( l2_arr.getClass() );
			json_objectmappings.register( f2_arr.getClass() );
			json_objectmappings.register( d2_arr.getClass() );
			json_objectmappings.register( bi_arr.getClass() );
			json_objectmappings.register( bd_arr.getClass() );
			json_objectmappings.register( s_arr.getClass() );

			json_struct = marshaller.toJSON( b1_arr );
			Assert.assertNotNull( json_struct );

			json_struct = marshaller.toJSON( i1_arr );
			json_struct = marshaller.toJSON( l1_arr );
			json_struct = marshaller.toJSON( f1_arr );
			json_struct = marshaller.toJSON( d1_arr );

		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

}
