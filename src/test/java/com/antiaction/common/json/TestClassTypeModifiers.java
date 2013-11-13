/*
 * JSON library.
 * Copyright 2012-2013 Antiaction (http://antiaction.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.antiaction.common.json;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.annotation.JSON;

/**
 * TODO javadoc
 * @author Nicholas
 * Created on 29/11/2012
 */
@RunWith(JUnit4.class)
@JSON(ignore={"$jacocoData"})
public class TestClassTypeModifiers {

	@Test
	public void test_jsonobjectmapper_classtypemask() {
		JSONObjectMappings json_om = new JSONObjectMappings();

		/*
		 * Annotation.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_ANNOTATION | ClassTypeModifiers.CT_INTERFACE | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( JSON.class ) );

		try {
			json_om.register( JSON.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Anonymous class.
		 */

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
			}
		};
		Assert.assertEquals( ClassTypeModifiers.CT_ANONYMOUSCLASS, ClassTypeModifiers.getClassTypeModifiersMask( runnable.getClass() ) );

		try {
			json_om.register( runnable.getClass() );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Array (unsupported primitive).
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_ARRAY | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_FINAL | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( byte[].class ) );

		try {
			json_om.register( byte[].class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported array type '[B'.", e.getMessage() );
		}

		/*
		 * Enum.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_ENUM | ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_FINAL | ClassTypeModifiers.CM_PUBLIC | ClassTypeModifiers.CM_STATIC, ClassTypeModifiers.getClassTypeModifiersMask( TestEnum.ENUM.getClass() ) );

		try {
			json_om.register( TestEnum.ENUM.getClass() );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Local class.
		 */

		class runnable2 implements Runnable {
			@Override
			public void run() {
			}
		};
		Assert.assertEquals( ClassTypeModifiers.CT_LOCALCLASS, ClassTypeModifiers.getClassTypeModifiersMask( runnable2.class ) );

		try {
			json_om.register( runnable2.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Interface.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_INTERFACE | ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_PUBLIC | ClassTypeModifiers.CM_STATIC, ClassTypeModifiers.getClassTypeModifiersMask( TestInterface.class ) );

		try {
			json_om.register( TestInterface.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Primitive.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_PRIMITIVE | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_FINAL | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( boolean.class ) );

		try {
			json_om.register( boolean.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Abstract member class.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( TestAbstractMemberClass.class ) );

		try {
			json_om.register( TestAbstractMemberClass.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Member class.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( TestMemberClass.class ) );

		try {
			json_om.register( TestMemberClass.class );
			Assert.fail( "Exception expected!" );
		}
		catch (JSONException e) {
			//e.printStackTrace();
			Assert.assertEquals( "Unsupported class type.", e.getMessage() );
		}

		/*
		 * Static member class.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_MEMBERCLASS | ClassTypeModifiers.CM_PUBLIC | ClassTypeModifiers.CM_STATIC, ClassTypeModifiers.getClassTypeModifiersMask( TestStaticMemberClass.class ) );

		try {
			json_om.register( TestStaticMemberClass.class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		/*
		 * Class.
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_CLASS | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( this.getClass() ) );

		try {
			json_om.register( this.getClass() );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

		/*
		 * Array (unsupported primitive).
		 */

		Assert.assertEquals( ClassTypeModifiers.CT_ARRAY | ClassTypeModifiers.CM_ABSTRACT | ClassTypeModifiers.CM_FINAL | ClassTypeModifiers.CM_PUBLIC, ClassTypeModifiers.getClassTypeModifiersMask( byte[].class ) );

		try {
			json_om.register( int[].class );
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}

	}

	public enum TestEnum {
		ENUM();
	}

	public interface TestInterface {
	}

	public abstract class TestAbstractMemberClass {
	}

	public class TestMemberClass {
	}

	@JSON(ignore={"$jacocoData"})
	public static class TestStaticMemberClass {
	}

}
