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
					Assert.assertFalse( json_decoder.eof() );
					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					bEof = json_decoder.fill( charBuffer );

					// debug
					//System.out.println( charBuffer.position() );
					//System.out.println( charBuffer.remaining() );
					//System.out.println( charBuffer.limit() );

					charBuffer.compact();
				}
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				charBuffer.clear();
				System.out.println( "sb.length: " + sb.length() );
			}
			Assert.assertTrue( json_decoder.eof() );

			json_decoder.byteBuffer.flip();
			bEof = json_decoder.fill( charBuffer );
			Assert.assertTrue( json_decoder.eof() );
			Assert.assertEquals( 0, charBuffer.position() );
			Assert.assertEquals( 0, charBuffer.remaining() );
			Assert.assertEquals( 0, charBuffer.limit() );

			bEof = json_decoder.fill( charBuffer );
			Assert.assertTrue( json_decoder.eof() );
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
					//return null;
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
	public void test_jsondecoder_invalid_encoding() {
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


		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
