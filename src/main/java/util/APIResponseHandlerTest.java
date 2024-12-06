// src/test/java/util/APIResponseHandlerTest.java
package util;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class APIResponseHandlerTest {

	@Test
	void testExtractMonsterName() {
		// テスト用のJSONレスポンス作成
		JSONObject response = new JSONObject()
				.put("choices", new JSONArray()
						.put(new JSONObject()
								.put("message", new JSONObject()
										.put("content", "テストモンスター"))));

		String monsterName = APIResponseHandler.extractMonsterName(response.toString());
		assertEquals("テストモンスター", monsterName);
	}

	@Test
	void testExtractImageData() {
		// Base64エンコードされた画像データのモック
		String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

		JSONObject response = new JSONObject()
				.put("data", new JSONArray()
						.put(new JSONObject()
								.put("b64_json", base64Image)));

		byte[] imageData = APIResponseHandler.extractImageData(response.toString());
		assertNotNull(imageData);
		assertTrue(imageData.length > 0);
	}
}