// src/test/java/security/SecurityUtilsTest.java
package security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SecurityUtilsTest {

	@Test
	void testGenerateSessionToken() {
		String token = SecurityUtils.generateSessionToken();
		assertNotNull(token);
		assertEquals(64, token.length()); // 32バイトを16進数に変換
	}

	@Test
	void testValidateRequest() {
		String token = SecurityUtils.generateSessionToken();
		assertTrue(SecurityUtils.validateRequest(token, token));
		assertFalse(SecurityUtils.validateRequest(token, "invalid-token"));
		assertFalse(SecurityUtils.validateRequest(null, token));
	}
}