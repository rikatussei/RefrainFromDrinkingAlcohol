package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIClient {
	private static final HttpClient client = HttpClient.newHttpClient();
	private final String apiKey;
	private static final String CHAT_API_URL = "https://api.openai.com/v1/chat/completions";
	private static final String IMAGE_API_URL = "https://api.openai.com/v1/images/generations";

	public OpenAIClient() {
		// 設定ファイルからAPIキーを読み込む
		Properties props = new Properties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			this.apiKey = props.getProperty("openai.api.key");
		} catch (IOException e) {
			throw new RuntimeException("設定ファイルの読み込みに失敗しました", e);
		}
	}

	public String generateMonsterName() throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", "gpt-3.5-turbo")
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "あなたは禁酒をテーマにしたRPGのモンスター名を生成します。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", "お酒や飲酒に関連した、面白くてユニークなモンスター名を1つ考えてください。")))
				.put("max_tokens", 50)
				.put("temperature", 0.8);

		HttpResponse<String> response = sendRequest(CHAT_API_URL, requestBody);
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getJSONObject("message")
				.getString("content")
				.trim();
	}

	public byte[] generateMonsterImage(String monsterName) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("prompt", "お酒や飲酒をテーマにした可愛らしいモンスター「" + monsterName + "」のイラスト。" +
						"シンプルで親しみやすいデザイン。明るい色調。")
				.put("n", 1)
				.put("size", "512x512")
				.put("response_format", "b64_json");

		HttpResponse<String> response = sendRequest(IMAGE_API_URL, requestBody);
		JSONObject jsonResponse = new JSONObject(response.body());
		String base64Image = jsonResponse.getJSONArray("data")
				.getJSONObject(0)
				.getString("b64_json");

		return java.util.Base64.getDecoder().decode(base64Image);
	}

	public double analyzeSentiment(String text) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", "gpt-3.5-turbo")
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "テキストの感情を分析し、0から1の数値で返してください。" +
										"0は非常にネガティブ、1は非常にポジティブを表します。" +
										"数値のみを返してください。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", text)))
				.put("max_tokens", 10)
				.put("temperature", 0.3);

		try {
			HttpResponse<String> response = sendRequest(CHAT_API_URL, requestBody);
			JSONObject jsonResponse = new JSONObject(response.body());
			String scoreText = jsonResponse.getJSONArray("choices")
					.getJSONObject(0)
					.getJSONObject("message")
					.getString("content")
					.trim();
			return Double.parseDouble(scoreText);
		} catch (Exception e) {
			// エラー時はニュートラルな値を返す
			return 0.5;
		}
	}

	public String generateSupportMessage(String userContext) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", "gpt-3.5-turbo")
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "あなたは禁酒に取り組む人々を応援するアシスタントです。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", "以下のコンテキストに基づいて、励ましのメッセージを生成してください：" + userContext)))
				.put("max_tokens", 100)
				.put("temperature", 0.7);

		HttpResponse<String> response = sendRequest(CHAT_API_URL, requestBody);
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getJSONObject("message")
				.getString("content")
				.trim();
	}

	private HttpResponse<String> sendRequest(String url, JSONObject requestBody)
			throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + apiKey)
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
				.build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}