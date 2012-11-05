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

	protected boolean bEof;

	protected int errorsPending;

	protected boolean bConversionError;

	protected String replacement;

	protected int replacementLength;

	public JSONDecoderCharset(Charset charset) {
		decoder = charset.newDecoder();
		decoder.onMalformedInput( CodingErrorAction.REPORT );
		decoder.onUnmappableCharacter( CodingErrorAction.REPORT );
		byteArray = new byte[ 1024 ];
		byteBuffer = ByteBuffer.wrap( byteArray );
	}

	@Override
	public void init(InputStream in) {
		decoder.reset();
		this.in = in;
		bEof = false;
		errorsPending = 0;
		bConversionError = false;
		replacement = decoder.replacement();
		replacementLength = replacement.length();
		byteBuffer.clear();
	}

	/*
	 * Assume charBuffer in write mode.
	 * @see com.antiaction.common.json.JSONDecoder#fill(java.nio.CharBuffer)
	 */
	@Override
	public boolean fill(CharBuffer charBuffer) throws IOException {
		int read;
		while ( errorsPending > 0 && charBuffer.remaining() >= replacementLength ) {
			charBuffer.append( replacement );
			--errorsPending;
		}
		if ( charBuffer.hasRemaining() ) {
			while ( !bEof && byteBuffer.hasRemaining() ) {
				read = in.read( byteArray, byteBuffer.position(), byteBuffer.remaining() );
				if ( read != -1 ) {
					byteBuffer.position( byteBuffer.position() + read );
				}
				else {
					bEof = true;
				}
			}
			// Switch buffer to read mode.
			byteBuffer.flip();
			if ( byteBuffer.hasRemaining() ) {
				try {
					boolean bDecodeLoop = true;
					while ( bDecodeLoop ) {
						CoderResult result = decoder.decode( byteBuffer, charBuffer, bEof );
						if ( result == CoderResult.UNDERFLOW ) {
							bDecodeLoop = false;
						}
						else if ( result == CoderResult.OVERFLOW ) {
							bDecodeLoop = false;
						}
						else if ( result.isError() ) {
							bConversionError = true;
							byteBuffer.position( Math.min( byteBuffer.position() + result.length(), byteBuffer.limit() ) );
							//errorsPending += result.length();
							++errorsPending;
							while ( errorsPending > 0 && charBuffer.remaining() >= replacementLength ) {
								charBuffer.append( replacement );
								--errorsPending;
							}
							if ( errorsPending > 0 ) {
								   bDecodeLoop = false;
							}
						}
					}
				}
				catch (CoderMalfunctionError e) {
					throw new IOException( "Colder malfunction!", e );
				}
			}
			// Switch buffer to write mode.
			byteBuffer.compact();
		}
		return (bEof && byteBuffer.position() == 0 && errorsPending == 0);
	}

	@Override
	public boolean isEof() {
		return (bEof && byteBuffer.position() == 0 && errorsPending == 0);
	}

	@Override
	public boolean hasConversionError() {
		return bConversionError;
	}

}
