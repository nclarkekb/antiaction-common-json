package com.antiaction.common.json.integration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONDecoderContext;
import com.antiaction.common.json.JSONEncoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;
import com.antiaction.common.json.JSONStreamMarshaller;
import com.antiaction.common.json.JSONStreamUnmarshaller;

@RunWith(JUnit4.class)
public class TestJSONMarshalling_Concat {

	@Test
	public void test_jsonstreammarshaller_concat() {
		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONEncoder json_encoder;
		JSONDecoder json_decoder;
		JSONDecoderContext json_decodercontext;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ByteArrayInputStream bIn;

		try {
			json_objectmappings.register( TestClass.class );
			json_encoder = json_encoding.getJSONEncoder(JSONEncoding.E_UTF8);
			json_decoder = json_encoding.getJSONDecoder(JSONEncoding.E_UTF8);
			json_decodercontext = new JSONDecoderContext(json_decoder, 8192);

			JSONStreamMarshaller json_marshaller = json_objectmappings.getStreamMarshaller();
			JSONStreamUnmarshaller json_unmarshaller = json_objectmappings.getStreamUnmarshaller();

			TestClass tc1 = new TestClass();
			tc1.id = 1;
			TestClass tc2 = new TestClass();
			tc2.id = 2;
			TestClass tc3 = new TestClass();
			tc3.id = 3;

			json_marshaller.toJSONText(tc1, json_encoder, false, bOut);
			json_marshaller.toJSONText(tc2, json_encoder, false, bOut);
			json_marshaller.toJSONText(tc3, json_encoder, false, bOut);
			bOut.close();

			byte[] bytes = bOut.toByteArray();
			bOut.reset();
			// debug
			//System.out.println(new String(bytes));

			TestClass t1;
			TestClass t2;
			TestClass t3;
			TestClass t4;

			bIn = new ByteArrayInputStream(bytes);
			json_decodercontext.init(bIn);
			t1 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass.class, null);
			Assert.assertNotNull(t1);
			Assert.assertEquals(1, t1.id);
			t2 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass.class, null);
			Assert.assertNotNull(t2);
			Assert.assertEquals(2, t2.id);
			t3 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass.class, null);
			Assert.assertNotNull(t3);
			Assert.assertEquals(3, t3.id);
			t4 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass.class, null);
			Assert.assertNull(t4);

			json_objectmappings.register( TestClass[].class );

			TestClass[] tcArr1 = new TestClass[3];
			tcArr1[ 0 ] = tc1;
			tcArr1[ 1 ] = tc2;
			tcArr1[ 2 ] = tc3;
			TestClass[] tcArr2 = new TestClass[3];
			tcArr2[ 0 ] = tc3;
			tcArr2[ 1 ] = tc2;
			tcArr2[ 2 ] = tc1;
			TestClass[] tcArr3 = new TestClass[3];
			tcArr3[ 0 ] = tc2;
			tcArr3[ 1 ] = tc1;
			tcArr3[ 2 ] = tc3;

			json_marshaller.toJSONText(tcArr1, json_encoder, false, bOut);
			json_marshaller.toJSONText(tcArr2, json_encoder, false, bOut);
			json_marshaller.toJSONText(tcArr3, json_encoder, false, bOut);
			bOut.close();

			bytes = bOut.toByteArray();
			bOut.reset();
			// debug
			//System.out.println(new String(bytes));

			TestClass[] tArr1;
			TestClass[] tArr2;
			TestClass[] tArr3;
			TestClass[] tArr4;

			bIn = new ByteArrayInputStream(bytes);
			json_decodercontext.init(bIn);
			tArr1 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass[].class, null);
			Assert.assertNotNull(tArr1);
			Assert.assertEquals(3, tArr1.length);
			Assert.assertEquals(1, tArr1[ 0 ].id);
			Assert.assertEquals(2, tArr1[ 1 ].id);
			Assert.assertEquals(3, tArr1[ 2 ].id);
			tArr2 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass[].class, null);
			Assert.assertNotNull(tArr2);
			Assert.assertEquals(3, tArr2.length);
			Assert.assertEquals(3, tArr2[ 0 ].id);
			Assert.assertEquals(2, tArr2[ 1 ].id);
			Assert.assertEquals(1, tArr2[ 2 ].id);
			tArr3 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass[].class, null);
			Assert.assertNotNull(tArr3);
			Assert.assertEquals(3, tArr3.length);
			Assert.assertEquals(2, tArr3[ 0 ].id);
			Assert.assertEquals(1, tArr3[ 1 ].id);
			Assert.assertEquals(3, tArr3[ 2 ].id);
			tArr4 = json_unmarshaller.toObject(bIn, json_decodercontext, true, TestClass[].class, null);
			Assert.assertNull(tArr4);
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	public static class TestClass {
		int id;
	}

}
