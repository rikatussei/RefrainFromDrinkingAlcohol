package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SentimentAnalysisService
 */
package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class SentimentAnalysisService {
	private static final String OPENAI_API_KEY = "your-api-key";
	private static final String API_URL = "https://api.openai.com/v1/completions";
	private static final HttpClient client = HttpClient.newHttpClient();

	public double analyzeSentiment(String text) throws IOException, InterruptedException {
		String prompt = String.format(
				"以下のテキストの感情を分析し、0から1の数値で表してください。" +
						"1は非常にポジティブ、0は非常にネガティブを表します：\n\n%s",
				text);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(API_URL))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + OPENAI_API_KEY)
				.POST(HttpRequest.BodyPublishers.ofString(buildRequestBody(prompt)))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		return parseSentimentScore(response.body());
	}

	private String buildRequestBody(String prompt) {
		JSONObject json = new JSONObject()
				.put("model", "text-davinci-003")
				.put("prompt", prompt)
				.put("max_tokens", 10)
				.put("temperature", 0.3);
		return json.toString();
	}

	private double parseSentimentScore(String response) {
		JSONObject jsonResponse = new JSONObject(response);
		String scoreText = jsonResponse.getJSONArray("choices")
				.getJSONObject(0)
				.getString("text")
				.trim();

		try {
			return Double.parseDouble(scoreText);
		} catch (NumberFormatException e) {
			return 0.5; // デフォルト値
		}
	}
}