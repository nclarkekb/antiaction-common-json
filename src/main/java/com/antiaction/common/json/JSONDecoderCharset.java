/*
 * Created on 10/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class JSONDecoderCharset implements JSONDecoder {

	protected CharsetDecoder decoder;

	protected byte[] byteArray;

	protected ByteBuffer byteBuffer;

	protected InputStream in;

	protected boolean eof;

	public JSONDecoderCharset(Charset charset) {
		decoder = charset.newDecoder();
		decoder.onMalformedInput( CodingErrorAction.REPORT );
		decoder.onUnmappableCharacter( CodingErrorAction.REPORT );
		byteArray = new byte[ 1024 ];
		byteBuffer = ByteBuffer.wrap( byteArray );
	}

	@Override
	public void init(InputStream in) {
		this.in = in;
		eof = false;
		byteBuffer.clear();
	}

	@Override
	public void fill(CharBuffer charBuffer) throws IOException {
		int read;
		if ( charBuffer.hasRemaining() ) {
			if ( byteBuffer.hasRemaining() ) {
				read = in.read( byteArray, byteBuffer.position(), byteBuffer.remaining() );
				if ( read != -1 ) {
					byteBuffer.position( byteBuffer.position() + read );
				}
				else {
					eof = true;
				}
			}
			byteBuffer.flip();
			try {
				boolean bDecodeLoop = true;
				while ( bDecodeLoop ) {
					CoderResult result = decoder.decode( byteBuffer, charBuffer, true );
					if ( result == CoderResult.UNDERFLOW ) {
						   bDecodeLoop = false;
					} else if ( result.isError() ) {
						byteBuffer.position( Math.min( byteBuffer.position() + result.length(), byteBuffer.limit() ) );
						//sb.append('?');
						//ew.bConversionError = true;
					}
				}
			} catch (CoderMalfunctionError e) {
			}
			byteBuffer.compact();
			charBuffer.flip();
		}
	}

}
