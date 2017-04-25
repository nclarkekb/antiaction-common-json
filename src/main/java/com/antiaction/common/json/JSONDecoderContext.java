package com.antiaction.common.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;

public class JSONDecoderContext {

	protected JSONDecoder decoder;

	protected char[] charArray;

	protected CharBuffer charBuffer;

	protected int pos;

	protected int limit;

	protected int y;

	protected int x;

	public JSONDecoderContext(JSONDecoder decoder, int buffersize) {
		this.decoder = decoder;
		charArray = new char[ buffersize ];
		charBuffer = CharBuffer.wrap( charArray );
	}

	public void init(InputStream in) throws IOException {
		decoder.init( in );
		decoder.fill( charBuffer );
		// Switch buffer to read mode.
		charBuffer.flip();
		pos = charBuffer.position();
		limit = charBuffer.limit();
		y = 1;
		x = 1;
	}

	public void position(int pos, int y, int x) {
		this.pos = pos;
		charBuffer.position(pos);
		this.y = y;
		this.x = x;
	}

	public void buffer() throws IOException {
		// Switch buffer to write mode.
		charBuffer.compact();
		decoder.fill( charBuffer );
		// Switch buffer to read mode.
		charBuffer.flip();
		pos = charBuffer.position();
		limit = charBuffer.limit();
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

}
