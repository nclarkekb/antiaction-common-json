package com.antiaction.common.json.integration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONEncoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMapping;
import com.antiaction.common.json.JSONObjectMappings;
import com.antiaction.common.json.TestHelpers;
import com.antiaction.common.json.annotation.JSONNullable;
import com.antiaction.common.json.annotation.JSONTypeInstance;
import com.antiaction.common.json.representation.JSONCollection;
import com.antiaction.common.json.representation.JSONTextUnmarshaller;

@RunWith(JUnit4.class)
public class TestJSONMarshalling_Map {

	@Test
	public void test_marshalling_map() {
		File file = TestHelpers.getTestResourceFile( "files.json" );
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

		SOLRFileResponse fileResponse2a;
		SOLRFileResponse fileResponse2b;

		try {
			fis = new FileInputStream( file );
			pbin = new PushbackInputStream( fis, 4 );
			encoding = JSONEncoding.encoding( pbin );

			JSONObjectMappings json_objectmappings = new JSONObjectMappings();
			objectMapping = json_objectmappings.register( SOLRFileResponse.class );
			Assert.assertNotNull( objectMapping );

			// debug
			//System.out.println( json_objectmappings.toString() );

			json_decoder = json_encoding.getJSONDecoder( encoding );
			json_encoder = json_encoding.getJSONEncoder( encoding );

			fileResponse2a = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, SOLRFileResponse.class );
			Assert.assertNotNull( fileResponse2a );

			assertSOLRFileResponse( fileResponse2a );

			bOut.reset();
			json_objectmappings.getStreamMarshaller().toJSONText( fileResponse2a, json_encoder, true, bOut );

			// debug
			//System.out.println( new String( bOut.toByteArray(), "UTF-8" ) );

			pbin = new PushbackInputStream( new ByteArrayInputStream( bOut.toByteArray() ), 4 );
			fileResponse2b = json_objectmappings.getStreamUnmarshaller().toObject( pbin, json_decoder, SOLRFileResponse.class );
			Assert.assertNotNull( fileResponse2b );

			assertSOLRFileResponse( fileResponse2b );
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
	}

	public static class SOLRFileResponse {

		public ResponseHeader responseHeader;

		//@JSONTypeInstance(HashMap.class)
		@JSONTypeInstance(LinkedHashMap.class)
		public Map<String, SOLRFile> files;

	}

	public static class ResponseHeader {

		int status;

		long QTime;

	}

	public static class SOLRFile {

		@JSONNullable
		Boolean directory;

		@JSONNullable
		Long size;

		// 2014-02-22T07:39:00Z
		String modified;

	}

	protected Object[][] expectedFiles = new Object[][] {
			{
				"schema.xml",
				null,
				60941L,
				"2014-02-22T07:39:00Z"
			},
			{
				"admin-extra.menu-top.html",
				null,
				951L,
				"2014-02-22T07:39:00Z"
			},
			{
				"xslt",
				true,
				null,
				"2014-03-23T09:47:27Z"
			},
			{
				"currency.xml",
				null,
				4041L,
				"2014-02-22T07:39:00Z"
			},
			{
				"velocity",
				true,
				null,
				"2014-03-23T09:47:27Z"
			},
			{
				"stopwords.txt",
				null,
				795L,
				"2014-02-22T07:39:00Z"
			},
			{
				"elevate.xml",
				null,
				1386L,
				"2014-02-22T07:39:00Z"
			},
			{
				"clustering",
				true,
				null,
				"2014-03-23T09:47:25Z"
			},
			{
				"mapping-ISOLatin1Accent.txt",
				null,
				3114L,
				"2014-02-22T07:39:00Z"
			},
			{
				"mapping-FoldToASCII.txt",
				null,
				82327L,
				"2014-02-22T07:39:00Z"
			},
			{
				"lib",
				true,
				null,
				"2014-03-23T13:31:42Z"
			},
			{
				"protwords.txt",
				null,
				894L,
				"2014-02-22T07:39:00Z"
			},
			{
				"update-script.js",
				null,
				1469L,
				"2014-02-22T07:39:00Z"
			},
			{
				"lang",
				true,
				null,
				"2014-03-23T09:47:26Z"
			},
			{
				"synonyms.txt",
				null,
				1148L,
				"2014-02-22T07:39:00Z"
			},
			{
				"admin-extra.html",
				null,
				1092L,
				"2014-02-22T07:39:00Z"
			},
			{
				"scripts.conf",
				null,
				921L,
				"2014-02-18T10:48:12Z"
			},
			{
				"admin-extra.menu-bottom.html",
				null,
				953L,
				"2014-02-22T07:39:00Z"
			},
			{
				"spellings.txt",
				null,
				16L,
				"2014-02-22T07:39:00Z"
			},
			{
				"solrconfig.xml",
				null,
				75921L,
				"2014-03-23T23:53:53Z"
			}
	};

	protected void assertSOLRFileResponse(SOLRFileResponse fileResponse) {
		Assert.assertNotNull( fileResponse.responseHeader );
		Assert.assertEquals( 0, fileResponse.responseHeader.status );
		Assert.assertEquals( 1, fileResponse.responseHeader.QTime );
		LinkedHashMap<String, SOLRFile> files = (LinkedHashMap<String, SOLRFile>)fileResponse.files;
		Assert.assertNotNull( files );

		Assert.assertEquals( expectedFiles.length, files.size() );
		Iterator<Entry<String, SOLRFile>> iter = files.entrySet().iterator();
		Entry<String, SOLRFile> entry;
		SOLRFile solrFile;
		int idx = 0;
		while ( iter.hasNext() ) {
			entry = iter.next();
			solrFile = entry.getValue();
			Assert.assertEquals( expectedFiles[ idx ][ 0 ], entry.getKey() );
			Assert.assertEquals( expectedFiles[ idx ][ 1 ], solrFile.directory );
			Assert.assertEquals( expectedFiles[ idx ][ 2 ], solrFile.size );
			Assert.assertEquals( expectedFiles[ idx ][ 3 ], solrFile.modified );
			++idx;
		}
	}

}
