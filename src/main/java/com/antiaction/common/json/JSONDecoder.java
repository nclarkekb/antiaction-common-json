/*
 * Created on 10/09/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;

/**
 * Interface for JSON decoders to implement.
 *
 * @author Nicholas
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
	 * @throws IOException if an i/o error was encountered during decoding
	 */
	public boolean fill(CharBuffer charBuffer) throws IOException;

	/**
	 * Has <code>InputStream</code> reached EOF.
	 * @return
	 */
	public boolean isEof();

	/**
	 * Was there any conversion errors while decoding the <code>InputStream</code>.
	 * @return boolean indicating if conversion errors were encountered
	 */
	public boolean hasConversionError();

}
