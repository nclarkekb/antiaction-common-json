/*
 * Created on 19/10/2012
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
public class TestJSONArray {

	@Test
	public void test_jsonarray() {
		JSONArray json_array = new JSONArray();
		json_array.add( new JSONString( "name" ) );
		json_array.add( JSONNull.Null );

		try {
			json_array.put( "name", JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.put( new JSONString( "name" ), JSONNull.Null );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( "42" );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}

		try {
			json_array.get( JSONString.String( "42" ) );
			Assert.fail( "Exception expected!" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void test_jsonarray_supported_unsupporteed() {
		JSONStructure json_struct = new JSONArray();
		JSONArray json_array = json_struct.getArray();
		Assert.assertEquals( json_struct, json_array );

		try {
			json_struct.getObject();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBoolean();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getString();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getLong();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getFloat();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getDouble();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBigInteger();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
		try {
			json_struct.getBigDecimal();
			Assert.fail( "Exception expected !" );
		}
		catch (UnsupportedOperationException e) {
		}
	}

}
