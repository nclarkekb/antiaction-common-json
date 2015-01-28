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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import org.junit.Assert;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 28/12/2012
 */
public class TestHelpers {

	protected static ClassLoader clsLdr = TestHelpers.class.getClassLoader();

	public static final File getTestResourceFile(String fname) {
		URL url = clsLdr.getResource( fname );
		if ( url ==  null ) {
			throw new IllegalStateException( "Could not find resource, '" + fname + "'." );
		}
		String path = url.getFile();
		if ( path == null ) {
			throw new IllegalStateException( "Could not resolve path for resource, " + fname + "'." );
		}
		path = path.replaceAll( "%5b", "[" );
		path = path.replaceAll( "%5d", "]" );
		File file = new File( path );
		return file;
	}

	public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( expecteds[ i ], actuals[ i ] );
		}
	}

	public static void assertArrayEquals(float[] expecteds, float[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Float)expecteds[ i ], (Float)actuals[ i ] );
		}
	}

	public static void assertArrayEquals(double[] expecteds, double[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Double)expecteds[ i ], (Double)actuals[ i ] );
		}
	}

	public static byte[] filterWhitespaces(byte[] in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int c;
		for (int i=0; i<in.length; ++ i) {
			c = in[ i ] & 255;
			switch ( c ) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				break;
			default:
				out.write( c );
				break;
			}
		}
		return out.toByteArray();
	}

	public static void saveFile(String fname, byte[] bytes) throws IOException {
		File file = new File( fname );
		if ( file.exists() ) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile( file, "rw" );
		raf.seek( 0L );
		raf.setLength( 0L );
		raf.write( bytes );
		raf.close();
	}

}
