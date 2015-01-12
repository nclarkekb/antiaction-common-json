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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;
import com.antiaction.common.json.TestHelpers;

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

		boolean[] b1_arr_copy;
		int[] i1_arr_copy;
		long[] l1_arr_copy;
		float[] f1_arr_copy;
		double[] d1_arr_copy;
		Boolean[] b2_arr_copy;
		Integer[] i2_arr_copy;
		Long[] l2_arr_copy;
		Float[] f2_arr_copy;
		Double[] d2_arr_copy;
		BigInteger[] bi_arr_copy;
		BigDecimal[] bd_arr_copy;
		String[] s_arr_copy;

		JSONCollection json_struct;
		JSONArray json_array;

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

			json_struct = marshaller.toJSONStructure( b1_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( true, json_array.get( 0 ).getBoolean() );
			Assert.assertEquals( false, json_array.get( 1 ).getBoolean() );

			b1_arr_copy = unmarshaller.toObject( json_struct, b1_arr.getClass() );
			Assert.assertNotNull( b1_arr_copy );
			Assert.assertArrayEquals( b1_arr, b1_arr_copy );

			json_struct = marshaller.toJSONStructure( i1_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Integer( 42 ), json_array.get( 0 ).getInteger() );
			Assert.assertEquals( new Integer( 4213 ), json_array.get( 1 ).getInteger() );

			i1_arr_copy = unmarshaller.toObject( json_struct, i1_arr.getClass() );
			Assert.assertNotNull( i1_arr_copy );
			Assert.assertArrayEquals( i1_arr, i1_arr_copy );

			json_struct = marshaller.toJSONStructure( l1_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Long( 12345678901234L ), json_array.get( 0 ).getLong() );
			Assert.assertEquals( new Long( 12345678901234L * 2L ), json_array.get( 1 ).getLong() );

			l1_arr_copy = unmarshaller.toObject( json_struct, l1_arr.getClass() );
			Assert.assertNotNull( l1_arr_copy );
			Assert.assertArrayEquals( l1_arr, l1_arr_copy );

			json_struct = marshaller.toJSONStructure( f1_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), json_array.get( 0 ).getFloat() );
			Assert.assertEquals( new Float( 1.0F / 5.0F ), json_array.get( 1 ).getFloat() );

			f1_arr_copy = unmarshaller.toObject( json_struct, f1_arr.getClass() );
			Assert.assertNotNull( f1_arr_copy );
			TestHelpers.assertArrayEquals( f1_arr, f1_arr_copy );

			json_struct = marshaller.toJSONStructure( d1_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), json_array.get( 0 ).getDouble() );
			Assert.assertEquals( new Double( 1.0 / 5.0 ), json_array.get( 1 ).getDouble() );

			d1_arr_copy = unmarshaller.toObject( json_struct, d1_arr.getClass() );
			Assert.assertNotNull( d1_arr_copy );
			TestHelpers.assertArrayEquals( d1_arr, d1_arr_copy );

			json_struct = marshaller.toJSONStructure( b2_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( false, json_array.get( 0 ).getBoolean() );
			Assert.assertEquals( true, json_array.get( 1 ).getBoolean() );

			b2_arr_copy = unmarshaller.toObject( json_struct, b2_arr.getClass() );
			Assert.assertNotNull( b2_arr_copy );
			Assert.assertArrayEquals( b2_arr, b2_arr_copy );

			json_struct = marshaller.toJSONStructure( i2_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Integer( 1234 ), json_array.get( 0 ).getInteger() );
			Assert.assertEquals( new Integer( 4321 ), json_array.get( 1 ).getInteger() );

			i2_arr_copy = unmarshaller.toObject( json_struct, i2_arr.getClass() );
			Assert.assertNotNull( i2_arr_copy );
			Assert.assertArrayEquals( i2_arr, i2_arr_copy );

			json_struct = marshaller.toJSONStructure( l2_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Long( 43210987654321L ), json_array.get( 0 ).getLong() );
			Assert.assertEquals( new Long( 43210987654321L * 2L ), json_array.get( 1 ).getLong() );

			l2_arr_copy = unmarshaller.toObject( json_struct, l2_arr.getClass() );
			Assert.assertNotNull( l2_arr_copy );
			Assert.assertArrayEquals( l2_arr, l2_arr_copy );

			json_struct = marshaller.toJSONStructure( f2_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Float( 3.0F ), json_array.get( 0 ).getFloat() );
			Assert.assertEquals( new Float( 5.0F ), json_array.get( 1 ).getFloat() );

			f2_arr_copy = unmarshaller.toObject( json_struct, f2_arr.getClass() );
			Assert.assertNotNull( f2_arr_copy );
			Assert.assertArrayEquals( f2_arr, f2_arr_copy );

			json_struct = marshaller.toJSONStructure( d2_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new Double( 3.0 ), json_array.get( 0 ).getDouble() );
			Assert.assertEquals( new Double( 5.0 ), json_array.get( 1 ).getDouble() );

			d2_arr_copy = unmarshaller.toObject( json_struct, d2_arr.getClass() );
			Assert.assertNotNull( d2_arr_copy );
			Assert.assertArrayEquals( d2_arr, d2_arr_copy );

			json_struct = marshaller.toJSONStructure( bi_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ), json_array.get( 0 ).getBigInteger() );
			Assert.assertEquals( new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ), json_array.get( 1 ).getBigInteger() );

			bi_arr_copy = unmarshaller.toObject( json_struct, bi_arr.getClass() );
			Assert.assertNotNull( bi_arr_copy );
			Assert.assertArrayEquals( bi_arr, bi_arr_copy );

			json_struct = marshaller.toJSONStructure( bd_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), json_array.get( 0 ).getBigDecimal() );
			Assert.assertEquals( new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ), json_array.get( 1 ).getBigDecimal() );

			bd_arr_copy = unmarshaller.toObject( json_struct, bd_arr.getClass() );
			Assert.assertNotNull( bd_arr_copy );
			Assert.assertArrayEquals( bd_arr, bd_arr_copy );

			json_struct = marshaller.toJSONStructure( s_arr );
			Assert.assertNotNull( json_struct );
			json_array = assertArray( json_struct );
			Assert.assertEquals( 2, json_array.values.size() );
			Assert.assertEquals( "json", json_array.get( 0 ).getString() );
			Assert.assertEquals( "JSON", json_array.get( 1 ).getString() );

			s_arr_copy = unmarshaller.toObject( json_struct, s_arr.getClass() );
			Assert.assertNotNull( s_arr_copy );
			Assert.assertArrayEquals( s_arr, s_arr_copy );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static JSONArray assertArray(JSONCollection json_struct) {
		try {
			json_struct.getObject();
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
		JSONArray json_array = json_struct.getArray();
		Assert.assertNotNull( json_array );
		return json_array;
	}

}
