package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIClient {
	private final String apiKey;
	private final String chatModel;
	private final String dalleModel;
	private final int maxTokens;
	private final double temperature;
	private static final HttpClient client = HttpClient.newHttpClient();

	// コンストラクタ
	public OpenAIClient() {
		ConfigLoader config = ConfigLoader.getInstance();
		this.apiKey = config.get("openai.api.key");
		this.chatModel = config.get("openai.model");
		this.dalleModel = config.get("openai.dalle-model");
		this.maxTokens = config.getInt("openai.max-tokens");
		this.temperature = config.getDouble("openai.temperature");
	}

	// 応援メッセージ生成（GPT-4使用）
	public String generateSupportMessage(String context) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", chatModel)
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "禁酒に取り組む人への励ましのメッセージを生成してください。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", context)))
				.put("max_tokens", maxTokens)
				.put("temperature", temperature);

		return sendChatRequest(requestBody);
	}

	// モンスター画像生成（DALL-E使用）
	public String generateMonsterImage(String monsterName) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", dalleModel)
				.put("prompt", "Create a cute cartoon monster related to " + monsterName)
				.put("n", 1)
				.put("size", "512x512");

		return sendImageRequest(requestBody);
	}

	// ChatGPT APIリクエスト送信
	private String sendChatRequest(JSONObject requestBody) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.openai.com/v1/chat/completions"))
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getJSONObject("message")
				.getString("content");
	}

	// DALL-E APIリクエスト送信
	private String sendImageRequest(JSONObject requestBody) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.openai.com/v1/images/generations"))
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("data")
				.getJSONObject(0)
				.getString("url");
	}

	// モデル情報表示用メソッド（デバッグ用）
	public void useChatModel() {
		System.out.println("Chat Model: " + chatModel);
	}

	public void useDalleModel() {
		System.out.println("DALL-E Model: " + dalleModel);
	}
}