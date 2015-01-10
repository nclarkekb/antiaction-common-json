/*
 * JSON library.
 * Copyright 2012-2013 Antiaction (http://antiaction.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antiaction.common.json.representation;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 23/02/2013
 */
@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Array {

	// TODO Finish implementing and testing top level array (un)marshalling.
	@Test
	public void test_jsonobjectmapper_array() {
		/*
		System.out.println( boolean[].class.getName() );
		System.out.println( int[].class.getName() );
		System.out.println( long[].class.getName() );
		System.out.println( float[].class.getName() );
		System.out.println( double[].class.getName() );
		System.out.println( Boolean[].class.getName() );
		System.out.println( Integer[].class.getName() );
		System.out.println( Long[].class.getName() );
		System.out.println( Float[].class.getName() );
		System.out.println( Double[].class.getName() );
		System.out.println( BigInteger[].class.getName() );
		System.out.println( BigDecimal[].class.getName() );
		System.out.println( String[].class.getName() );
		*/

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

		JSONCollection json_struct;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();
		JSONStructureMarshaller marshaller = json_objectmappings.getStructureMarshaller();
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

			json_struct = marshaller.toJSONStructure( b1_arr );
			Assert.assertNotNull( json_struct );

			json_struct = marshaller.toJSONStructure( i1_arr );
			json_struct = marshaller.toJSONStructure( l1_arr );
			json_struct = marshaller.toJSONStructure( f1_arr );
			json_struct = marshaller.toJSONStructure( d1_arr );

		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

}
