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
import java.io.OutputStream;

/**
 * Interface for JSON encoders to implement.
 * Implementations will most likely not be thread safe!
 *
 * @author Nicholas
 * Created on 21/08/2012
 */
public interface JSONEncoder {

	/**
	 * Initialize Encoder with an <code>OutputStream</code>.
	 * @param out encoded JSON text <code>OutputStream</code>
	 */
	public void init(OutputStream out);

	/**
	 * Write String to the buffer of data to be encoded next.
	 * @param str String to encode
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(String str) throws IOException;

	/**
	 * Write a char to the buffer of data to be encoded next.
	 * @param c char to encode
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(char c) throws IOException;

	/**
	 * Write char array interval to the buffer of data to be encoded next.
	 * @param c char array to encode
	 * @param off offset in array
	 * @param len number of chars to write
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(char[] c, int off, int len) throws IOException;

	/**
	 * Write char array to the buffer of data to be encoded next.
	 * @param c char array to encode
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(char[] c) throws IOException;

	/**
	 * Write a byte to the buffer of data to be encoded next.
	 * @param b byte to encode
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(int b) throws IOException;

	/**
	 * Write byte array interval to the buffer of data to be encoded next.
	 * @param b byte array to encode
	 * @param off offset in array
	 * @param len number of bytes to write
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(byte[] b, int off, int len) throws IOException;

	/**
	 * Write byte array to the buffer of data to be encoded next.
	 * @param b byte array to encode
	 * @throws IOException if an I/O exception occurs while encoding
	 */
	public void write(byte[] b) throws IOException;

	/**
	 * Encode the last bytes, if any, and reset buffers.
	 * @throws IOException if an I/O error occurs while closing encoder
	 */
	public void close() throws IOException;

}
