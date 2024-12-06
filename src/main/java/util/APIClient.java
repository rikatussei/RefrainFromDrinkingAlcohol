// src/main/java/util/APIClient.java
package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.ApplicationConfig;

public class APIClient {
	private static final Logger logger = LoggerFactory.getLogger(APIClient.class);
	private static final HttpClient client = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private final String apiKey;

	public APIClient() {
		this.apiKey = ApplicationConfig.OpenAI.getApiKey();
	}

	public String post(String endpoint, String jsonBody) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endpoint))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + apiKey)
				.POST(HttpRequest.BodyPublishers.ofString(jsonBody))
				.build();

		try {
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 200) {
				logger.error("API呼び出しエラー: {} - {}", response.statusCode(), response.body());
				throw new IOException("APIリクエストが失敗しました: " + response.statusCode());
			}

			return response.body();
		} catch (IOException | InterruptedException e) {
			logger.error("API呼び出し中に例外が発生", e);
			throw e;
		}
	}
}