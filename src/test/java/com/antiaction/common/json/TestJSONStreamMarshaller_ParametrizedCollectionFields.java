package com.antiaction.common.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONNullable;

/**
 * FIXME Support byte/char/Byte/Character collection tests
 * @author nicl
 */
@RunWith(JUnit4.class)
public class TestJSONStreamMarshaller_ParametrizedCollectionFields {

	@Test
	public void test_jsonobjectmapper_array_tojson() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONEncoder json_encoder = json_encoding.getJSONEncoder( JSONEncoding.E_UTF8 );
		JSONDecoder json_decoder = json_encoding.getJSONDecoder( JSONEncoding.E_UTF8 );
		JSONObjectMappings json_objectmappings = new JSONObjectMappings();
		JSONStreamMarshaller streamMarshaller = json_objectmappings.getStreamMarshaller();
		JSONStreamUnmarshaller streamUnmarshaller = json_objectmappings.getStreamUnmarshaller();
		try {
			json_objectmappings.register( TestJSONMapObjectArrays.class );

			TestJSONMapObjectArrays obj = getTestJSONMapObjectArraysObjectMold();
			TestJSONMapObjectArrays result;

			out.reset();
			streamMarshaller.toJSONText( obj, json_encoder, false, out );

			byte[] json_compact = out.toByteArray();
			// debug
			//System.out.println( new String( json_compact ) );

			in = new ByteArrayInputStream( json_compact );
			result = streamUnmarshaller.toObject( in, json_decoder, TestJSONMapObjectArrays.class );
			assert_jsonobjectmapper_array_result( result );

			out.reset();
			streamMarshaller.toJSONText( obj, json_encoder, true, out );

			byte[] json_pretty = out.toByteArray();
			// debug
			//System.out.println( new String( json_pretty ) );

			in = new ByteArrayInputStream( json_pretty );
			result = streamUnmarshaller.toObject( in, json_decoder, TestJSONMapObjectArrays.class );
			assert_jsonobjectmapper_array_result( result );

            Assert.assertThat( json_compact.length, is( not( equalTo( json_pretty.length ) ) ) );

            json_pretty = TestHelpers.filterWhitespaces( json_pretty );

            Assert.assertEquals( json_compact.length, json_pretty.length );
            Assert.assertArrayEquals( json_compact, json_pretty );
			/*
			 * Null values.
			 */
			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).b2_arr = TestHelpers.arrayToArrayList( new Boolean[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).i2_arr = TestHelpers.arrayToArrayList( new Integer[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).l2_arr = TestHelpers.arrayToArrayList( new Long[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).f2_arr = TestHelpers.arrayToArrayList( new Float[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).d2_arr = TestHelpers.arrayToArrayList( new Double[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).bi_arr = TestHelpers.arrayToArrayList( new BigInteger[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).bd_arr = TestHelpers.arrayToArrayList( new BigDecimal[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).s_arr = TestHelpers.arrayToArrayList( new String[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );

			obj = getTestJSONMapObjectArraysObjectMold();
			obj.obj_arr.get( 0 ).obj_arr = TestHelpers.arrayToArrayList( new TestJSONMapObjectArrays[ 1 ] );
			assert_tojson_exception( streamMarshaller, obj, json_encoder, out );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public TestJSONMapObjectArrays getTestJSONMapObjectArraysObjectMold() {
		TestJSONMapObjectArrays obj = new TestJSONMapObjectArrays();
		obj.b2_arr = TestHelpers.arrayToArrayList( new Boolean[] { false, true } );
		obj.i2_arr = TestHelpers.arrayToArrayList( new Integer[] { 1234, 4321 } );
		obj.l2_arr = TestHelpers.arrayToArrayList( new Long[] { 43210987654321L, 43210987654321L * 2L } );
		obj.f2_arr = TestHelpers.arrayToArrayList( new Float[] { 3.0F, 5.0F } );
		obj.d2_arr = TestHelpers.arrayToArrayList( new Double[] { 3.0, 5.0 } );
		obj.bi_arr = TestHelpers.arrayToArrayList( new BigInteger[] { new BigInteger( "123456789012345678901234567890123456789012" ), new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ) } );
		obj.bd_arr = TestHelpers.arrayToArrayList( new BigDecimal[] { new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ) } );
		obj.s_arr = TestHelpers.arrayToArrayList( new String[] { "json", "JSON" } );
		//obj.b = "bytes".getBytes();
		TestJSONMapObjectArrays obj2 = new TestJSONMapObjectArrays();
		obj.obj_arr = TestHelpers.arrayToArrayList( new TestJSONMapObjectArrays[] { obj2 } );
		obj2.b2_arr = TestHelpers.arrayToArrayList( new Boolean[] {} );
		obj2.i2_arr = TestHelpers.arrayToArrayList( new Integer[] {} );
		obj2.l2_arr = TestHelpers.arrayToArrayList( new Long[] {} );
		obj2.f2_arr = TestHelpers.arrayToArrayList( new Float[] {} );
		obj2.d2_arr = TestHelpers.arrayToArrayList( new Double[] {} );
		obj2.bi_arr = TestHelpers.arrayToArrayList( new BigInteger[] {} );
		obj2.bd_arr = TestHelpers.arrayToArrayList( new BigDecimal[] {} );
		obj2.s_arr = TestHelpers.arrayToArrayList( new String[] {} );
		//obj2.b = "BYTES".getBytes();
		obj2.obj_arr = TestHelpers.arrayToArrayList( new TestJSONMapObjectArrays[] { new TestJSONMapObjectArrays() } );
		return obj;
	}

	public static class TestJSONMapObjectArrays {

		@JSONNullable
		public ArrayList<Boolean> b2_arr;

		@JSONNullable
		public ArrayList<Integer> i2_arr;

		@JSONNullable
		public ArrayList<Long> l2_arr;

		@JSONNullable
		public ArrayList<Float> f2_arr;

		@JSONNullable
		public ArrayList<Double> d2_arr;

		@JSONNullable
		public ArrayList<BigInteger> bi_arr;

		@JSONNullable
		public ArrayList<BigDecimal> bd_arr;

		@JSONNullable
		public ArrayList<String> s_arr;

		@JSONNullable
		public ArrayList<TestJSONMapObjectArrays> obj_arr;

	}

	public void assert_jsonobjectmapper_array_result(TestJSONMapObjectArrays result) {
		ArrayList<Boolean> b2_arr = TestHelpers.arrayToArrayList( new Boolean[] { false, true } );
		ArrayList<Integer> i2_arr = TestHelpers.arrayToArrayList( new Integer[] { 1234, 4321 } );
		ArrayList<Long> l2_arr = TestHelpers.arrayToArrayList( new Long[] { 43210987654321L, 43210987654321L * 2L } );
		ArrayList<Float> f2_arr = TestHelpers.arrayToArrayList( new Float[] { 3.0F, 5.0F } );
		ArrayList<Double> d2_arr = TestHelpers.arrayToArrayList( new Double[] { 3.0, 5.0 } );
		ArrayList<BigInteger> bi_arr = TestHelpers.arrayToArrayList( new BigInteger[] { new BigInteger( "123456789012345678901234567890123456789012" ), new BigInteger( "123456789012345678901234567890123456789012" ).multiply( new BigInteger( "2" ) ) } );
		ArrayList<BigDecimal> bd_arr = TestHelpers.arrayToArrayList( new BigDecimal[] { new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ), new BigDecimal( "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825" ).multiply( new BigDecimal( "2" ) ) } );
		ArrayList<String> s_arr = TestHelpers.arrayToArrayList( new String[] { "json", "JSON" } );

		Assert.assertNotNull( result );
		TestHelpers.assertListEquals( b2_arr, result.b2_arr );
		TestHelpers.assertListEquals( i2_arr, result.i2_arr );
		TestHelpers.assertListEquals( l2_arr, result.l2_arr );
		TestHelpers.assertListEquals( f2_arr, result.f2_arr );
		TestHelpers.assertListEquals( d2_arr, result.d2_arr );
		TestHelpers.assertListEquals( bi_arr, result.bi_arr );
		TestHelpers.assertListEquals( bd_arr, result.bd_arr );
		TestHelpers.assertListEquals( s_arr, result.s_arr );

		b2_arr = TestHelpers.arrayToArrayList( new Boolean[] {} );
		i2_arr = TestHelpers.arrayToArrayList( new Integer[] {} );
		l2_arr = TestHelpers.arrayToArrayList( new Long[] {} );
		f2_arr = TestHelpers.arrayToArrayList( new Float[] {} );
		d2_arr = TestHelpers.arrayToArrayList( new Double[] {} );
		bi_arr = TestHelpers.arrayToArrayList( new BigInteger[] {} );
		bd_arr = TestHelpers.arrayToArrayList( new BigDecimal[] {} );
		s_arr = TestHelpers.arrayToArrayList( new String[] {} );

		Assert.assertNotNull( result.obj_arr );
		Assert.assertEquals( 1, result.obj_arr.size() );
		result = result.obj_arr.get( 0 );

		TestHelpers.assertListEquals( b2_arr, result.b2_arr );
		TestHelpers.assertListEquals( i2_arr, result.i2_arr );
		TestHelpers.assertListEquals( l2_arr, result.l2_arr );
		TestHelpers.assertListEquals( f2_arr, result.f2_arr );
		TestHelpers.assertListEquals( d2_arr, result.d2_arr );
		TestHelpers.assertListEquals( bi_arr, result.bi_arr );
		TestHelpers.assertListEquals( bd_arr, result.bd_arr );
		TestHelpers.assertListEquals( s_arr, result.s_arr );

		Assert.assertNotNull( result.obj_arr );
		Assert.assertEquals( 1, result.obj_arr.size() );
		result = result.obj_arr.get( 0 );

		Assert.assertNull( result.b2_arr );
		Assert.assertNull( result.i2_arr );
		Assert.assertNull( result.l2_arr );
		Assert.assertNull( result.f2_arr );
		Assert.assertNull( result.d2_arr );
		Assert.assertNull( result.bi_arr );
		Assert.assertNull( result.bd_arr );
		Assert.assertNull( result.s_arr );
		Assert.assertNull( result.obj_arr );
	}

	public void assert_tojson_exception(JSONStreamMarshaller marshaller, Object obj, JSONEncoder json_encoder, ByteArrayOutputStream out) throws IllegalArgumentException, IllegalAccessException {
		try {
			marshaller.toJSONText( obj, json_encoder, false, out );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			// debug
			//e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected Exception!" );
		}
	}

}
