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

public interface JSONDecoder {

	public void init(InputStream in);

	public boolean fill(CharBuffer charBuffer) throws IOException;

	public boolean isEof();

	public boolean hasConversionError();

}
