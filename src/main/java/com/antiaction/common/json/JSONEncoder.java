/*
 * Created on 21/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.OutputStream;

public interface JSONEncoder {

	public void init(OutputStream out);

	public void write(String str) throws IOException;

	public void write(char c) throws IOException;

	public void write(char[] b, int off, int len) throws IOException;

	public void write(char[] b) throws IOException;

	public void write(int b) throws IOException;

	public void write(byte[] b, int off, int len) throws IOException;

	public void write(byte[] b) throws IOException;

	public void close() throws IOException;

}
