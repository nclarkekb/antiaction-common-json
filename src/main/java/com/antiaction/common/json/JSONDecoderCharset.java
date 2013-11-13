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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/**
 * JSON Decoder implementation using a given <code>Charset</code>.
 * This implementation is not thread safe!
 *
 * @author Nicholas
 * Created on 10/09/2012
 */
public class JSONDecoderCharset implements JSONDecoder {

	/** Charset Decoder used. */
	protected CharsetDecoder decoder;

	/** Byte array used by <code>ByteBuffer</code>. */
	protected byte[] byteArray;

	/** Internal staging byte buffer prior to decoding. */
	protected ByteBuffer byteBuffer;

	/** <code>InputStream</code> currently used. */
	protected InputStream in;

	/** Is inputstream EOF. */
	protected boolean bEof;

	/** Conversion errors pending where replacement still has to be written to the char buffer. */
	protected int errorsPending;

	/** Has a conversion error occurred. */
	protected boolean bConversionError;

	/** Replacement string used in conversion errors. */
	protected String replacement;

	/** Length of replacement string. */
	protected int replacementLength;

	/**
	 * Construct a reusable JSON Decoder using the provided <code>Charset</code>.
	 * @param charset <code>charset</code> to use when decoding text
	 */
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
