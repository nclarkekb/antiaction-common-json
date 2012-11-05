/*
 * Created on 30/10/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestJSONEncoderCharset {

	@Test
	public void test_jsonencodercharset() {
		Charset charset;

		// debug
		//System.out.println( "UTF-8" );

		charset = Charset.forName("UTF-8");
		test_jsonencodercharset_params( charset, JSONEncoding.E_UTF8 );

		// debug
		//System.out.println( "UTF-16" );

		charset = Charset.forName("UTF-16");
		test_jsonencodercharset_params( charset, JSONEncoding.E_UTF16BE );

		// debug
		//System.out.println( "UTF-16BE" );

		charset = Charset.forName("UTF-16BE");
		test_jsonencodercharset_params( charset, JSONEncoding.E_UTF16BE );

		// debug
		//System.out.println( "UTF-16LE" );

		charset = Charset.forName("UTF-16LE");
		test_jsonencodercharset_params( charset, JSONEncoding.E_UTF16LE );
	}

	public void test_jsonencodercharset_params(Charset charset, int expected_encoding) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;

		JSONEncoderCharset json_encoder = new JSONEncoderCharset( charset );
		JSONDecoderCharset json_decoder = new JSONDecoderCharset( charset );

		char[] chars = new char[ 16 ];
		CharBuffer charBuffer = CharBuffer.wrap( chars );

		int idx;
		byte[] bytes;
		boolean bEof;

		try {
			/*
			 * Char data.
			 */

			StringBuilder sb = new StringBuilder();
			for ( int i=0; i<16384; ++i ) {
				sb.append( (char)i );
			}
			String inStr = sb.toString();
			char[] inChars = inStr.toCharArray();

			/*
			 * write(String).
			 */

			out.reset();
			json_encoder.init( out );

			json_encoder.write( inStr );
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inChars, sb.toString().toCharArray() );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * write(char)
			 */

			out.reset();
			json_encoder.init( out );

			for ( int i=0; i<inChars.length; ++i ) {
				json_encoder.write( inChars[ i ] );
			}
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inChars, sb.toString().toCharArray() );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * write(char[]).
			 */

			out.reset();
			json_encoder.init( out );

			json_encoder.write( inChars );
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inChars, sb.toString().toCharArray() );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * write(char, off, len).
			 */

			out.reset();
			json_encoder.init( out );

			idx = 0;
			while ( idx < inChars.length ) {
				json_encoder.write( inChars, idx, 16 );
				idx += 16;
			}
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inChars, sb.toString().toCharArray() );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * Byte data.
			 */

			out.reset();
			for ( int n = 0; n < 64; ++n ) {
				for ( int i = 0; i < 256; ++i ) {
					out.write( i );
				}
			}
			byte[] inBytes = out.toByteArray();

			/*
			 * write(byte)
			 */

			out.reset();
			json_encoder.init( out );

			for ( int i=0; i<inChars.length; ++i ) {
				json_encoder.write( inBytes[ i ] );
			}
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inBytes, sb.toString().getBytes( "ISO-8859-1" ) );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * write(byte[])
			 */

			out.reset();
			json_encoder.init( out );

			json_encoder.write( inBytes );
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inBytes, sb.toString().getBytes( "ISO-8859-1" ) );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );

			/*
			 * write(char, off, len).
			 */

			out.reset();
			json_encoder.init( out );

			idx = 0;
			while ( idx < inChars.length ) {
				json_encoder.write( inBytes, idx, 16 );
				idx += 16;
			}
			json_encoder.close();
			json_encoder.close();

			// debug
			//System.out.println( out.size() );

			bytes = out.toByteArray();

			in = new ByteArrayInputStream( bytes );

			json_decoder.init( in );
			sb.setLength( 0 );

			bEof = false;
			while ( !bEof ) {
				bEof = json_decoder.fill( charBuffer );
				// Switch buffer to read mode.
				charBuffer.flip();
				if (charBuffer.remaining() > 0 ) {
					sb.append( chars, 0, charBuffer.remaining() );
				}
				// Switch buffer to write mode.
				charBuffer.clear();
			}

			Assert.assertArrayEquals( inBytes, sb.toString().getBytes( "ISO-8859-1" ) );
			// debug
			//System.out.println( sb.toString() );
			//System.out.println( sb.toString().length() );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@Test
	public void test_jsonencodercharset_exception() {
		final Charset charset = Charset.forName( "UTF-8" );
		final CharsetEncoder encoder = charset.newEncoder();
		try {
			JSONEncoderCharset json_encoder = new JSONEncoderCharset( charset );

			json_encoder.encoder = new CharsetEncoder( encoder.charset(), encoder.averageBytesPerChar(), encoder.maxBytesPerChar() ) {
				@Override
				protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
					return CoderResult.malformedForLength( 1 );
				}
			};
			json_encoder.write( "test".getBytes( "ISO-8859-1" ) );
			json_encoder.close();
			Assert.fail( "Exception expected!" );
		}
		catch (IOException e) {
		}
	}

}
