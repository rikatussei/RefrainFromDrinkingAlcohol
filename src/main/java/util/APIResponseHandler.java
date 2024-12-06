// src/main/java/util/APIResponseHandler.java
package util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import logging.ApplicationException;

public class APIResponseHandler {
	private static final Logger logger = LoggerFactory.getLogger(APIResponseHandler.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T parseResponse(String jsonResponse, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonResponse, valueType);
		} catch (Exception e) {
			logger.error("APIレスポースのパースに失敗", e);
			throw new ApplicationException(
					"APIレスポンスの解析に失敗しました",
					ErrorCodes.API_ERROR,
					e);
		}
	}

	public static String extractMonsterName(String response) {
		try {
			JSONObject jsonResponse = new JSONObject(response);
			return jsonResponse.getJSONArray("choices")
					.getJSONObject(0)
					.getJSONObject("message")
					.getString("content")
					.trim();
		} catch (Exception e) {
			logger.error("モンスター名の抽出に失敗", e);
			throw new ApplicationException(
					"モンスター名の生成に失敗しました",
					ErrorCodes.MONSTER_GENERATION_ERROR,
					e);
		}
	}

	public static byte[] extractImageData(String response) {
		try {
			JSONObject jsonResponse = new JSONObject(response);
			String base64Image = jsonResponse.getJSONArray("data")
					.getJSONObject(0)
					.getString("b64_json");
			return java.util.Base64.getDecoder().decode(base64Image);
		} catch (Exception e) {
			logger.error("画像データの抽出に失敗", e);
			throw new ApplicationException(
					"モンスター画像の生成に失敗しました",
					ErrorCodes.MONSTER_GENERATION_ERROR,
					e);
		}
	}
}