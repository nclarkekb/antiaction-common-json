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
import java.nio.CharBuffer;

/**
 * Interface for JSON decoders to implement.
 * Implementations will most likely not be thread safe!
 *
 * @author Nicholas
 * Created on 10/09/2012
 */
public interface JSONDecoder {

	/**
	 * Initialize Decoder with an <code>InputStream</code>.
	 * @param in encoded JSON text <code>InputStream</code>
	 */
	public void init(InputStream in);

	/**
	 * Fill char buffer with as much decoded JSON text as possible.
	 * @param charBuffer char buffer for decoded JSON text
	 * @return EOF boolean indication
	 * @throws IOException if an I/O error was encountered during decoding
	 */
	public boolean fill(CharBuffer charBuffer) throws IOException;

	/**
	 * Has <code>InputStream</code> reached EOF.
	 * @return true is the end of the stream has been reached, false otherwise
	 */
	public boolean isEof();

	/**
	 * Was there any conversion errors while decoding the <code>InputStream</code>.
	 * @return boolean indicating if conversion errors were encountered
	 */
	public boolean hasConversionError();

}
