/*
 * Created on 16/01/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONConverter;
import com.antiaction.common.json.annotation.JSONNullValues;
import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Converter {

	@Test
	public void test_jsonobjectmapper_converter_exceptions() {
		String fieldName = "Abe";
		JSONValue json_value = null;

		JSONConverterAbstract converter = new JSONConverterAbstract() {
		};

		try {
			converter.getBoolean( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getInteger( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getLong( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getFloat( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getDouble( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getBigInteger( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getBigDecimal( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getString( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			converter.getBytes( fieldName, json_value );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_value = converter.getJSONValue( fieldName, false );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, 42 );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, 1234L );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, 1.0F );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, 2.0 );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, new BigInteger( "42" ) );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, new BigDecimal( "3.14159" ) );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, "str" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_value = converter.getJSONValue( fieldName, new byte[] { 4, 2 } );
		}
		catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void test_jsonobjectmapper_converter_toobject() {
		JSONConverterAbstract[] converters;
		JSONObjectMapper json_om = new JSONObjectMapper();
		JSONStructure json_struct;

		try {
			json_om.register( TestBoolean1Conversion.class );
			json_om.register( TestInteger1Conversion.class );
			json_om.register( TestLong1Conversion.class );
			json_om.register( TestFloat1Conversion.class );
			json_om.register( TestDouble1Conversion.class );
			json_om.register( TestBoolean2Conversion.class );
			json_om.register( TestInteger2Conversion.class );
			json_om.register( TestLong2Conversion.class );
			json_om.register( TestFloat2Conversion.class );
			json_om.register( TestDouble2Conversion.class );
			json_om.register( TestBigIntegerConversion.class );
			json_om.register( TestBigDecimalConversion.class );
			json_om.register( TestStringConversion.class );
			json_om.register( TestBytesConversion.class );

			json_om.register( TestBooleanArray1Conversion.class );
			json_om.register( TestIntegerArray1Conversion.class );
			json_om.register( TestLongArray1Conversion.class );
			json_om.register( TestFloatArray1Conversion.class );
			json_om.register( TestDoubleArray1Conversion.class );
			json_om.register( TestBooleanArray2Conversion.class );
			json_om.register( TestIntegerArray2Conversion.class );
			json_om.register( TestLongArray2Conversion.class );
			json_om.register( TestFloatArray2Conversion.class );
			json_om.register( TestDoubleArray2Conversion.class );
			json_om.register( TestBigIntegerArrayConversion.class );
			json_om.register( TestBigDecimalArrayConversion.class );
			json_om.register( TestStringArrayConversion.class );

			json_om.register( TestNullableBoolean2Conversion.class );
			json_om.register( TestNullableInteger2Conversion.class );
			json_om.register( TestNullableLong2Conversion.class );
			json_om.register( TestNullableFloat2Conversion.class );
			json_om.register( TestNullableDouble2Conversion.class );
			json_om.register( TestNullableBigIntegerConversion.class );
			json_om.register( TestNullableBigDecimalConversion.class );
			json_om.register( TestNullableStringConversion.class );
			json_om.register( TestNullableBytesConversion.class );

			json_om.register( TestNullValuesBooleanArray2Conversion.class );
			json_om.register( TestNullValuesIntegerArray2Conversion.class );
			json_om.register( TestNullValuesLongArray2Conversion.class );
			json_om.register( TestNullValuesFloatArray2Conversion.class );
			json_om.register( TestNullValuesDoubleArray2Conversion.class );
			json_om.register( TestNullValuesBigIntegerArrayConversion.class );
			json_om.register( TestNullValuesBigDecimalArrayConversion.class );
			json_om.register( TestNullValuesStringArrayConversion.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		TestBoolean1Conversion bc1;
		TestInteger1Conversion ic1;
		TestLong1Conversion lc1;
		TestFloat1Conversion fc1;
		TestDouble1Conversion dc1;
		TestBoolean2Conversion bc2;
		TestInteger2Conversion ic2;
		TestLong2Conversion lc2;
		TestFloat2Conversion fc2;
		TestDouble2Conversion dc2;
		TestBigIntegerConversion bic;
		TestBigDecimalConversion bdc;
		TestStringConversion sc;
		TestBytesConversion bac;

		TestBooleanArray1Conversion bc1_arr;
		TestIntegerArray1Conversion ic1_arr;
		TestLongArray1Conversion lc1_arr;
		TestFloatArray1Conversion fc1_arr;
		TestDoubleArray1Conversion dc1_arr;
		TestBooleanArray2Conversion bc2_arr;
		TestIntegerArray2Conversion ic2_arr;
		TestLongArray2Conversion lc2_arr;
		TestFloatArray2Conversion fc2_arr;
		TestDoubleArray2Conversion dc2_arr;
		TestBigIntegerArrayConversion bic_arr;
		TestBigDecimalArrayConversion bdc_arr;
		TestStringArrayConversion sc_arr;

		TestNullableBoolean2Conversion nbc2;
		TestNullableInteger2Conversion nic2;
		TestNullableLong2Conversion nlc2;
		TestNullableFloat2Conversion nfc2;
		TestNullableDouble2Conversion ndc2;
		TestNullableBigIntegerConversion nbic;
		TestNullableBigDecimalConversion nbdc;
		TestNullableStringConversion nsc;
		TestNullableBytesConversion nbac;

		TestNullValuesBooleanArray2Conversion nvbc2_arr;
		TestNullValuesIntegerArray2Conversion nvic2_arr;
		TestNullValuesLongArray2Conversion nvlc2_arr;
		TestNullValuesFloatArray2Conversion nvfc2_arr;
		TestNullValuesDoubleArray2Conversion nvdc2_arr;
		TestNullValuesBigIntegerArrayConversion nvbic_arr;
		TestNullValuesBigDecimalArrayConversion nvbdc_arr;
		TestNullValuesStringArrayConversion nvsc_arr;

		json_struct = new JSONObject();
		json_struct.put( "b1", JSONString.String( "true" ) );

		try {
			bc1 = json_om.toObject( json_struct, TestBoolean1Conversion.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " may required converters!" ) );
		}

		try {
			bc1 = new TestBoolean1Conversion();
			bc1.b1 = true;
			json_om.toJSON( bc1 );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " may required converters!" ) );
		}

		/*
		 * Null converter.
		 */

		converters = new JSONConverterAbstract[ JSONObjectMapper.converterIds ];
		converters[ JSONObjectMapper.converterNameIdMap.get( "tc" ) ] = new NullObjectConverter();

		/*
		 * Null.
		 */

		try {
			json_struct = new JSONObject();
			json_struct.put( "b1", JSONString.String( "true" ) );
			bc1 = json_om.toObject( json_struct, TestBoolean1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "i1", JSONString.String( "42" ) );
			ic1 = json_om.toObject( json_struct, TestInteger1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "l1", JSONString.String( "12345678901234" ) );
			lc1 = json_om.toObject( json_struct, TestLong1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "f1", JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc1 = json_om.toObject( json_struct, TestFloat1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "d1", JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc1 = json_om.toObject( json_struct, TestDouble1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "b2", JSONString.String( "true" ) );
			bc2 = json_om.toObject( json_struct, TestBoolean2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "i2", JSONString.String( "42" ) );
			ic2 = json_om.toObject( json_struct, TestInteger2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "l2", JSONString.String( "12345678901234" ) );
			lc2 = json_om.toObject( json_struct, TestLong2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "f2", JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc2 = json_om.toObject( json_struct, TestFloat2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "d2", JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc2 = json_om.toObject( json_struct, TestDouble2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "bi", JSONString.String( "12345678901234567890" ) );
			bic = json_om.toObject( json_struct, TestBigIntegerConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "bd", JSONString.String( "0.12345678901234567890" ) );
			bdc = json_om.toObject( json_struct, TestBigDecimalConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "s", JSONString.String( "streng" ) );
			sc = json_om.toObject( json_struct, TestStringConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "b", JSONString.String( "bytes" ) );
			bac = json_om.toObject( json_struct, TestBytesConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}

		/*
		 * Null array values.
		 */

		JSONArray json_array;

		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "b1_arr" );
			json_array.add( JSONString.String( "true" ) );
			bc1_arr = json_om.toObject( json_struct, TestBooleanArray1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "i1_arr" );
			json_array.add( JSONString.String( "42" ) );
			ic1_arr = json_om.toObject( json_struct, TestIntegerArray1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "l1_arr" );
			json_array.add( JSONString.String( "12345678901234" ) );
			lc1_arr = json_om.toObject( json_struct, TestLongArray1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "f1_arr" );
			json_array.add( JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc1_arr = json_om.toObject( json_struct, TestFloatArray1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "d1_arr" );
			json_array.add( JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc1_arr = json_om.toObject( json_struct, TestDoubleArray1Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "b2_arr" );
			json_array.add( JSONString.String( "true" ) );
			bc2_arr = json_om.toObject( json_struct, TestBooleanArray2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "i2_arr" );
			json_array.add( JSONString.String( "42" ) );
			ic2_arr = json_om.toObject( json_struct, TestIntegerArray2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "l2_arr" );
			json_array.add( JSONString.String( "12345678901234" ) );
			lc2_arr = json_om.toObject( json_struct, TestLongArray2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "f2_arr" );
			json_array.add( JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc2_arr = json_om.toObject( json_struct, TestFloatArray2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "d2_arr" );
			json_array.add( JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc2_arr = json_om.toObject( json_struct, TestDoubleArray2Conversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bi_arr" );
			json_array.add( JSONString.String( "12345678901234567890" ) );
			bic_arr = json_om.toObject( json_struct, TestBigIntegerArrayConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bd_arr" );
			json_array.add( JSONString.String( "0.12345678901234567890" ) );
			bdc_arr = json_om.toObject( json_struct, TestBigDecimalArrayConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "s_arr" );
			json_array.add( JSONString.String( "streng" ) );
			sc_arr = json_om.toObject( json_struct, TestStringArrayConversion.class, converters );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}

		/*
		 * Nullable.
		 */

		try {
			json_struct = new JSONObject();
			json_struct.put( "b2", JSONString.String( "true" ) );
			nbc2 = json_om.toObject( json_struct, TestNullableBoolean2Conversion.class, converters );
			Assert.assertNotNull( nbc2 );
			Assert.assertNull( nbc2.b2 );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "i2", JSONString.String( "42" ) );
			nic2 = json_om.toObject( json_struct, TestNullableInteger2Conversion.class, converters );
			Assert.assertNotNull( nic2 );
			Assert.assertNull( nic2.i2 );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "l2", JSONString.String( "12345678901234" ) );
			nlc2 = json_om.toObject( json_struct, TestNullableLong2Conversion.class, converters );
			Assert.assertNotNull( nlc2 );
			Assert.assertNull( nlc2.l2 );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "f2", JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			nfc2 = json_om.toObject( json_struct, TestNullableFloat2Conversion.class, converters );
			Assert.assertNotNull( nfc2 );
			Assert.assertNull( nfc2.f2 );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "d2", JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			ndc2 = json_om.toObject( json_struct, TestNullableDouble2Conversion.class, converters );
			Assert.assertNotNull( ndc2 );
			Assert.assertNull( ndc2.d2 );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "bi", JSONString.String( "12345678901234567890" ) );
			nbic = json_om.toObject( json_struct, TestNullableBigIntegerConversion.class, converters );
			Assert.assertNotNull( nbic );
			Assert.assertNull( nbic.bi );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "bd", JSONString.String( "0.12345678901234567890" ) );
			nbdc = json_om.toObject( json_struct, TestNullableBigDecimalConversion.class, converters );
			Assert.assertNotNull( nbdc );
			Assert.assertNull( nbdc.bd );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "s", JSONString.String( "streng" ) );
			nsc = json_om.toObject( json_struct, TestNullableStringConversion.class, converters );
			Assert.assertNotNull( nsc );
			Assert.assertNull( nsc.s );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_struct.put( "b", JSONString.String( "bytes" ) );
			nbac = json_om.toObject( json_struct, TestNullableBytesConversion.class, converters );
			Assert.assertNotNull( nbac );
			Assert.assertNull( nbac.b );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}

		/*
		 * NullValues array.
		 */

		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "b2_arr" );
			json_array.add( JSONString.String( "true" ) );
			nvbc2_arr = json_om.toObject( json_struct, TestNullValuesBooleanArray2Conversion.class, converters );
			Assert.assertNotNull( nvbc2_arr );
			Assert.assertNotNull( nvbc2_arr.b2_arr );
			Assert.assertEquals( 1, nvbc2_arr.b2_arr.length );
			Assert.assertNull( nvbc2_arr.b2_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "i2_arr" );
			json_array.add( JSONString.String( "42" ) );
			nvic2_arr = json_om.toObject( json_struct, TestNullValuesIntegerArray2Conversion.class, converters );
			Assert.assertNotNull( nvic2_arr );
			Assert.assertNotNull( nvic2_arr.i2_arr );
			Assert.assertEquals( 1, nvic2_arr.i2_arr.length );
			Assert.assertNull( nvic2_arr.i2_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "l2_arr" );
			json_array.add( JSONString.String( "12345678901234" ) );
			nvlc2_arr = json_om.toObject( json_struct, TestNullValuesLongArray2Conversion.class, converters );
			Assert.assertNotNull( nvlc2_arr );
			Assert.assertNotNull( nvlc2_arr.l2_arr );
			Assert.assertEquals( 1, nvlc2_arr.l2_arr.length );
			Assert.assertNull( nvlc2_arr.l2_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "f2_arr" );
			json_array.add( JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			nvfc2_arr = json_om.toObject( json_struct, TestNullValuesFloatArray2Conversion.class, converters );
			Assert.assertNotNull( nvfc2_arr );
			Assert.assertNotNull( nvfc2_arr.f2_arr );
			Assert.assertEquals( 1, nvfc2_arr.f2_arr.length );
			Assert.assertNull( nvfc2_arr.f2_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "d2_arr" );
			json_array.add( JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			nvdc2_arr = json_om.toObject( json_struct, TestNullValuesDoubleArray2Conversion.class, converters );
			Assert.assertNotNull( nvdc2_arr );
			Assert.assertNotNull( nvdc2_arr.d2_arr );
			Assert.assertEquals( 1, nvdc2_arr.d2_arr.length );
			Assert.assertNull( nvdc2_arr.d2_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bi_arr" );
			json_array.add( JSONString.String( "12345678901234567890" ) );
			nvbic_arr = json_om.toObject( json_struct, TestNullValuesBigIntegerArrayConversion.class, converters );
			Assert.assertNotNull( nvbic_arr );
			Assert.assertNotNull( nvbic_arr.bi_arr );
			Assert.assertEquals( 1, nvbic_arr.bi_arr.length );
			Assert.assertNull( nvbic_arr.bi_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bd_arr" );
			json_array.add( JSONString.String( "0.12345678901234567890" ) );
			nvbdc_arr = json_om.toObject( json_struct, TestNullValuesBigDecimalArrayConversion.class, converters );
			Assert.assertNotNull( nvbdc_arr );
			Assert.assertNotNull( nvbdc_arr.bd_arr );
			Assert.assertEquals( 1, nvbdc_arr.bd_arr.length );
			Assert.assertNull( nvbdc_arr.bd_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
		try {
			json_struct = new JSONObject();
			json_array = json_struct.addArray( "s_arr" );
			json_array.add( JSONString.String( "streng" ) );
			nvsc_arr = json_om.toObject( json_struct, TestNullValuesStringArrayConversion.class, converters );
			Assert.assertNotNull( nvsc_arr );
			Assert.assertNotNull( nvsc_arr.s_arr );
			Assert.assertEquals( 1, nvsc_arr.s_arr.length );
			Assert.assertNull( nvsc_arr.s_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}

		/*
		 * String converter.
		 */

		converters = new JSONConverterAbstract[ JSONObjectMapper.converterIds ];
		converters[ JSONObjectMapper.converterNameIdMap.get( "tc" ) ] = new StringConverter();

		/*
		 * Converted.
		 */

		try {
			json_struct = new JSONObject();
			json_struct.put( "b1", JSONString.String( "true" ) );
			bc1 = json_om.toObject( json_struct, TestBoolean1Conversion.class, converters );
			Assert.assertNotNull( bc1 );
			Assert.assertTrue( bc1.b1 );

			json_struct = new JSONObject();
			json_struct.put( "i1", JSONString.String( "42" ) );
			ic1 = json_om.toObject( json_struct, TestInteger1Conversion.class, converters );
			Assert.assertNotNull( ic1 );
			Assert.assertEquals( 42, ic1.i1 );

			json_struct = new JSONObject();
			json_struct.put( "l1", JSONString.String( "12345678901234" ) );
			lc1 = json_om.toObject( json_struct, TestLong1Conversion.class, converters );
			Assert.assertNotNull( lc1 );
			Assert.assertEquals( 12345678901234L, lc1.l1 );

			json_struct = new JSONObject();
			json_struct.put( "f1", JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc1 = json_om.toObject( json_struct, TestFloat1Conversion.class, converters );
			Assert.assertNotNull( fc1 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)fc1.f1 );

			json_struct = new JSONObject();
			json_struct.put( "d1", JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc1 = json_om.toObject( json_struct, TestDouble1Conversion.class, converters );
			Assert.assertNotNull( dc1 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)dc1.d1 );

			json_struct = new JSONObject();
			json_struct.put( "b2", JSONString.String( "true" ) );
			bc2 = json_om.toObject( json_struct, TestBoolean2Conversion.class, converters );
			Assert.assertNotNull( bc2 );
			Assert.assertTrue( bc2.b2 );

			json_struct = new JSONObject();
			json_struct.put( "i2", JSONString.String( "42" ) );
			ic2 = json_om.toObject( json_struct, TestInteger2Conversion.class, converters );
			Assert.assertNotNull( ic2 );
			Assert.assertEquals( new Integer( 42 ), ic2.i2 );

			json_struct = new JSONObject();
			json_struct.put( "l2", JSONString.String( "12345678901234" ) );
			lc2 = json_om.toObject( json_struct, TestLong2Conversion.class, converters );
			Assert.assertNotNull( lc2 );
			Assert.assertEquals( new Long( 12345678901234L ), lc2.l2 );

			json_struct = new JSONObject();
			json_struct.put( "f2", JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc2 = json_om.toObject( json_struct, TestFloat2Conversion.class, converters );
			Assert.assertNotNull( fc2 );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), fc2.f2 );

			json_struct = new JSONObject();
			json_struct.put( "d2", JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc2 = json_om.toObject( json_struct, TestDouble2Conversion.class, converters );
			Assert.assertNotNull( dc2 );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), dc2.d2 );

			json_struct = new JSONObject();
			json_struct.put( "bi", JSONString.String( "12345678901234567890" ) );
			bic = json_om.toObject( json_struct, TestBigIntegerConversion.class, converters );
			Assert.assertNotNull( bic );
			Assert.assertEquals( new BigInteger( "12345678901234567890" ), bic.bi );

			json_struct = new JSONObject();
			json_struct.put( "bd", JSONString.String( "0.12345678901234567890" ) );
			bdc = json_om.toObject( json_struct, TestBigDecimalConversion.class, converters );
			Assert.assertNotNull( bdc );
			Assert.assertEquals( new BigDecimal( "0.12345678901234567890" ), bdc.bd );

			json_struct = new JSONObject();
			json_struct.put( "s", JSONString.String( "streng" ) );
			sc = json_om.toObject( json_struct, TestStringConversion.class, converters );
			Assert.assertNotNull( sc );
			Assert.assertEquals( "streng".toUpperCase(), sc.s );

			json_struct = new JSONObject();
			json_struct.put( "b", JSONString.String( "bytes" ) );
			bac = json_om.toObject( json_struct, TestBytesConversion.class, converters );
			Assert.assertNotNull( bac );
			Assert.assertArrayEquals( "bytes".toUpperCase().getBytes(), bac.b );

			/*
			 * Array.
			 */

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "b1_arr" );
			json_array.add( JSONString.String( "true" ) );
			bc1_arr = json_om.toObject( json_struct, TestBooleanArray1Conversion.class, converters );
			Assert.assertNotNull( bc1_arr );
			Assert.assertEquals( 1, bc1_arr.b1_arr.length );
			Assert.assertTrue( bc1_arr.b1_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "i1_arr" );
			json_array.add( JSONString.String( "42" ) );
			ic1_arr = json_om.toObject( json_struct, TestIntegerArray1Conversion.class, converters );
			Assert.assertNotNull( ic1_arr );
			Assert.assertEquals( 1, ic1_arr.i1_arr.length );
			Assert.assertEquals( 42, ic1_arr.i1_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "l1_arr" );
			json_array.add( JSONString.String( "12345678901234" ) );
			lc1_arr = json_om.toObject( json_struct, TestLongArray1Conversion.class, converters );
			Assert.assertNotNull( lc1_arr );
			Assert.assertEquals( 1, lc1_arr.l1_arr.length );
			Assert.assertEquals( 12345678901234L, lc1_arr.l1_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "f1_arr" );
			json_array.add( JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc1_arr = json_om.toObject( json_struct, TestFloatArray1Conversion.class, converters );
			Assert.assertNotNull( fc1_arr );
			Assert.assertEquals( 1, fc1_arr.f1_arr.length );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), (Float)fc1_arr.f1_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "d1_arr" );
			json_array.add( JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc1_arr = json_om.toObject( json_struct, TestDoubleArray1Conversion.class, converters );
			Assert.assertNotNull( dc1_arr );
			Assert.assertEquals( 1, dc1_arr.d1_arr.length );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), (Double)dc1_arr.d1_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "b2_arr" );
			json_array.add( JSONString.String( "true" ) );
			bc2_arr = json_om.toObject( json_struct, TestBooleanArray2Conversion.class, converters );
			Assert.assertNotNull( bc2_arr );
			Assert.assertEquals( 1, bc2_arr.b2_arr.length );
			Assert.assertTrue( bc2_arr.b2_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "i2_arr" );
			json_array.add( JSONString.String( "42" ) );
			ic2_arr = json_om.toObject( json_struct, TestIntegerArray2Conversion.class, converters );
			Assert.assertNotNull( ic2_arr );
			Assert.assertEquals( 1, ic2_arr.i2_arr.length );
			Assert.assertEquals( new Integer( 42 ), ic2_arr.i2_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "l2_arr" );
			json_array.add( JSONString.String( "12345678901234" ) );
			lc2_arr = json_om.toObject( json_struct, TestLongArray2Conversion.class, converters );
			Assert.assertNotNull( lc2_arr );
			Assert.assertEquals( 1, lc2_arr.l2_arr.length );
			Assert.assertEquals( new Long( 12345678901234L ), lc2_arr.l2_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "f2_arr" );
			json_array.add( JSONString.String( new Float( 1.0F / 3.0F ).toString() ) );
			fc2_arr = json_om.toObject( json_struct, TestFloatArray2Conversion.class, converters );
			Assert.assertNotNull( fc2_arr );
			Assert.assertEquals( 1, fc2_arr.f2_arr.length );
			Assert.assertEquals( new Float( 1.0F / 3.0F ), fc2_arr.f2_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "d2_arr" );
			json_array.add( JSONString.String( new Double( 1.0 / 3.0 ).toString() ) );
			dc2_arr = json_om.toObject( json_struct, TestDoubleArray2Conversion.class, converters );
			Assert.assertNotNull( dc2_arr );
			Assert.assertEquals( 1, dc2_arr.d2_arr.length );
			Assert.assertEquals( new Double( 1.0 / 3.0 ), dc2_arr.d2_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bi_arr" );
			json_array.add( JSONString.String( "12345678901234567890" ) );
			bic_arr = json_om.toObject( json_struct, TestBigIntegerArrayConversion.class, converters );
			Assert.assertNotNull( bic_arr );
			Assert.assertEquals( 1, bic_arr.bi_arr.length );
			Assert.assertEquals( new BigInteger( "12345678901234567890" ), bic_arr.bi_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "bd_arr" );
			json_array.add( JSONString.String( "0.12345678901234567890" ) );
			bdc_arr = json_om.toObject( json_struct, TestBigDecimalArrayConversion.class, converters );
			Assert.assertNotNull( bdc_arr );
			Assert.assertEquals( 1, bdc_arr.bd_arr.length );
			Assert.assertEquals( new BigDecimal( "0.12345678901234567890" ), bdc_arr.bd_arr[ 0 ] );

			json_struct = new JSONObject();
			json_array = json_struct.addArray( "s_arr" );
			json_array.add( JSONString.String( "streng" ) );
			sc_arr = json_om.toObject( json_struct, TestStringArrayConversion.class, converters );
			Assert.assertNotNull( sc_arr );
			Assert.assertEquals( 1, sc_arr.s_arr.length );
			Assert.assertEquals( "streng".toUpperCase(), sc_arr.s_arr[ 0 ] );
		}
		catch (JSONException e) {
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class TestBoolean1Conversion {
		@JSONConverter(name="tc")
		public boolean b1;
	}
	public static class TestInteger1Conversion {
		@JSONConverter(name="tc")
		public int i1;
	}
	public static class TestLong1Conversion {
		@JSONConverter(name="tc")
		public long l1;
	}
	public static class TestFloat1Conversion {
		@JSONConverter(name="tc")
		public float f1;
	}
	public static class TestDouble1Conversion {
		@JSONConverter(name="tc")
		public double d1;
	}
	public static class TestBoolean2Conversion {
		@JSONConverter(name="tc")
		public Boolean b2;
	}
	public static class TestInteger2Conversion {
		@JSONConverter(name="tc")
		public Integer i2;
	}
	public static class TestLong2Conversion {
		@JSONConverter(name="tc")
		public Long l2;
	}
	public static class TestFloat2Conversion {
		@JSONConverter(name="tc")
		public Float f2;
	}
	public static class TestDouble2Conversion {
		@JSONConverter(name="tc")
		public Double d2;
	}
	public static class TestBigIntegerConversion {
		@JSONConverter(name="tc")
		public BigInteger bi;
	}
	public static class TestBigDecimalConversion {
		@JSONConverter(name="tc")
		public BigDecimal bd;
	}
	public static class TestStringConversion {
		@JSONConverter(name="tc")
		public String s;
	}
	public static class TestBytesConversion {
		@JSONConverter(name="tc")
		public byte[] b;
	}

	public static class TestBooleanArray1Conversion {
		@JSONConverter(name="tc")
		public boolean[] b1_arr;
	}
	public static class TestIntegerArray1Conversion {
		@JSONConverter(name="tc")
		public int[] i1_arr;
	}
	public static class TestLongArray1Conversion {
		@JSONConverter(name="tc")
		public long[] l1_arr;
	}
	public static class TestFloatArray1Conversion {
		@JSONConverter(name="tc")
		public float[] f1_arr;
	}
	public static class TestDoubleArray1Conversion {
		@JSONConverter(name="tc")
		public double[] d1_arr;
	}
	public static class TestBooleanArray2Conversion {
		@JSONConverter(name="tc")
		public Boolean[] b2_arr;
	}
	public static class TestIntegerArray2Conversion {
		@JSONConverter(name="tc")
		public Integer[] i2_arr;
	}
	public static class TestLongArray2Conversion {
		@JSONConverter(name="tc")
		public Long[] l2_arr;
	}
	public static class TestFloatArray2Conversion {
		@JSONConverter(name="tc")
		public Float[] f2_arr;
	}
	public static class TestDoubleArray2Conversion {
		@JSONConverter(name="tc")
		public Double[] d2_arr;
	}
	public static class TestBigIntegerArrayConversion {
		@JSONConverter(name="tc")
		public BigInteger[] bi_arr;
	}
	public static class TestBigDecimalArrayConversion {
		@JSONConverter(name="tc")
		public BigDecimal[] bd_arr;
	}
	public static class TestStringArrayConversion {
		@JSONConverter(name="tc")
		public String[] s_arr;
	}

	public static class TestNullableBoolean2Conversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public Boolean b2;
	}
	public static class TestNullableInteger2Conversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public Integer i2;
	}
	public static class TestNullableLong2Conversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public Long l2;
	}
	public static class TestNullableFloat2Conversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public Float f2;
	}
	public static class TestNullableDouble2Conversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public Double d2;
	}
	public static class TestNullableBigIntegerConversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public BigInteger bi;
	}
	public static class TestNullableBigDecimalConversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public BigDecimal bd;
	}
	public static class TestNullableStringConversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public String s;
	}
	public static class TestNullableBytesConversion {
		@JSONNullable
		@JSONConverter(name="tc")
		public byte[] b;
	}

	public static class TestNullValuesBooleanArray2Conversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public Boolean[] b2_arr;
	}
	public static class TestNullValuesIntegerArray2Conversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public Integer[] i2_arr;
	}
	public static class TestNullValuesLongArray2Conversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public Long[] l2_arr;
	}
	public static class TestNullValuesFloatArray2Conversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public Float[] f2_arr;
	}
	public static class TestNullValuesDoubleArray2Conversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public Double[] d2_arr;
	}
	public static class TestNullValuesBigIntegerArrayConversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public BigInteger[] bi_arr;
	}
	public static class TestNullValuesBigDecimalArrayConversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public BigDecimal[] bd_arr;
	}
	public static class TestNullValuesStringArrayConversion {
		@JSONNullValues
		@JSONConverter(name="tc")
		public String[] s_arr;
	}

	public static class NullObjectConverter extends JSONConverterAbstract {
		@Override
		public Boolean getBoolean(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public Integer getInteger(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public Long getLong(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public Float getFloat(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public Double getDouble(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public BigInteger getBigInteger(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public BigDecimal getBigDecimal(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public String getString(String fieldName, JSONValue json_value) {
			return null;
		}
		@Override
		public byte[] getBytes(String fieldName, JSONValue json_value) {
			return null;
		}
	}

	public static class StringConverter extends JSONConverterAbstract {
		@Override
		public Boolean getBoolean(String fieldName, JSONValue json_value) {
			String str = json_value.getString();
			return "true".equalsIgnoreCase(str) ? true : false;
		}
		@Override
		public Integer getInteger(String fieldName, JSONValue json_value) {
			return Integer.parseInt( json_value.getString() );
		}
		@Override
		public Long getLong(String fieldName, JSONValue json_value) {
			return Long.parseLong( json_value.getString() );
		}
		@Override
		public Float getFloat(String fieldName, JSONValue json_value) {
			return Float.parseFloat( json_value.getString() );
		}
		@Override
		public Double getDouble(String fieldName, JSONValue json_value) {
			return Double.parseDouble( json_value.getString() );
		}
		@Override
		public BigInteger getBigInteger(String fieldName, JSONValue json_value) {
			return new BigInteger( json_value.getString() );
		}
		@Override
		public BigDecimal getBigDecimal(String fieldName, JSONValue json_value) {
			return new BigDecimal( json_value.getString() );
		}
		@Override
		public String getString(String fieldName, JSONValue json_value) {
			return json_value.getString().toUpperCase();
		}
		@Override
		public byte[] getBytes(String fieldName, JSONValue json_value) {
			return json_value.getString().toUpperCase().getBytes();
		}
	}

	/*
	public static class TestTypesClass {

		@JSONConverter(name="tc")
		private boolean b1;

		@JSONConverter(name="tc")
		private int i1;

		@JSONConverter(name="tc")
		private long l1;

		@JSONConverter(name="tc")
		private float f1;

		@JSONConverter(name="tc")
		private double d1;

		@JSONNullable
		private Boolean b2;

		@JSONNullable
		private Integer i2;

		@JSONNullable
		private Long l2;

		@JSONNullable
		private Float f2;

		@JSONNullable
		private Double d2;

		@JSONNullable
		private BigInteger bi;

		@JSONNullable
		private BigDecimal bd;

		@JSONNullable
		private String s;

		@JSONNullable
		private byte[] b;

	}
	*/

	@Test
	public void test_jsonobjectmapper_converter_tojson() {
		JSONConverterAbstract[] converters;
		JSONObjectMapper json_om = new JSONObjectMapper();

		try {
			json_om.register( TestBoolean1Conversion.class );
			json_om.register( TestInteger1Conversion.class );
			json_om.register( TestLong1Conversion.class );
			json_om.register( TestFloat1Conversion.class );
			json_om.register( TestDouble1Conversion.class );
			json_om.register( TestBoolean2Conversion.class );
			json_om.register( TestInteger2Conversion.class );
			json_om.register( TestLong2Conversion.class );
			json_om.register( TestFloat2Conversion.class );
			json_om.register( TestDouble2Conversion.class );
			json_om.register( TestBigIntegerConversion.class );
			json_om.register( TestBigDecimalConversion.class );
			json_om.register( TestStringConversion.class );
			json_om.register( TestBytesConversion.class );

			json_om.register( TestBooleanArray1Conversion.class );
			json_om.register( TestIntegerArray1Conversion.class );
			json_om.register( TestLongArray1Conversion.class );
			json_om.register( TestFloatArray1Conversion.class );
			json_om.register( TestDoubleArray1Conversion.class );
			json_om.register( TestBooleanArray2Conversion.class );
			json_om.register( TestIntegerArray2Conversion.class );
			json_om.register( TestLongArray2Conversion.class );
			json_om.register( TestFloatArray2Conversion.class );
			json_om.register( TestDoubleArray2Conversion.class );
			json_om.register( TestBigIntegerArrayConversion.class );
			json_om.register( TestBigDecimalArrayConversion.class );
			json_om.register( TestStringArrayConversion.class );

			json_om.register( TestNullableBoolean2Conversion.class );
			json_om.register( TestNullableInteger2Conversion.class );
			json_om.register( TestNullableLong2Conversion.class );
			json_om.register( TestNullableFloat2Conversion.class );
			json_om.register( TestNullableDouble2Conversion.class );
			json_om.register( TestNullableBigIntegerConversion.class );
			json_om.register( TestNullableBigDecimalConversion.class );
			json_om.register( TestNullableStringConversion.class );
			json_om.register( TestNullableBytesConversion.class );

			json_om.register( TestNullValuesBooleanArray2Conversion.class );
			json_om.register( TestNullValuesIntegerArray2Conversion.class );
			json_om.register( TestNullValuesLongArray2Conversion.class );
			json_om.register( TestNullValuesFloatArray2Conversion.class );
			json_om.register( TestNullValuesDoubleArray2Conversion.class );
			json_om.register( TestNullValuesBigIntegerArrayConversion.class );
			json_om.register( TestNullValuesBigDecimalArrayConversion.class );
			json_om.register( TestNullValuesStringArrayConversion.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		TestBoolean1Conversion bc1;
		TestInteger1Conversion ic1;
		TestLong1Conversion lc1;
		TestFloat1Conversion fc1;
		TestDouble1Conversion dc1;
		TestBoolean2Conversion bc2;
		TestInteger2Conversion ic2;
		TestLong2Conversion lc2;
		TestFloat2Conversion fc2;
		TestDouble2Conversion dc2;
		TestBigIntegerConversion bic;
		TestBigDecimalConversion bdc;
		TestStringConversion sc;
		TestBytesConversion bac;

		TestBooleanArray1Conversion bc1_arr;
		TestIntegerArray1Conversion ic1_arr;
		TestLongArray1Conversion lc1_arr;
		TestFloatArray1Conversion fc1_arr;
		TestDoubleArray1Conversion dc1_arr;
		TestBooleanArray2Conversion bc2_arr;
		TestIntegerArray2Conversion ic2_arr;
		TestLongArray2Conversion lc2_arr;
		TestFloatArray2Conversion fc2_arr;
		TestDoubleArray2Conversion dc2_arr;
		TestBigIntegerArrayConversion bic_arr;
		TestBigDecimalArrayConversion bdc_arr;
		TestStringArrayConversion sc_arr;

		TestNullableBoolean2Conversion nbc2;
		TestNullableInteger2Conversion nic2;
		TestNullableLong2Conversion nlc2;
		TestNullableFloat2Conversion nfc2;
		TestNullableDouble2Conversion ndc2;
		TestNullableBigIntegerConversion nbic;
		TestNullableBigDecimalConversion nbdc;
		TestNullableStringConversion nsc;
		TestNullableBytesConversion nbac;

		TestNullValuesBooleanArray2Conversion nvbc2_arr;
		TestNullValuesIntegerArray2Conversion nvic2_arr;
		TestNullValuesLongArray2Conversion nvlc2_arr;
		TestNullValuesFloatArray2Conversion nvfc2_arr;
		TestNullValuesDoubleArray2Conversion nvdc2_arr;
		TestNullValuesBigIntegerArrayConversion nvbic_arr;
		TestNullValuesBigDecimalArrayConversion nvbdc_arr;
		TestNullValuesStringArrayConversion nvsc_arr;

		/*
		 * Null converter.
		 */

		converters = new JSONConverterAbstract[ JSONObjectMapper.converterIds ];
		converters[ JSONObjectMapper.converterNameIdMap.get( "tc" ) ] = new NullJsonConverter();

		/*
		 * Null.
		 */

		try {
			bc1 = new TestBoolean1Conversion();
			bc1.b1 = true;
			json_om.toJSON( bc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			ic1 = new TestInteger1Conversion();
			ic1.i1 = 42;
			json_om.toJSON( ic1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			lc1 = new TestLong1Conversion();
			lc1.l1 = 12345678901234L;
			json_om.toJSON( lc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			fc1 = new TestFloat1Conversion();
			fc1.f1 = 1.0F / 3.0F;
			json_om.toJSON( fc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			dc1 = new TestDouble1Conversion();
			dc1.d1 = 1.0 / 3.0;
			json_om.toJSON( dc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			bc2 = new TestBoolean2Conversion();
			bc2.b2 = true;
			json_om.toJSON( bc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			ic2 = new TestInteger2Conversion();
			ic2.i2 = 42;
			json_om.toJSON( ic2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			lc2 = new TestLong2Conversion();
			lc2.l2 = 12345678901234L;
			json_om.toJSON( lc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			fc2 = new TestFloat2Conversion();
			fc2.f2 = 1.0F / 3.0F;
			json_om.toJSON( fc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			dc2 = new TestDouble2Conversion();
			dc2.d2 = 1.0 / 3.0;
			json_om.toJSON( dc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bic = new TestBigIntegerConversion();
			bic.bi = new BigInteger( "12345678901234567890" );
			json_om.toJSON( bic, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bdc = new TestBigDecimalConversion();
			bdc.bd = new BigDecimal( "0.12345678901234567890" );
			json_om.toJSON( bdc, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			sc = new TestStringConversion();
			sc.s = "streng";
			json_om.toJSON( sc, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bac = new TestBytesConversion();
			bac.b = "bytes".getBytes();
			json_om.toJSON( bac, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}

		/*
		 * Null array values.
		 */

		try {
			bc1_arr = new TestBooleanArray1Conversion();
			bc1_arr.b1_arr = new boolean[] { true };
			json_om.toJSON( bc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			ic1_arr = new TestIntegerArray1Conversion();
			ic1_arr.i1_arr = new int[] { 42 };
			json_om.toJSON( ic1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			lc1_arr = new TestLongArray1Conversion();
			lc1_arr.l1_arr = new long[] { 12345678901234L };
			json_om.toJSON( lc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			fc1_arr = new TestFloatArray1Conversion();
			fc1_arr.f1_arr = new float[] { 1.0F / 3.0F };
			json_om.toJSON( fc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			dc1_arr = new TestDoubleArray1Conversion();
			dc1_arr.d1_arr = new double[] { 1.0 / 3.0 };
			json_om.toJSON( dc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			bc2_arr = new TestBooleanArray2Conversion();
			bc2_arr.b2_arr = new Boolean[] { true };
			json_om.toJSON( bc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			ic2_arr = new TestIntegerArray2Conversion();
			ic2_arr.i2_arr = new Integer[] { 42 };
			json_om.toJSON( ic2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			lc2_arr = new TestLongArray2Conversion();
			lc2_arr.l2_arr = new Long[] { 12345678901234L };
			json_om.toJSON( lc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			fc2_arr = new TestFloatArray2Conversion();
			fc2_arr.f2_arr = new Float[] { 1.0F / 3.0F };
			json_om.toJSON( fc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			dc2_arr = new TestDoubleArray2Conversion();
			dc2_arr.d2_arr = new Double[] { 1.0 / 3.0 };
			json_om.toJSON( dc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			bic_arr = new TestBigIntegerArrayConversion();
			bic_arr.bi_arr = new BigInteger[] { new BigInteger( "12345678901234567890" ) };
			json_om.toJSON( bic_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			bdc_arr = new TestBigDecimalArrayConversion();
			bdc_arr.bd_arr = new BigDecimal[] { new BigDecimal( "0.12345678901234567890" ) };
			json_om.toJSON( bdc_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			sc_arr = new TestStringArrayConversion();
			sc_arr.s_arr = new String[] { "streng" };
			json_om.toJSON( sc_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}

		/*
		 * JSONNull converter.
		 */

		converters = new JSONConverterAbstract[ JSONObjectMapper.converterIds ];
		converters[ JSONObjectMapper.converterNameIdMap.get( "tc" ) ] = new JSONNullJsonConverter();

		/*
		 * JSONNull.
		 */

		try {
			bc1 = new TestBoolean1Conversion();
			bc1.b1 = true;
			json_om.toJSON( bc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			ic1 = new TestInteger1Conversion();
			ic1.i1 = 42;
			json_om.toJSON( ic1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			lc1 = new TestLong1Conversion();
			lc1.l1 = 12345678901234L;
			json_om.toJSON( lc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			fc1 = new TestFloat1Conversion();
			fc1.f1 = 1.0F / 3.0F;
			json_om.toJSON( fc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			dc1 = new TestDouble1Conversion();
			dc1.d1 = 1.0 / 3.0;
			json_om.toJSON( dc1, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not be null." ) );
		}
		try {
			bc2 = new TestBoolean2Conversion();
			bc2.b2 = true;
			json_om.toJSON( bc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			ic2 = new TestInteger2Conversion();
			ic2.i2 = 42;
			json_om.toJSON( ic2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			lc2 = new TestLong2Conversion();
			lc2.l2 = 12345678901234L;
			json_om.toJSON( lc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			fc2 = new TestFloat2Conversion();
			fc2.f2 = 1.0F / 3.0F;
			json_om.toJSON( fc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			dc2 = new TestDouble2Conversion();
			dc2.d2 = 1.0 / 3.0;
			json_om.toJSON( dc2, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bic = new TestBigIntegerConversion();
			bic.bi = new BigInteger( "12345678901234567890" );
			json_om.toJSON( bic, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bdc = new TestBigDecimalConversion();
			bdc.bd = new BigDecimal( "0.12345678901234567890" );
			json_om.toJSON( bdc, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			sc = new TestStringConversion();
			sc.s = "streng";
			json_om.toJSON( sc, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}
		try {
			bac = new TestBytesConversion();
			bac.b = "bytes".getBytes();
			json_om.toJSON( bac, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is not nullable." ) );
		}

		/*
		 * JSONNull array values.
		 */

		try {
			bc1_arr = new TestBooleanArray1Conversion();
			bc1_arr.b1_arr = new boolean[] { true };
			json_om.toJSON( bc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			ic1_arr = new TestIntegerArray1Conversion();
			ic1_arr.i1_arr = new int[] { 42 };
			json_om.toJSON( ic1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			lc1_arr = new TestLongArray1Conversion();
			lc1_arr.l1_arr = new long[] { 12345678901234L };
			json_om.toJSON( lc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			fc1_arr = new TestFloatArray1Conversion();
			fc1_arr.f1_arr = new float[] { 1.0F / 3.0F };
			json_om.toJSON( fc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			dc1_arr = new TestDoubleArray1Conversion();
			dc1_arr.d1_arr = new double[] { 1.0 / 3.0 };
			json_om.toJSON( dc1_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " is primitive and can not have null values." ) );
		}
		try {
			bc2_arr = new TestBooleanArray2Conversion();
			bc2_arr.b2_arr = new Boolean[] { true };
			json_om.toJSON( bc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			ic2_arr = new TestIntegerArray2Conversion();
			ic2_arr.i2_arr = new Integer[] { 42 };
			json_om.toJSON( ic2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			lc2_arr = new TestLongArray2Conversion();
			lc2_arr.l2_arr = new Long[] { 12345678901234L };
			json_om.toJSON( lc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			fc2_arr = new TestFloatArray2Conversion();
			fc2_arr.f2_arr = new Float[] { 1.0F / 3.0F };
			json_om.toJSON( fc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			dc2_arr = new TestDoubleArray2Conversion();
			dc2_arr.d2_arr = new Double[] { 1.0 / 3.0 };
			json_om.toJSON( dc2_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			bic_arr = new TestBigIntegerArrayConversion();
			bic_arr.bi_arr = new BigInteger[] { new BigInteger( "12345678901234567890" ) };
			json_om.toJSON( bic_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			bdc_arr = new TestBigDecimalArrayConversion();
			bdc_arr.bd_arr = new BigDecimal[] { new BigDecimal( "0.12345678901234567890" ) };
			json_om.toJSON( bdc_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}
		try {
			sc_arr = new TestStringArrayConversion();
			sc_arr.s_arr = new String[] { "streng" };
			json_om.toJSON( sc_arr, converters );
		}
		catch (JSONException e) {
			Assert.assertTrue( e.getMessage().endsWith( " does not allow null values." ) );
		}

		/*
		 * JSONString Converter.
		 */

		converters = new JSONConverterAbstract[ JSONObjectMapper.converterIds ];
		converters[ JSONObjectMapper.converterNameIdMap.get( "tc" ) ] = new JSONValueJsonConverter();

		JSONStructure json_struct;
		JSONObject json_object;
		JSONValue json_value;
		JSONArray json_array;

		try {
			bc1 = new TestBoolean1Conversion();
			bc1.b1 = true;
			json_struct = json_om.toJSON( bc1, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "b1" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "true", json_value.getString() );

			ic1 = new TestInteger1Conversion();
			ic1.i1 = 42;
			json_struct = json_om.toJSON( ic1, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "i1" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "42", json_value.getString() );

			lc1 = new TestLong1Conversion();
			lc1.l1 = 12345678901234L;
			json_struct = json_om.toJSON( lc1, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "l1" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "12345678901234", json_value.getString() );

			fc1 = new TestFloat1Conversion();
			fc1.f1 = 1.0F / 3.0F;
			json_struct = json_om.toJSON( fc1, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "f1" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( new Float( 1.0F / 3.0F ).toString(), json_value.getString() );

			dc1 = new TestDouble1Conversion();
			dc1.d1 = 1.0 / 3.0;
			json_struct = json_om.toJSON( dc1, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "d1" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( new Double( 1.0 / 3.0 ).toString(), json_value.getString() );

			bc2 = new TestBoolean2Conversion();
			bc2.b2 = true;
			json_struct = json_om.toJSON( bc2, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "b2" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "true", json_value.getString() );

			ic2 = new TestInteger2Conversion();
			ic2.i2 = 42;
			json_struct = json_om.toJSON( ic2, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "i2" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "42", json_value.getString() );

			lc2 = new TestLong2Conversion();
			lc2.l2 = 12345678901234L;
			json_struct = json_om.toJSON( lc2, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "l2" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "12345678901234", json_value.getString() );

			fc2 = new TestFloat2Conversion();
			fc2.f2 = 1.0F / 3.0F;
			json_struct = json_om.toJSON( fc2, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "f2" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( new Float( 1.0F / 3.0F ).toString(), json_value.getString() );

			dc2 = new TestDouble2Conversion();
			dc2.d2 = 1.0 / 3.0;
			json_struct = json_om.toJSON( dc2, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "d2" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( new Double( 1.0 / 3.0 ).toString(), json_value.getString() );

			bic = new TestBigIntegerConversion();
			bic.bi = new BigInteger( "12345678901234567890" );
			json_struct = json_om.toJSON( bic, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "bi" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "12345678901234567890", json_value.getString() );

			bdc = new TestBigDecimalConversion();
			bdc.bd = new BigDecimal( "0.12345678901234567890" );
			json_struct = json_om.toJSON( bdc, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "bd" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "0.12345678901234567890", json_value.getString() );

			sc = new TestStringConversion();
			sc.s = "streng";
			json_struct = json_om.toJSON( sc, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "s" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "STRENG", json_value.getString() );

			bac = new TestBytesConversion();
			bac.b = "bytes".getBytes();
			json_struct = json_om.toJSON( bac, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "b" );
			Assert.assertNotNull( json_value );
			Assert.assertEquals( "BYTES", json_value.getString() );

			/*
			 * Array.
			 */

			bc1_arr = new TestBooleanArray1Conversion();
			bc1_arr.b1_arr = new boolean[] { true };
			json_struct = json_om.toJSON( bc1_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "b1_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "true", json_array.get( 0 ).getString() );

			ic1_arr = new TestIntegerArray1Conversion();
			ic1_arr.i1_arr = new int[] { 42 };
			json_struct = json_om.toJSON( ic1_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "i1_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "42", json_array.get( 0 ).getString() );

			lc1_arr = new TestLongArray1Conversion();
			lc1_arr.l1_arr = new long[] { 12345678901234L };
			json_struct = json_om.toJSON( lc1_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "l1_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "12345678901234", json_array.get( 0 ).getString() );

			fc1_arr = new TestFloatArray1Conversion();
			fc1_arr.f1_arr = new float[] { 1.0F / 3.0F };
			json_struct = json_om.toJSON( fc1_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "f1_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( new Float( 1.0F / 3.0F ).toString(), json_array.get( 0 ).getString() );

			dc1_arr = new TestDoubleArray1Conversion();
			dc1_arr.d1_arr = new double[] { 1.0 / 3.0 };
			json_struct = json_om.toJSON( dc1_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "d1_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( new Double( 1.0 / 3.0 ).toString(), json_array.get( 0 ).getString() );

			bc2_arr = new TestBooleanArray2Conversion();
			bc2_arr.b2_arr = new Boolean[] { true };
			json_struct = json_om.toJSON( bc2_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "b2_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "true", json_array.get( 0 ).getString() );

			ic2_arr = new TestIntegerArray2Conversion();
			ic2_arr.i2_arr = new Integer[] { 42 };
			json_struct = json_om.toJSON( ic2_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "i2_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "42", json_array.get( 0 ).getString() );

			lc2_arr = new TestLongArray2Conversion();
			lc2_arr.l2_arr = new Long[] { 12345678901234L };
			json_struct = json_om.toJSON( lc2_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "l2_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "12345678901234", json_array.get( 0 ).getString() );

			fc2_arr = new TestFloatArray2Conversion();
			fc2_arr.f2_arr = new Float[] { 1.0F / 3.0F };
			json_struct = json_om.toJSON( fc2_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "f2_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( new Float( 1.0F / 3.0F ).toString(), json_array.get( 0 ).getString() );

			dc2_arr = new TestDoubleArray2Conversion();
			dc2_arr.d2_arr = new Double[] { 1.0 / 3.0 };
			json_struct = json_om.toJSON( dc2_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "d2_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( new Double( 1.0 / 3.0 ).toString(), json_array.get( 0 ).getString() );

			bic_arr = new TestBigIntegerArrayConversion();
			bic_arr.bi_arr = new BigInteger[] { new BigInteger( "12345678901234567890" ) };
			json_struct = json_om.toJSON( bic_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "bi_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "12345678901234567890", json_array.get( 0 ).getString() );

			bdc_arr = new TestBigDecimalArrayConversion();
			bdc_arr.bd_arr = new BigDecimal[] { new BigDecimal( "0.12345678901234567890" ) };
			json_struct = json_om.toJSON( bdc_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "bd_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "0.12345678901234567890", json_array.get( 0 ).getString() );

			sc_arr = new TestStringArrayConversion();
			sc_arr.s_arr = new String[] { "streng" };
			json_struct = json_om.toJSON( sc_arr, converters );
			Assert.assertNotNull( json_struct);
			json_object = json_struct.getObject();
			Assert.assertNotNull( json_object );
			json_value = json_object.get( "s_arr" );
			Assert.assertNotNull( json_value );
			json_array = json_value.getArray();
			Assert.assertNotNull( json_array );
			Assert.assertEquals( 1, json_array.values.size() );
			Assert.assertEquals( "STRENG", json_array.get( 0 ).getString() );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class NullJsonConverter extends JSONConverterAbstract {
		@Override
		public JSONValue getJSONValue(String fieldName, Boolean boolean_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Integer integer_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Long long_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Float float_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Double double_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigInteger biginteger_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigDecimal bigdecimal_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, String string_value) {
			return null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, byte[] byte_array) {
			return null;
		}
	}

	public static class JSONNullJsonConverter extends JSONConverterAbstract {
		@Override
		public JSONValue getJSONValue(String fieldName, Boolean boolean_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Integer integer_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Long long_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Float float_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Double double_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigInteger biginteger_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigDecimal bigdecimal_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, String string_value) {
			return JSONNull.Null;
		}
		@Override
		public JSONValue getJSONValue(String fieldName, byte[] byte_array) {
			return JSONNull.Null;
		}
	}

	public static class JSONValueJsonConverter extends JSONConverterAbstract {
		@Override
		public JSONValue getJSONValue(String fieldName, Boolean boolean_value) {
			return JSONString.String( boolean_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Integer integer_value) {
			return JSONString.String( integer_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Long long_value) {
			return JSONString.String( long_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Float float_value) {
			return JSONString.String( float_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, Double double_value) {
			return JSONString.String( double_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigInteger biginteger_value) {
			return JSONString.String( biginteger_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, BigDecimal bigdecimal_value) {
			return JSONString.String( bigdecimal_value.toString() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, String string_value) {
			return JSONString.String( string_value.toString().toUpperCase() );
		}
		@Override
		public JSONValue getJSONValue(String fieldName, byte[] byte_array) {
			return JSONString.String( new String( byte_array ).toUpperCase() );
		}
	}

}
