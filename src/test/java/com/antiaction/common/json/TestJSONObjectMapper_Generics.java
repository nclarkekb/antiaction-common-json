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

package com.antiaction.common.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 18/12/2012
 */
@RunWith(JUnit4.class)
public class TestJSONObjectMapper_Generics {

	@Test
	public void test_jsonobjectmapper_generics() {
		JSONObjectMappings json_om = new JSONObjectMappings();
		try {
			json_om.register( TestJSONMapObjectGenerics.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
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

	@Test
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

		JSONObjectMappings json_om = new JSONObjectMappings();

		/*
		 * Lists.
		 */

		Object[][] testClasses = new Object[][] {
			{ TestJSONMapInvalidList1.class, "Unsupported collection interface field type. (java.util.List)" },
			{ TestJSONMapInvalidList2.class, "Collection must have parametrized type(s). (java.util.ArrayList)" },
			{ TestJSONMapInvalidList3.class, "Collection must have parametrized type(s). (java.util.LinkedList)" },
			{ TestJSONMapInvalidList4.class, "Unsupported collection interface field type. (java.util.List)" },
			{ TestJSONMapInvalidList5.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidList6.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidList7.class, "Unsupported collection interface field type. (java.util.List)" },
			{ TestJSONMapInvalidList8.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidList9.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidList10.class, "Unsupported collection interface field type. (java.util.List .." },
			{ TestJSONMapInvalidMap1.class, "Unsupported collection interface field type. (java.util.Map)" },
			{ TestJSONMapInvalidMap2.class, "Collection must have parametrized type(s). (java.util.HashMap)" },
			{ TestJSONMapInvalidMap3.class, "Collection must have parametrized type(s). (java.util.TreeMap)" },
			{ TestJSONMapInvalidMap4.class, "Unsupported collection interface field type. (java.util.Map)" },
			{ TestJSONMapInvalidMap5.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidMap6.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidMap7.class, "Unsupported collection interface field type. (java.util.Map)" },
			{ TestJSONMapInvalidMap8.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidMap9.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidMap10.class, "Unsupported collection interface field type. (java.util.Map .." },
			{ TestJSONMapInvalidSet1.class, "Unsupported collection interface field type. (java.util.Set)" },
			{ TestJSONMapInvalidSet2.class, "Collection must have parametrized type(s). (java.util.HashSet)" },
			{ TestJSONMapInvalidSet3.class, "Collection must have parametrized type(s). (java.util.TreeSet)" },
			{ TestJSONMapInvalidSet4.class, "Unsupported collection interface field type. (java.util.Set)" },
			{ TestJSONMapInvalidSet5.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidSet6.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidSet7.class, "Unsupported collection interface field type. (java.util.Set)" },
			{ TestJSONMapInvalidSet8.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidSet9.class, "Unsupported use of wildcard parameterized types." },
			{ TestJSONMapInvalidSet10.class, "Unsupported collection interface field type. (java.util.Set .."}
		};

		Class<?> clazz;
		String msg;

		for ( int i=0; i<testClasses.length; ++i ) {
			clazz = (Class<?>)testClasses[ i ][ 0 ];
			msg = (String)testClasses[ i ][ 1 ];
			try {
				json_om.register( clazz );
				Assert.fail( "Exception expected!" );
			}
			catch (JSONException e) {
				// debug
				e.printStackTrace();
				System.out.println( e.getMessage() );
				System.out.println( msg );
				Assert.assertTrue( e.getMessage().startsWith( msg ) );
			}
		}

		/*
		try {
			json_om.register( TestJSONMapArrayObject.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		*/
	}

	/*
	 * Lists.
	 */

	public static class TestJSONMapInvalidList1 {
		@SuppressWarnings("rawtypes")
		public List list;
	}

	public static class TestJSONMapInvalidList2 {
		@SuppressWarnings("rawtypes")
		public ArrayList list;
	}

	public static class TestJSONMapInvalidList3 {
		@SuppressWarnings("rawtypes")
		public LinkedList list;
	}

	public static class TestJSONMapInvalidList4 {
		public List<?> list;
	}

	public static class TestJSONMapInvalidList5 {
		public ArrayList<?> list;
	}

	public static class TestJSONMapInvalidList6 {
		public LinkedList<?> list;
	}

	public static class TestJSONMapInvalidList7 {
		public List<? extends Object> list;
	}

	public static class TestJSONMapInvalidList8 {
		public ArrayList<? extends Object> list;
	}

	public static class TestJSONMapInvalidList9 {
		public LinkedList<? extends Object> list;
	}

	public interface List2<E> extends List<E> {
	};

	public interface List3<E> extends List2<E> {
	};

	public static class TestJSONMapInvalidList10 {
		@SuppressWarnings("rawtypes")
		public List3 list;
	}

	/*
	 * Maps.
	 */

	public static class TestJSONMapInvalidMap1 {
		@SuppressWarnings("rawtypes")
		public Map map;
	}

	public static class TestJSONMapInvalidMap2 {
		@SuppressWarnings("rawtypes")
		public HashMap map;
	}

	public static class TestJSONMapInvalidMap3 {
		@SuppressWarnings("rawtypes")
		public TreeMap map;
	}

	public static class TestJSONMapInvalidMap4 {
		public Map<?, ?> map;
	}

	public static class TestJSONMapInvalidMap5 {
		public HashMap<?, Integer> map;
	}

	public static class TestJSONMapInvalidMap6 {
		public TreeMap<Integer, ?> map;
	}

	public static class TestJSONMapInvalidMap7 {
		public Map<? extends Object, ? extends Object> map;
	}

	public static class TestJSONMapInvalidMap8 {
		public HashMap<? extends Object, Integer> map;
	}

	public static class TestJSONMapInvalidMap9 {
		public TreeMap<Integer, ? extends Object> map;
	}

	public interface Map2<K, V> extends Map<K, V> {
	};

	public interface Map3<K, V> extends Map2<K, V> {
	};

	public static class TestJSONMapInvalidMap10 {
		@SuppressWarnings("rawtypes")
		public Map3 map;
	}

	/*
	 * Sets.
	 */

	public static class TestJSONMapInvalidSet1 {
		@SuppressWarnings("rawtypes")
		public Set set;
	}

	public static class TestJSONMapInvalidSet2 {
		@SuppressWarnings("rawtypes")
		public HashSet set;
	}

	public static class TestJSONMapInvalidSet3 {
		@SuppressWarnings("rawtypes")
		public TreeSet set;
	}

	public static class TestJSONMapInvalidSet4 {
		public Set<?> set;
	}

	public static class TestJSONMapInvalidSet5 {
		public HashSet<?> set;
	}

	public static class TestJSONMapInvalidSet6 {
		public TreeSet<?> set;
	}

	public static class TestJSONMapInvalidSet7 {
		public Set<? extends Object> set;
	}

	public static class TestJSONMapInvalidSet8 {
		public HashSet<? extends Object> set;
	}

	public static class TestJSONMapInvalidSet9 {
		public TreeSet<? extends Object> set;
	}

	public interface Set2<E> extends Set<E> {
	};

	public interface Set3<E> extends Set2<E> {
	};

	public static class TestJSONMapInvalidSet10 {
		@SuppressWarnings("rawtypes")
		public Set3 list;
	}

	public static class TestJSONMapArrayObject {

		public int[] a_int;

		//public TestJSONMapObjectGenerics<?> genObj;

		public ArrayList fudge0;

		public ArrayList<? extends Object> fudge;

		public ArrayList<String> fudge2;

	}

}
