// src/main/java/config/SecurityConfig.java
package config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfig {
	private static final SecretKey KEY = generateKey();
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	public static String encryptPassword(String rawPassword) {
		return ENCODER.encode(rawPassword);
	}

	public static boolean matches(String rawPassword, String encodedPassword) {
		return ENCODER.matches(rawPassword, encodedPassword);
	}

	private static SecretKey generateKey() {
		byte[] key = new byte[32];
		// 本番環境では環境変数から取得
		return new SecretKeySpec(key, "AES");
	}
}