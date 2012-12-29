/*
 * Created on 28/12/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.antiaction.common.json;

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

}
