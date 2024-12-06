// src/main/java/security/CSRFToken.java
package security;

import java.security.SecureRandom;
import java.util.Base64;

public class CSRFToken {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generateToken() {
		byte[] bytes = new byte[32];
		secureRandom.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}
}