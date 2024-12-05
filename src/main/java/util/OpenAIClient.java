package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * OpenAI APIとの通信を管理するクライアントクラス
 */
public class OpenAIClient {
	// HTTPクライアントのシングルトンインスタンス
	private static final HttpClient client = HttpClient.newHttpClient();

	// APIの設定情報
	private final String apiKey;
	private final String chatModel;
	private final String dalleModel;
	private final int maxTokens;
	private final double temperature;

	// APIエンドポイント
	private static final String CHAT_API_URL = "https://api.openai.com/v1/chat/completions";
	private static final String IMAGE_API_URL = "https://api.openai.com/v1/images/generations";

	/**
	 * コンストラクタ：設定をロード
	 */
	public OpenAIClient() {
		ConfigLoader config = ConfigLoader.getInstance();
		this.apiKey = config.get("openai.api.key");
		this.chatModel = config.get("openai.model"); // gpt-4o-mini
		this.dalleModel = config.get("openai.dalle-model"); // dall-e-2
		this.maxTokens = config.getInt("openai.max-tokens");
		this.temperature = config.getDouble("openai.temperature");
	}

	/**
	 * モンスター名を生成
	 * @return 生成されたモンスター名
	 */
	public String generateMonsterName() throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", chatModel)
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "あなたは禁酒をテーマにしたRPGのモンスター名を生成します。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", "お酒や飲酒に関連した、面白くてユニークなモンスター名を1つ考えてください。")))
				.put("max_tokens", maxTokens)
				.put("temperature", temperature);

		HttpResponse<String> response = sendRequest(CHAT_API_URL, requestBody);
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getJSONObject("message")
				.getString("content")
				.trim();
	}

	/**
	 * モンスター画像を生成
	 * @param monsterName モンスター名
	 * @return Base64エンコードされた画像データ
	 */
	public byte[] generateMonsterImage(String monsterName) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", dalleModel)
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

	/**
	 * 応援メッセージを生成
	 * @param context ユーザーのコンテキスト情報
	 * @return 生成された応援メッセージ
	 */
	public String generateSupportMessage(String context) throws IOException, InterruptedException {
		JSONObject requestBody = new JSONObject()
				.put("model", chatModel)
				.put("messages", new JSONArray()
						.put(new JSONObject()
								.put("role", "system")
								.put("content", "あなたは禁酒に取り組む人々を応援するアシスタントです。"))
						.put(new JSONObject()
								.put("role", "user")
								.put("content", "以下の状況に基づいて、励ましのメッセージを生成してください：" + context)))
				.put("max_tokens", maxTokens)
				.put("temperature", temperature);

		HttpResponse<String> response = sendRequest(CHAT_API_URL, requestBody);
		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getJSONObject("message")
				.getString("content")
				.trim();
	}

	/**
	 * HTTPリクエストを送信
	 * @param url エンドポイントURL
	 * @param requestBody リクエストボディ
	 * @return HTTPレスポンス
	 */
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

	/**
	 * 使用中のチャットモデルを表示（デバッグ用）
	 */
	public void useChatModel() {
		System.out.println("Chat Model: " + chatModel);
	}

	/**
	 * 使用中の画像生成モデルを表示（デバッグ用）
	 */
	public void useDalleModel() {
		System.out.println("DALL-E Model: " + dalleModel);
	}
}