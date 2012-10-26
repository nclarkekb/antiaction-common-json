/*
 * Created on 20/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JSONString extends JSONValue {

	public static class Block {
		char[] chars;
		int offset;
		int len;
		public Block(char[] chars, int offset, int len) {
			this.chars = chars;
			this.offset = offset;
			this.len = len;
		}
	}

	protected static Block[] escapeTable = new Block[ 128 ];

	protected String str;

	protected char[] chars;

	protected List<Block> blocks = new LinkedList<Block>();

	static {
		char[] tmpChars;
		for ( int i=0; i<32; ++i ) {
			switch ( i ) {
			case 0x08:
				tmpChars = "\\b".toCharArray();
				escapeTable[ 0x08 ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			case 0x09:
				tmpChars = "\\t".toCharArray();
				escapeTable[ 0x09 ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			case 0x0A:
				tmpChars = "\\n".toCharArray();
				escapeTable[ 0x0A ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			case 0x0C:
				tmpChars = "\\f".toCharArray();
				escapeTable[ 0x0C ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			case 0x0D:
				tmpChars = "\\r".toCharArray();
				escapeTable[ 0x0D ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			default:
				tmpChars = ("\\u" + Integer.toHexString(0x10000 | i).substring(1).toUpperCase()).toCharArray();
				escapeTable[ i ] = new Block( tmpChars, 0, tmpChars.length );
				break;
			}
		}
		tmpChars = "\\\"".toCharArray();
		escapeTable[ 0x22 ] = new Block( tmpChars, 0, tmpChars.length );
		tmpChars = "\\/".toCharArray();
		escapeTable[ 0x2F ] = new Block( tmpChars, 0, tmpChars.length );
		tmpChars = "\\\\".toCharArray();
		escapeTable[ 0x5C ] = new Block( tmpChars, 0, tmpChars.length );
	}

	public static final JSONString String(String str) {
		return new JSONString( str );
	}

	public JSONString(String str) {
		type = JSONConstants.VT_STRING;
		this.str = str;
		chars = str.toCharArray();
		// Blockify.
		int pos = 0;
		int prev = pos;
		int len = chars.length;
		char c;
		Block escaped;
		while ( pos < len ) {
			c = chars[ pos ];
			if ( c < 128 ) {
				escaped = escapeTable[ c ];
				if ( escaped != null ) {
					if ( pos > prev ) {
						blocks.add( new Block( chars, prev, pos - prev ) );
					}
					blocks.add( escaped );
					++pos;
					prev = pos;
				}
				else {
					++pos;
				}
			}
			else {
				++pos;
			}
		}
		if ( pos > prev ) {
			blocks.add( new Block( chars, prev, pos - prev ) );
		}
	}

	public String getString() {
		return str;
	}

	@Override
	public void encode(JSONEncoder encoder) throws IOException {
		encoder.write( '"' );
		Block block;
		for ( int i=0; i<blocks.size(); ++i ) {
			block = blocks.get( i );
			encoder.write( block.chars, block.offset, block.len );
		}
		encoder.write( '"' );
	}

	@Override
	public String toString() {
		return '"' + str + '"';
	}

	@Override
    public boolean equals(Object obj) {
        if ( obj == null || !(obj instanceof JSONString) ) {
            return false;
        }
        JSONString json_stringObj = (JSONString)obj;
        if ( !str.equals( json_stringObj.str ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return str.hashCode();
    }

}