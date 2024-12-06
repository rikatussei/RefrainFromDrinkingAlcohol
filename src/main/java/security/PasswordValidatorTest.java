// src/test/java/security/PasswordValidatorTest.java
package security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordValidatorTest {

	@Test
	void testValidPassword() {
		assertTrue(PasswordValidator.isValid("Test123@#"));
		assertTrue(PasswordValidator.isValid("SecurePass1@"));
	}

	@Test
	void testInvalidPasswords() {
		// 短すぎるパスワード
		assertFalse(PasswordValidator.isValid("Test1@"));

		// 数字なし
		assertFalse(PasswordValidator.isValid("TestPass@"));

		// 特殊文字なし
		assertFalse(PasswordValidator.isValid("TestPass123"));

		// 大文字なし
		assertFalse(PasswordValidator.isValid("testpass1@"));

		// 小文字なし
		assertFalse(PasswordValidator.isValid("TESTPASS1@"));

		// スペースを含む
		assertFalse(PasswordValidator.isValid("Test Pass1@"));

		// nullの場合
		assertFalse(PasswordValidator.isValid(null));
	}
}