/*
 * Created on 18/12/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Generics {

	@Test
	public void test_jsonobjectmapper_generics() {
		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			json_om.register( TestJSONMapObjectGenerics.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class TestJSONMapObjectGenerics<T> {

		protected int i1;

		protected Integer i2;

		protected TestJSONMapObject obj;

		protected T t;

	}

	public static class TestJSONMapObject {

		protected Integer i;

	}

	public static class TestJSONMapArrayObject {

		public int[] a_int;

		//public TestJSONMapObjectGenerics<?> genObj;

		public ArrayList fudge0;

		public ArrayList<? extends Object> fudge;

		public ArrayList<String> fudge2;

	}

	//@Test
	public void test_jsonobjectmapper_arrays() {
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

		JSONObjectMapper json_om = new JSONObjectMapper();
		try {
			json_om.register( TestJSONMapArrayObject.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

}
