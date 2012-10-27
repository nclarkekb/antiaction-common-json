/*
 * Created on 23/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class JSONEncoderCharset implements JSONEncoder {

	protected CharsetEncoder encoder;

	protected char[] charArray;

	protected CharBuffer charBuffer;

	protected byte[] byteArray;

	protected ByteBuffer byteBuffer;

	protected OutputStream out;

	public JSONEncoderCharset(Charset charset) {
		encoder = charset.newEncoder();
		encoder.onMalformedInput( CodingErrorAction.REPORT );
		encoder.onUnmappableCharacter( CodingErrorAction.REPORT );
		charArray = new char[ 1024 ];
		charBuffer = CharBuffer.wrap( charArray );
		byteArray = new byte[ 1024 ];
		byteBuffer = ByteBuffer.wrap( byteArray );
	}

	@Override
	public void init(OutputStream out) {
		this.out = out;
		charBuffer.clear();
		byteBuffer.clear();
	}

	protected void encode_buffer(boolean endOfInput) throws IOException {
		charBuffer.flip();
		CoderResult result = encoder.encode( charBuffer, byteBuffer, endOfInput );
		if ( result == CoderResult.OVERFLOW ) {
			byteBuffer.flip();
			int pos = byteBuffer.position();
			int limit = byteBuffer.limit();
			out.write( byteArray, pos, limit - pos );
			byteBuffer.position( limit );
			byteBuffer.compact();
		}
		/*
		else if (result == CoderResult.UNDERFLOW) {
		}
		*/
		else if ( result.isError() ) {
			throw new IOException( "Encoding error!" );
		}
		charBuffer.compact();
	}

	@Override
	public void write(String str) throws IOException {
		char[] tmpChars = str.toCharArray();
		write( tmpChars, 0, tmpChars.length );
	}

	@Override
	public void write(char c) throws IOException {
		if ( charBuffer.remaining() == 0 ) {
			encode_buffer( false );
		}
		charBuffer.put( c );
	}

	@Override
	public void write(char[] c) throws IOException {
		write( c, 0, c.length );
	}

	@Override
	public void write(char[] c, int off, int len) throws IOException {
		if ( charBuffer.remaining() == 0 ) {
			encode_buffer( false );
		}
		while ( len > 0 ) {
			int pos = charBuffer.position();
			int remaining = charBuffer.remaining();
			if ( remaining > len ) {
				remaining = len;
			}
			len -= remaining;
			while ( remaining > 0 ) {
				charArray[ pos++ ] = c[ off++ ];
				--remaining;
			}
			charBuffer.position( pos );
		}
	}

	@Override
	public void write(int b) throws IOException {
		if ( charBuffer.remaining() == 0 ) {
			encode_buffer( false );
		}
		charBuffer.put( (char)(b & 255) );
	}

	@Override
	public void write(byte[] b) throws IOException {
		write( b, 0, b.length );
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if ( charBuffer.remaining() == 0 ) {
			encode_buffer( false );
		}
		while( len > 0 ) {
			int pos = charBuffer.position();
			int remaining = charBuffer.remaining();
			if ( remaining > len ) {
				remaining = len;
			}
			len -= remaining;
			while ( remaining > 0 ) {
				charArray[ pos++ ] = (char)(b[ off++ ] & 255);
				--remaining;
			}
			charBuffer.position( pos );
		}
	}

	@Override
	public void close() throws IOException {
		encode_buffer( true );
		if ( charBuffer.position() > 0 ) {
			throw new IOException( "'charBuffer' should be empty!" );
		}
		if ( byteBuffer.position() > 0 ) {
			byteBuffer.flip();
			int pos = byteBuffer.position();
			int limit = byteBuffer.limit();
			out.write( byteArray, pos, limit - pos );
			byteBuffer.position( limit );
			byteBuffer.compact();
		}
	}

}
