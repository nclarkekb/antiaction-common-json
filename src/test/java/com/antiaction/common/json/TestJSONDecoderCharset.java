/*
 * Created on 28/10/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.CoderResult;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONDecoderCharset {

	@Test
	public void test_jsondecodercharset() {
		test_jsondecodercharset_params( 256 * 1 );
		test_jsondecodercharset_params( 256 * 2 );
		test_jsondecodercharset_params( 256 * 3 );
		test_jsondecodercharset_params( 256 * 4 );
		test_jsondecodercharset_params( 256 * 5 );
		test_jsondecodercharset_params( 256 * 6 );
		test_jsondecodercharset_params( 256 * 7 );
		test_jsondecodercharset_params( 256 * 8 );
	}

	public void test_jsondecodercharset_params(int bufferSize) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONDecoderCharset json_decoder = null;

		char[] chars = new char[ bufferSize ];
		CharBuffer charBuffer = CharBuffer.wrap( chars );
		StringBuilder sb = new StringBuilder();

		/*
		 * Buffering.
		 */

		try {
			out.reset();
			for ( int n = 0; n < 64; ++n ) {
				for ( int i = 0; i < 256; ++i ) {
					out.write( i );
				}
			}
			byte[] bytes = out.toByteArray();

			String inStr = new String( bytes, "ISO-8859-1" );
			byte[] encodedBytes = inStr.getBytes( "UTF-8" );

			// debug
			//System.out.println( bytes.length );
			//System.out.println( encodedBytes.length );

			in = new ByteArrayInputStream( encodedBytes );
			json_decoder = new JSONDecoderCharset( Charset.forName( "UTF-8" ) );

			json_decoder.init( in );
			sb.setLength( 0 );

			boolean bEof = false;
			while ( !bEof ) {
				while ( !bEof && charBuffer.remaining() > 0 ) {
					Assert.assertFalse( json_decoder.isEof() );
					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					bEof = json_decoder.fill( charBuffer );

					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );
				}
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
				// debug
				//System.out.println( "sb.length: " + sb.length() );
			}
			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertFalse( json_decoder.hasConversionError() );

			bEof = json_decoder.fill( charBuffer );
			// Switch buffer to read mode.
			charBuffer.flip();
			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertEquals( 0, charBuffer.position() );
			Assert.assertEquals( 0, charBuffer.remaining() );
			Assert.assertEquals( 0, charBuffer.limit() );

			// Switch buffer to write mode.
			charBuffer.flip();
			bEof = json_decoder.fill( charBuffer );
			// Switch buffer to read mode.
			charBuffer.flip();
			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertEquals( 0, charBuffer.position() );
			Assert.assertEquals( 0, charBuffer.remaining() );
			Assert.assertEquals( 0, charBuffer.limit() );
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		/*
		 * CoderMalfunctionException.
		 */

		try {
			final Charset charset = Charset.forName( "UTF-8" );
			final CharsetDecoder decoder = charset.newDecoder();
			json_decoder.decoder = new CharsetDecoder( decoder.charset(), decoder.averageCharsPerByte(), decoder.maxCharsPerByte()) {
				@Override
				protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
					throw new CoderMalfunctionError( new NullPointerException()  );
				}
			};
			charBuffer.clear();
			json_decoder.fill( charBuffer );
			Assert.fail( "Exception expected!" );
		}
		catch (IOException e) {
		}
	}

	@Test
	public void test_jsondecodercharset_doublereads() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONDecoderCharset json_decoder = null;

		char[] chars = new char[ 16 ];
		CharBuffer charBuffer = CharBuffer.wrap( chars );
		StringBuilder sb = new StringBuilder();

		try {
			out.reset();
			for ( int i=0; i<256; ++i ) {
				out.write( i );
			}
			byte[] encodedBytes = out.toByteArray();

			in = new ByteArrayInputStream( encodedBytes );
			json_decoder = new JSONDecoderCharset( Charset.forName( "UTF-8" ) );

			json_decoder.init( in );
			sb.setLength( 0 );

			boolean bEof = false;
			while ( !bEof ) {
				while ( !bEof && charBuffer.remaining() > 0 ) {
					Assert.assertFalse( json_decoder.isEof() );
					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					bEof = json_decoder.fill( charBuffer );
					bEof = json_decoder.fill( charBuffer );

					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );
				}
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}
			// debug
			System.out.println( sb.toString() );
			System.out.println( sb.toString().length() );

			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertFalse( json_decoder.hasConversionError() );
		}
		catch (IOException e) {
		}
	}

	@Test
	public void test_jsondecodercharset_invalid_encoding() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONDecoderCharset json_decoder = null;

		char[] chars = new char[ 1024 ];
		CharBuffer charBuffer = CharBuffer.wrap( chars );
		StringBuilder sb = new StringBuilder();

		try {
			out.reset();
			for ( int i=0; i<256; ++i ) {
				out.write( i );
			}
			byte[] encodedBytes = out.toByteArray();

			String inStr = out.toString( "ISO-8859-1" );

			in = new ByteArrayInputStream( encodedBytes );
			json_decoder = new JSONDecoderCharset( Charset.forName( "UTF-8" ) );

			json_decoder.init( in );
			sb.setLength( 0 );

			boolean bEof = false;
			while ( !bEof ) {
				while ( !bEof && charBuffer.remaining() > 0 ) {
					Assert.assertFalse( json_decoder.isEof() );
					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					bEof = json_decoder.fill( charBuffer );

					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );
				}
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
				// debug
				//System.out.println( "sb.length: " + sb.length() );
			}
			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertTrue( json_decoder.hasConversionError() );

			String destStr = sb.toString();
			Assert.assertEquals( inStr.length(), destStr.length() );

			sb.setLength( 0 );
			for ( int i=0; i<128; ++i) {
				sb.append( (char)i );
			}
			for ( int i=0; i<128; ++i) {
				sb.append( '?' );
			}
			String expectedStr = sb.toString();

			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			Assert.assertEquals( expectedStr, destStr );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	//@Test
	public void test_jsondecodercharset_conversionerror() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		JSONDecoderCharset json_decoder = null;

		char[] chars = new char[ 16 ];
		CharBuffer charBuffer = CharBuffer.wrap( chars );
		StringBuilder sb = new StringBuilder();

		try {
			out.reset();
			for ( int i=0; i<256; ++i ) {
				out.write( i );
			}
			byte[] encodedBytes = out.toByteArray();

			in = new ByteArrayInputStream( encodedBytes );
			json_decoder = new JSONDecoderCharset( Charset.forName( "UTF-8" ) );

			json_decoder.init( in );
			sb.setLength( 0 );

			Assert.assertFalse( json_decoder.bEof );
			Assert.assertFalse( json_decoder.bConversionError );
			Assert.assertEquals( 0, json_decoder.errorsPending );

			json_decoder.errorsPending = 24;

			boolean bEof = false;
			while ( !bEof ) {
				while ( !bEof && charBuffer.remaining() > 0 ) {
					Assert.assertFalse( json_decoder.isEof() );
					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					bEof = json_decoder.fill( charBuffer );

					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );
				}
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				charBuffer.clear();
				// debug
				//System.out.println( "sb.length: " + sb.length() );
			}
			// debug
			System.out.println( sb.toString() );
			System.out.println( sb.toString().length() );

			Assert.assertTrue( json_decoder.isEof() );
			Assert.assertTrue( json_decoder.hasConversionError() );

		}
		catch (IOException e) {
		}
	}

}
