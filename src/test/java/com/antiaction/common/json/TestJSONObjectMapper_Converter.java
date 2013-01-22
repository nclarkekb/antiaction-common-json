/*
 * Created on 16/01/2013
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONConverter;
import com.antiaction.common.json.annotation.JSONNullable;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Converter {

	@Test
	public void test_jsonobjectmapper_converter() {
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

}
