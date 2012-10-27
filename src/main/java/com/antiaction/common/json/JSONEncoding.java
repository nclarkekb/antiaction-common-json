/*
 * Created on 08/10/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;

/**
 * BOM
 * 00 00 FE FF  UTF-32, big-endian
 * FF FE 00 00  UTF-32, little-endian
 * FE FF        UTF-16, big-endian
 * FF FE        UTF-16, little-endian
 * EF BB BF     UTF-8
 * Data
 * xx xx xx xx  UTF-8
 * 00 xx 00 xx  UTF-16BE
 * xx 00 xx 00  UTF-16LE
 * 00 00 00 xx  UTF-32BE
 * xx 00 00 00  UTF-32LE
 *
 * @author Nicholas
 */
public class JSONEncoding {

	// Error
	public static final int E_UNKNOWN = -1;

	// "UTF-8" - xx xx xx xx
	public static final int E_UTF8 = 0;

	// "UTF-16BE" - 00 xx 00 xx
	public static final int E_UTF16BE = 1;

	// "UTF-16LE" - xx 00 xx 00
	public static final int E_UTF16LE = 2;

	// "UTF-32BE" - 00 00 00 xx
	public static final int E_UTF32BE = 3;

	// "UTF-32LE" - xx 00 00 00
	public static final int E_UTF32LE = 4;

	public static int encoding(PushbackInputStream in) throws IOException {
		int encoding = E_UNKNOWN;
		byte[] bytes = new byte[ 4 ];
		int offset = 0;
		int len = 4;
		int read = 0;
		while ( read != -1 && len > 0 ) {
			offset += read;
			len -= read;
			read = in.read( bytes, offset, len );
		}
		len = offset;
		offset = 0;
		if ( len >= 2 ) {
			int b0;
			int b1;
			int b2 = 0;
			int b3 = 0;
			int mask = 0;
			switch ( len ) {
			default:
				b3 = bytes[ 3 ] & 255;
				if ( b3 != 0 ) {
					mask |=  8;
				}
			case 3:
				b2 = bytes[ 2 ] & 255;
				if ( b2 != 0 ) {
					mask |= 4;
				}
			case 2:
				b1 = bytes[ 1 ] & 255;
				if ( b1 != 0 ) {
					mask |=  2;
				}
				b0 = bytes[ 0 ] & 255;
				if ( b0 != 0 ) {
					mask |= 1;
				}
				break;
			}
			switch ( len ) {
			case 2:
				if ( mask == 3 ) {
					if ( b0 == 0xFE && b1 == 0xFF ) {
						encoding = E_UTF16BE;
						offset += 2;
						len -= 2;
					}
					else if ( b0 == 0xFF && b1 == 0xFE ) {
						encoding = E_UTF16LE;
						offset += 2;
						len -= 2;
					}
					else {
						encoding = E_UTF8;
					}
				}
				break;
			case 3:
				if ( mask == 7 ) {
					if ( b0 == 0xEF && b1 == 0xBB && b2 == 0xBF ) {
						encoding = E_UTF8;
						offset += 3;
						len -= 3;
					}
					else {
						encoding = E_UTF8;
					}
				}
				break;
			default:
				switch ( mask ) {
				case 0x0F:
					// 1111
					if ( b0 == 0xEF && b1 == 0xBB && b2 == 0xBF ) {
						encoding = E_UTF8;
						offset += 3;
						len -= 3;
					}
					else {
						encoding = E_UTF8;
					}
					break;
				case 0x0E:
					// 0111
					break;
				case 0x0D:
					// 1011
					break;
				case 0x0C:
					// 0011
					if ( b2 == 0xFE && b3 == 0xFF ) {
						encoding = E_UTF32BE;
						offset += 4;
						len -= 4;
					}
					break;
				case 0x0B:
					// 1101
					if ( b0 == 0xFE && b1 == 0xFF ) {
						encoding = E_UTF16BE;
						offset += 2;
						len -= 2;
					}
					break;
				case 0x0A:
					// 0101
					encoding = E_UTF16BE;
					break;
				case 0x09:
					// 1001
					break;
				case 0x08:
					// 0001
					encoding = E_UTF32BE;
					break;
				case 0x07:
					// 1110
					if ( b0 == 0xFF && b1 == 0xFE ) {
						encoding = E_UTF16LE;
						offset += 2;
						len -= 2;
					}
					break;
				case 0x06:
					// 0110
					break;
				case 0x05:
					// 1010
					encoding = E_UTF16LE;
					break;
				case 0x04:
					// 0010
					break;
				case 0x03:
					// 1100
					if ( b0 == 0xFF && b1 == 0xFE ) {
						encoding = E_UTF32LE;
						offset += 4;
						len -= 4;
					}
					break;
				case 0x02:
					// 0100
					break;
				case 0x01:
					// 1000
					encoding = E_UTF32LE;
					break;
				case 0x00:
					// 0000
					break;
				}
				break;
			}
		}
		if ( len > 0 ) {
			in.unread( bytes, offset, len );
		}
		return encoding;
	}

	private static final ThreadLocal<JSONEncoding> JSONEncodingTL =
		new ThreadLocal<JSONEncoding>() {
		@Override
		public JSONEncoding initialValue() {
			return new JSONEncoding();
		}
	};

	public static JSONEncoding getJSONEncoding() {
		return JSONEncodingTL.get();
	}

	private JSONDecoder utf8_decoder;
	private JSONDecoder utf16be_decoder;
	private JSONDecoder utf16le_decoder;

	private JSONEncoding() {
	}

	public JSONDecoder getJSONDecoder(int encoding) {
		JSONDecoder jsondecoder = null;
		Charset charset;
		switch ( encoding ) {
		case E_UTF8:
			if (utf8_decoder == null) {
				charset = Charset.forName( "UTF-8" );
				utf8_decoder = new JSONDecoderCharset( charset );
			}
			jsondecoder = utf8_decoder;
			break;
		case E_UTF16BE:
			if ( utf16be_decoder == null ) {
				charset = Charset.forName( "UTF-16BE" );
				utf16be_decoder = new JSONDecoderCharset( charset );
			}
			jsondecoder = utf16be_decoder;
			break;
		case E_UTF16LE:
			if ( utf16le_decoder == null ) {
				charset = Charset.forName( "UTF-16LE" );
				utf16le_decoder = new JSONDecoderCharset( charset );
			}
			jsondecoder = utf16le_decoder;
			break;
		case E_UTF32BE:
		case E_UTF32LE:
		case E_UNKNOWN:
		default:
			throw new IllegalArgumentException( "Unsupported encoding!" );
		}
		return jsondecoder;
	}

}
