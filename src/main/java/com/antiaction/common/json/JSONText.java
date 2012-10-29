/*
 * Created on 06/08/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.LinkedList;

public class JSONText {

	public void encodeJSONtext(JSONStructure json_structure, JSONEncoder encoder, OutputStream out) throws IOException {
		encoder.init( out );
		if ( json_structure != null ) {
			json_structure.encode( encoder );
		}
		else {
			throw new IOException( "Invalid JSON structure!" );
		}
		encoder.close();
	}

	public static final int S_START = 0;
	public static final int S_OBJECT = 1;
	public static final int S_OBJECT_NAME = 20;
	public static final int S_OBJECT_COLON = 21;
	public static final int S_OBJECT_VALUE = 23;
	public static final int S_OBJECT_VALUE_NEXT = 24;
	public static final int S_OBJECT_NAME_NEXT = 25;
	public static final int S_ARRAY = 2;
	public static final int S_ARRAY_VALUE = 3;
	public static final int S_ARRAY_NEXT = 4;
	public static final int S_VALUE_START = 5;
	public static final int S_STRING = 6;
	public static final int S_STRING_UNESCAPE = 7;
	public static final int S_STRING_UNHEX = 8;
	public static final int S_CONSTANT = 9;
	public static final int S_NUMBER_MINUS = 10;
	public static final int S_NUMBER_ZERO = 11;
	public static final int S_NUMBER_INTEGER = 12;
	public static final int S_NUMBER_DECIMAL = 13;
	public static final int S_NUMBER_DECIMALS = 14;
	public static final int S_NUMBER_E = 15;
	public static final int S_NUMBER_EXPONENT = 16;
	public static final int S_NUMBER_EXPONENTS = 17;
	public static final int S_EOF = 18;

	protected StringBuilder sbStr = new StringBuilder();

	protected static class StackEntry {
		JSONStructure json_structure;
		JSONString json_name;
		StackEntry(	JSONStructure json_structure, JSONString json_name) {
			this.json_structure = json_structure;
			this.json_name = json_name;
		}
	}

	public JSONStructure decodeJSONtext(InputStream in, JSONDecoder decoder) throws IOException {
		LinkedList<StackEntry> stack = new LinkedList<StackEntry>();
		StackEntry entry = null;
		JSONStructure current = null;

		char[] charArray = new char[ 1024 ];
		CharBuffer charBuffer = CharBuffer.wrap( charArray );

		decoder.init( in );
		decoder.fill( charBuffer );
		int pos = charBuffer.position();
		int limit = charBuffer.limit();

		int hexValue = 0;
		int hexCount = 0;
		int i;
		String constant;

		JSONString json_string = null;
		JSONString json_name = null;
		JSONValue json_value = null;

		int state = S_START;
		int rstate = -1;
		boolean bLoop = true;
		char c;
		while ( bLoop ) {
			while ( pos < limit ) {
				c = charArray[ pos ];
				switch ( state ) {
				case S_START:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case '{':
						current = new JSONObject();
						stack.add( new StackEntry( current, json_name ) );
						state = S_OBJECT;
						break;
					case '[':
						current = new JSONArray();
						stack.add( new StackEntry( current, json_name ) );
						state = S_ARRAY;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_OBJECT:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case '}':
						/*
						if ( stack.size() == 0 ) {
							throw new IOException( "Invalid JSON structure!" );
						}
						*/
						entry = stack.removeLast();
						current = entry.json_structure;
						json_name = entry.json_name;
						json_value = current;
						if ( stack.size() > 0 ) {
							current = stack.getLast().json_structure;
							if (current.type == JSONConstants.VT_OBJECT) {
								state = S_OBJECT_VALUE;
							}
							else {
								state = S_ARRAY_VALUE;
							}
						}
						else {
							state = S_EOF;
						}
						break;
					case '"':
						sbStr.setLength( 0 );
						state = S_STRING;
						rstate = S_OBJECT_NAME;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_OBJECT_NAME:
					json_name = json_string;
					state = S_OBJECT_COLON;
					// debug
					//System.out.println( json_value.toString() );
				case S_OBJECT_COLON:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case ':':
						state = S_VALUE_START;
						rstate = S_OBJECT_VALUE;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_OBJECT_VALUE:
					current.put( json_name, json_value );
					state = S_OBJECT_VALUE_NEXT;
					// debug
					//System.out.println( json_value.toString() );
				case S_OBJECT_VALUE_NEXT:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case '}':
						/*
						if ( stack.size() == 0 ) {
							throw new IOException( "Invalid JSON structure!" );
						}
						*/
						entry = stack.removeLast();
						current = entry.json_structure;
						json_name = entry.json_name;
						json_value = current;
						if ( stack.size() > 0 ) {
							current = stack.getLast().json_structure;
							if (current.type == JSONConstants.VT_OBJECT) {
								state = S_OBJECT_VALUE;
							}
							else {
								state = S_ARRAY_VALUE;
							}
						}
						else {
							state = S_EOF;
						}
						break;
					case ',':
						state = S_OBJECT_NAME_NEXT;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_OBJECT_NAME_NEXT:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case '"':
						sbStr.setLength( 0 );
						state = S_STRING;
						rstate = S_OBJECT_NAME;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_ARRAY:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case ']':
						/*
						if ( stack.size() == 0 ) {
							throw new IOException( "Invalid JSON structure!" );
						}
						*/
						entry = stack.removeLast();
						current = entry.json_structure;
						json_name = entry.json_name;
						json_value = current;
						if ( stack.size() > 0 ) {
							current = stack.getLast().json_structure;
							if (current.type == JSONConstants.VT_OBJECT) {
								state = S_OBJECT_VALUE;
							}
							else {
								state = S_ARRAY_VALUE;
							}
						}
						else {
							state = S_EOF;
						}
						break;
					case '{':
						current = new JSONObject();
						stack.add( new StackEntry( current, json_name ) );
						state = S_OBJECT;
						break;
					case '[':
						current = new JSONArray();
						stack.add( new StackEntry( current, json_name ) );
						state = S_ARRAY;
						break;
					case '"':
						sbStr.setLength( 0 );
						state = S_STRING;
						rstate = S_ARRAY_VALUE;
						break;
					case 'f':
					case 'n':
					case 't':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_CONSTANT;
						rstate = S_ARRAY_VALUE;
						break;
					case '-':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_MINUS;
						rstate = S_ARRAY_VALUE;
						break;
					case '0':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_ZERO;
						rstate = S_ARRAY_VALUE;
						break;
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_INTEGER;
						rstate = S_ARRAY_VALUE;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_ARRAY_VALUE:
					current.add( json_value );
					state = S_ARRAY_NEXT;
					// debug
					//System.out.println( json_value.toString() );
				case S_ARRAY_NEXT:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case ']':
						/*
						if ( stack.size() == 0 ) {
							throw new IOException( "Invalid JSON structure!" );
						}
						*/
						entry = stack.removeLast();
						current = entry.json_structure;
						json_name = entry.json_name;
						json_value = current;
						if ( stack.size() > 0 ) {
							current = stack.getLast().json_structure;
							if (current.type == JSONConstants.VT_OBJECT) {
								state = S_OBJECT_VALUE;
							}
							else {
								state = S_ARRAY_VALUE;
							}
						}
						else {
							state = S_EOF;
						}
						break;
					case ',':
						state = S_VALUE_START;
						rstate = S_ARRAY_VALUE;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_VALUE_START:
					// rstate should be set prior to this state.
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					case '{':
						current = new JSONObject();
						stack.add( new StackEntry( current, json_name ) );
						state = S_OBJECT;
						break;
					case '[':
						current = new JSONArray();
						stack.add( new StackEntry( current, json_name ) );
						state = S_ARRAY;
						break;
					case '"':
						sbStr.setLength( 0 );
						state = S_STRING;
						break;
					case 'f':
					case 'n':
					case 't':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_CONSTANT;
						break;
					case '-':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_MINUS;
						break;
					case '0':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_ZERO;
						break;
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.setLength( 0 );
						sbStr.append( c );
						state = S_NUMBER_INTEGER;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_STRING:
					switch ( c ) {
					case '"':
						json_string = JSONString.String( sbStr.toString() );
						json_value = json_string;
						state = rstate;
						break;
					case '\\':
						state = S_STRING_UNESCAPE;
						break;
					default:
						if ( c < 32 ) {
							throw new IOException( "Invalid JSON structure!" );
						}
						sbStr.append( c );
						break;
					}
					++pos;
					break;
				case S_STRING_UNESCAPE:
					switch ( c ) {
					case '"':
						sbStr.append( '"');
						state = S_STRING;
						break;
					case '/':
						sbStr.append( '/' );
						state = S_STRING;
						break;
					case '\\':
						sbStr.append( '\\' );
						state = S_STRING;
						break;
					case 'b':
						sbStr.append( (char)0x08 );
						state = S_STRING;
						break;
					case 't':
						sbStr.append( (char)0x09 );
						state = S_STRING;
						break;
					case 'n':
						sbStr.append( (char)0x0A );
						state = S_STRING;
						break;
					case 'f':
						sbStr.append( (char)0x0C );
						state = S_STRING;
						break;
					case 'r':
						sbStr.append( (char)0x0D );
						state = S_STRING;
						break;
					case 'u':
						hexValue = 0;
						hexCount = 4;
						state = S_STRING_UNHEX;
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
					break;
				case S_STRING_UNHEX:
					if ( c > 255 ) {
						throw new IOException( "Invalid JSON structure!" );
					}
					i = asciiHexTab[ c ];
					if ( i == -1 ) {
						throw new IOException( "Invalid JSON structure!" );
					}
					hexValue <<= 4;
					hexValue |= i;
					--hexCount;
					if (hexCount == 0) {
						sbStr.append( (char)hexValue );
						state = S_STRING;
					}
					++pos;
					break;
				case S_CONSTANT:
					switch ( c ) {
					case 'a':
					case 'l':
					case 's':
					case 'e':
					case 'r':
					case 'u':
						sbStr.append( c );
						++pos;
						break;
					default:
						constant = sbStr.toString();
						if ( "false".equals( constant ) ) {
							json_value = JSONBoolean.False;
						}
						else if ( "null".equals( constant ) ) {
							json_value = JSONNull.Null;
						}
						else if ( "true".equals( constant ) ) {
							json_value = JSONBoolean.True;
						}
						else {
							throw new IOException( "Invalid JSON constant: '" + constant + "'!" );
						}
						state = rstate;
						break;
					}
					break;
				case S_NUMBER_MINUS:
					switch ( c ) {
					case '0':
						sbStr.append( c );
						state = S_NUMBER_ZERO;
						break;
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						state = S_NUMBER_INTEGER;
						break;
					default:
						throw new IOException( "Invalid JSON number structure!" );
					}
					++pos;
					break;
				case S_NUMBER_INTEGER:
					switch ( c ) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						++pos;
						break;
					case '.':
						sbStr.append( c );
						state = S_NUMBER_DECIMAL;
						++pos;
						break;
					case 'e':
						sbStr.append( c );
						++pos;
						break;
					case 'E':
						sbStr.append( c );
						++pos;
						break;
					default:
						json_value = new JSONNumber( sbStr.toString() );
						state = rstate;
						break;
					}
					break;
				case S_NUMBER_ZERO:
					switch ( c ) {
					case '.':
						sbStr.append( c );
						state = S_NUMBER_DECIMAL;
						++pos;
						break;
					case 'e':
						sbStr.append( c );
						state = S_NUMBER_E;
						++pos;
						break;
					case 'E':
						sbStr.append( c );
						state = S_NUMBER_E;
						++pos;
						break;
					default:
						json_value = new JSONNumber( sbStr.toString() );
						state = rstate;
						break;
					}
					break;
				case S_NUMBER_DECIMAL:
					switch ( c ) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						state = S_NUMBER_DECIMALS;
						++pos;
						break;
					default:
						throw new IOException( "Invalid JSON number structure!" );
					}
					break;
				case S_NUMBER_DECIMALS:
					switch ( c ) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						++pos;
						break;
					case 'e':
						sbStr.append( c );
						state = S_NUMBER_E;
						++pos;
						break;
					case 'E':
						sbStr.append( c );
						state = S_NUMBER_E;
						++pos;
						break;
					default:
						json_value = new JSONNumber( sbStr.toString() );
						state = rstate;
					}
					break;
				case S_NUMBER_E:
					switch ( c ) {
					case '+':
						sbStr.append( c );
						state = S_NUMBER_EXPONENT;
						break;
					case '-':
						sbStr.append( c );
						state = S_NUMBER_EXPONENT;
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						state = S_NUMBER_EXPONENTS;
						break;
					default:
						throw new IOException( "Invalid JSON number structure!" );
					}
					++pos;
					break;
				case S_NUMBER_EXPONENT:
					switch ( c ) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						state = S_NUMBER_EXPONENTS;
						++pos;
						break;
					default:
						throw new IOException( "Invalid JSON number structure!" );
					}
					break;
				case S_NUMBER_EXPONENTS:
					switch ( c ) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						sbStr.append( c );
						++pos;
						break;
					default:
						json_value = new JSONNumber( sbStr.toString() );
						state = rstate;
					}
					break;
				case S_EOF:
					switch ( c ) {
					case 0x20:
					case 0x09:
					case 0x0A:
					case 0x0D:
						// Whitespace.
						break;
					default:
						throw new IOException( "Invalid JSON structure!" );
					}
					++pos;
				}
			}
			bLoop = false;
		}
		if (current == null || stack.size() > 0) {
			throw new IOException( "Invalid JSON structure!" );
		}
		return current;
	}

	/** Hex char to integer conversion table. */
	public static int[] asciiHexTab = new int[256];

	/** Integer to hex char conversion table. */
	public static char[] hexTab = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/*
	 * Initialize ASCII hex table.
	 */
	static {
		String hex = "0123456789abcdef";
		for (int i=0; i<asciiHexTab.length; ++i) {
			asciiHexTab[i] = hex.indexOf(i);
		}
		hex = hex.toUpperCase();
		for (int i=0; i<hex.length(); ++i) {
			asciiHexTab[hex.charAt(i)] = i;
		}
	}

}
