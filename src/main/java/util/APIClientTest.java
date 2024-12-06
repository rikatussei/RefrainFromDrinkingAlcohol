// src/test/java/util/APIClientTest.java
package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class APIClientTest {
	private APIClient apiClient;

	@BeforeEach
	void setUp() {
		apiClient = new APIClient();
	}

	@Test
	void testPost() {
		// テストケース実装
		assertDoesNotThrow(() -> {
			String response = apiClient.post(
					"https://api.example.com/test",
					"{\"test\": true}");
			assertNotNull(response);
		});
	}
}