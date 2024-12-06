// src/main/java/security/SecurityUtils.java
package security;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtils {
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
	private static final SecureRandom secureRandom = new SecureRandom();

	// セッショントークン生成
	public static String generateSessionToken() {
		byte[] bytes = new byte[32];
		secureRandom.nextBytes(bytes);
		return bytesToHex(bytes);
	}

	// リクエストの検証
	public static boolean validateRequest(String token, String storedToken) {
		if (token == null || storedToken == null) {
			return false;
		}
		return token.equals(storedToken);
	}

	// バイト配列を16進数文字列に変換
	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}

	// HTTPSチェック
	public static boolean isSecureConnection(String url) {
		return url != null && url.toLowerCase().startsWith("https");
	}
}