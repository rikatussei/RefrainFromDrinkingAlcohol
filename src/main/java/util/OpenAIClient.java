package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIClient {
	private static final String API_KEY = "your-api-key"; // OpenAI APIキーをここに入力
	private static final String NAME_API_ENDPOINT = "https://api.openai.com/v1/completions";
	private static final String IMAGE_API_ENDPOINT = "https://api.openai.com/v1/images/generations";

	// モンスター名を生成する
	public String generateMonsterName() throws IOException {
		// OpenAI APIにリクエストを送信するためのJSONデータ
		String requestBody = "{"
				+ "\"model\": \"text-davinci-003\","
				+ "\"prompt\": \"Generate a unique monster name.\","
				+ "\"max_tokens\": 10"
				+ "}";

		// API呼び出し
		HttpURLConnection connection = setupConnection(NAME_API_ENDPOINT, requestBody);

		// APIレスポンスを文字列として返す
		return parseResponse(connection);
	}

	// モンスター画像を生成する
	public byte[] generateMonsterImage(String monsterName) throws IOException {
		// OpenAIのDALL-E APIに送信するためのJSONデータ
		String requestBody = "{"
				+ "\"prompt\": \"A fantasy monster named " + monsterName + "\","
				+ "\"n\": 1,"
				+ "\"size\": \"512x512\""
				+ "}";

		// API呼び出し
		HttpURLConnection connection = setupConnection(IMAGE_API_ENDPOINT, requestBody);

		// バイナリデータ（画像）を返す
		InputStream responseStream = connection.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = responseStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		return outputStream.toByteArray();
	}

	// 共通のHTTP接続設定
	private HttpURLConnection setupConnection(String endpoint, String requestBody) throws IOException {
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		// リクエストボディを送信
		try (OutputStream os = connection.getOutputStream()) {
			os.write(requestBody.getBytes());
		}

		return connection;
	}

	// APIレスポンスを文字列として解析
	private String parseResponse(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
