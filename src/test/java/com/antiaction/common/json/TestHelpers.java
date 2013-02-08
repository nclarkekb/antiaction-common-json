/*
 * Created on 28/12/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;

public class TestHelpers {

	public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( expecteds[ i ], actuals[ i ] );
		}
	}

	public static void assertArrayEquals(float[] expecteds, float[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Float)expecteds[ i ], (Float)actuals[ i ] );
		}
	}

	public static void assertArrayEquals(double[] expecteds, double[] actuals) {
		Assert.assertEquals( expecteds.length, actuals.length );
		for ( int i=0; i<expecteds.length; ++i ) {
			Assert.assertEquals( (Double)expecteds[ i ], (Double)actuals[ i ] );
		}
	}

	public static byte[] filterWhitespaces(byte[] in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int c;
		for (int i=0; i<in.length; ++ i) {
			c = in[ i ] & 255;
			switch ( c ) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				break;
			default:
				out.write( c );
				break;
			}
		}
		return out.toByteArray();
	}

}
