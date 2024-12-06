// src/main/java/security/SecurityConfig.java
package security;

import java.security.Key;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SecurityConfig {
	private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	private static final Key JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String encodePassword(String rawPassword) {
		return PASSWORD_ENCODER.encode(rawPassword);
	}

	public static boolean verifyPassword(String rawPassword, String encodedPassword) {
		return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
	}

	public static Key getJwtKey() {
		return JWT_KEY;
	}
}