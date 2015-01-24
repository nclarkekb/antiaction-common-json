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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSONNullable;
import com.antiaction.common.json.representation.JSONCollection;
import com.antiaction.common.json.representation.JSONTextUnmarshaller;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 22/01/2013
 */
@RunWith(JUnit4.class)
public class TestJSONMarshallingTopLevelArray {

	@Test
	public void test_json_solr() {
		File file = TestHelpers.getTestResourceFile( "books.json" );
		// debug
		//System.out.println( file.getPath() );
		FileInputStream fis = null;

		PushbackInputStream pbin;
		int encoding;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		JSONTextUnmarshaller textUnmarshaller = new JSONTextUnmarshaller();
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;
		JSONEncoder json_encoder;
		JSONCollection json_struct;
		JSONCollection json_struct2;

		JSONObjectMapping objectMapping;

		Book[] books_1a = null;
		Book[] books_1b = null;

		try {
	        fis = new FileInputStream( file );
			pbin = new PushbackInputStream( fis, 4 );
			encoding = JSONEncoding.encoding( pbin );

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			objectMapping = json_objectmappings.register( Book[].class );
			Assert.assertNotNull( objectMapping );

			// debug
			//System.out.println( json_objectmappings.toString() );

			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_encoder = json_encoding.getJSONEncoder( encoding );

			json_struct = textUnmarshaller.toJSONStructure( pbin, json_decoder );

			books_1a = json_objectmappings.getStructureUnmarshaller().toObject( json_struct, Book[].class );
			Assert.assertNotNull( books_1a );

			json_struct2 = json_objectmappings.getStructureMarshaller().toJSONStructure( books_1a );

			bOut.reset();
			json_objectmappings.getTextMarshaller().toJSONText( json_struct2, json_encoder, true, bOut );

			// debug
			//System.out.println( new String( bOut.toByteArray(), "UTF-8" ) );

			books_1b = json_objectmappings.getStructureUnmarshaller().toObject( json_struct2, Book[].class );
			Assert.assertNotNull( books_1b );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		finally {
			if ( fis != null ) {
				try {
					fis.close();
				}
				catch (IOException e) {
				}
			}
		}

		Book[] books_2a = null;
		Book[] books_2b = null;

		try {
	        fis = new FileInputStream( file );
			pbin = new PushbackInputStream( fis, 4 );
			encoding = JSONEncoding.encoding( pbin );

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			objectMapping = json_objectmappings.register( Book[].class );
			Assert.assertNotNull( objectMapping );

			// debug
			//System.out.println( json_objectmappings.toString() );

			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_encoder = json_encoding.getJSONEncoder( encoding );

			books_2a = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, Book[].class );
			Assert.assertNotNull( books_2a );

			bOut.reset();
			json_objectmappings.getStreamMarshaller().toJSONText( books_2a, json_encoder, true, bOut );

			// debug
			//System.out.println( new String( bOut.toByteArray(), "UTF-8" ) );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bOut.toByteArray() ), 4 );
			books_2b = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, Book[].class );
			Assert.assertNotNull( books_2b );
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if ( fis != null ) {
				try {
					fis.close();
				}
				catch (IOException e) {
				}
			}
		}

		Assert.assertNotNull( books_1a );
		Assert.assertNotNull( books_1b );
		Assert.assertNotNull( books_2a );
		Assert.assertNotNull( books_2b );
		Assert.assertEquals( 4, books_1a.length );
		Assert.assertEquals( 4, books_1b.length );
		Assert.assertEquals( 4, books_2a.length );
		Assert.assertEquals( 4, books_2b.length );
		assertBooks( books_1a );
		assertBooks( books_1b );
		assertBooks( books_2a );
		assertBooks( books_2b );
	}

	public void assertBooks(Book[] books) {
		Book book;
		String[] cat;

		book = books[ 0 ];
		Assert.assertEquals( "978-0641723445", book.id );
		cat = book.cat;
		Assert.assertEquals( 2, book.cat.length );
		Assert.assertEquals( "book", cat[ 0 ] );
		Assert.assertEquals( "hardcover", cat[ 1 ] );
		Assert.assertEquals( "The Lightning Thief", book.name );
		Assert.assertEquals( "Rick Riordan", book.author );
		Assert.assertEquals( "Percy Jackson and the Olympians", book.series_t );
		Assert.assertEquals( new Integer( 1 ), book.sequence_i );
		Assert.assertEquals( "fantasy", book.genre_s );
		Assert.assertEquals( true, book.inStock );
		Assert.assertEquals( new Float( 12.50 ), book.price );
		Assert.assertEquals( new Integer( 384 ), book.pages_i );

		book = books[ 1 ];
		Assert.assertEquals( "978-1423103349", book.id );
		cat = book.cat;
		Assert.assertEquals( 2, book.cat.length );
		Assert.assertEquals( "book", cat[ 0 ] );
		Assert.assertEquals( "paperback", cat[ 1 ] );
		Assert.assertEquals( "The Sea of Monsters", book.name );
		Assert.assertEquals( "Rick Riordan", book.author );
		Assert.assertEquals( "Percy Jackson and the Olympians", book.series_t );
		Assert.assertEquals( new Integer( 2 ), book.sequence_i );
		Assert.assertEquals( "fantasy", book.genre_s );
		Assert.assertEquals( true, book.inStock );
		Assert.assertEquals( new Float( 6.49 ), book.price );
		Assert.assertEquals( new Integer( 304 ), book.pages_i );

		book = books[ 2 ];
		Assert.assertEquals( "978-1857995879", book.id );
		cat = book.cat;
		Assert.assertEquals( 2, book.cat.length );
		Assert.assertEquals( "book", cat[ 0 ] );
		Assert.assertEquals( "paperback", cat[ 1 ] );
		Assert.assertEquals( "Sophie's World : The Greek Philosophers", book.name );
		Assert.assertEquals( "Jostein Gaarder", book.author );
		Assert.assertEquals( null, book.series_t );
		Assert.assertEquals( new Integer( 1 ), book.sequence_i );
		Assert.assertEquals( "fantasy", book.genre_s );
		Assert.assertEquals( true, book.inStock );
		Assert.assertEquals( new Float( 3.07 ), book.price );
		Assert.assertEquals( new Integer( 64 ), book.pages_i );

		book = books[ 3 ];
		Assert.assertEquals( "978-1933988177", book.id );
		cat = book.cat;
		Assert.assertEquals( 2, book.cat.length );
		Assert.assertEquals( "book", cat[ 0 ] );
		Assert.assertEquals( "paperback", cat[ 1 ] );
		Assert.assertEquals( "Lucene in Action, Second Edition", book.name );
		Assert.assertEquals( "Michael McCandless", book.author );
		Assert.assertEquals( null, book.series_t );
		Assert.assertEquals( new Integer( 1 ), book.sequence_i );
		Assert.assertEquals( "IT", book.genre_s );
		Assert.assertEquals( true, book.inStock );
		Assert.assertEquals( new Float( 30.50 ), book.price );
		Assert.assertEquals( new Integer( 475 ), book.pages_i );
	}

	public static class Book {
		String id;

		String[] cat;

		String name;

		String author;

		@JSONNullable
	    String series_t;

		Integer sequence_i;

		String genre_s;

		Boolean inStock;

		Float price;

		Integer pages_i;
	}

}
