/*
 * Created on 21/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for JSON encoders to implement.
 * Implementations will most likely not be thread safe!
 *
 * @author Nicholas
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
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(String str) throws IOException;

	/**
	 * Write a char to the buffer of data to be encoded next.
	 * @param c char to encode
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(char c) throws IOException;

	/**
	 * Write char array interval to the buffer of data to be encoded next.
	 * @param c char array to encode
	 * @param off offset in array
	 * @param len number of chars to write
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(char[] c, int off, int len) throws IOException;

	/**
	 * Write char array to the buffer of data to be encoded next.
	 * @param c char array to encode
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(char[] c) throws IOException;

	/**
	 * Write a byte to the buffer of data to be encoded next.
	 * @param b byte to encode
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(int b) throws IOException;

	/**
	 * Write byte array interval to the buffer of data to be encoded next.
	 * @param b byte array to encode
	 * @param off offset in array
	 * @param len number of bytes to write
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(byte[] b, int off, int len) throws IOException;

	/**
	 * Write byte array to the buffer of data to be encoded next.
	 * @param b byte array to encode
	 * @throws IOException if an i/o exception occurs while encoding
	 */
	public void write(byte[] b) throws IOException;

	/**
	 * Encode the last bytes, if any, and reset buffers.
	 * @throws IOException if an i/o error occurs while closing encoder
	 */
	public void close() throws IOException;

}
