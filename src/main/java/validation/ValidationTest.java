// src/test/java/validation/ValidationTest.java
package validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidationTest {
	private final Validation validation = new Validation();

	@Test
	void testIsNull() {
		validation.isNull("テスト", null);
		assertTrue(validation.hasErrorMsg());

		validation.isNull("テスト", "");
		assertTrue(validation.hasErrorMsg());

		validation.isNull("テスト", "値あり");
		assertFalse(validation.hasErrorMsg());
	}

	@Test
	void testLength() {
		validation.length("テスト", "abc", 2, 5);
		assertFalse(validation.hasErrorMsg());

		validation.length("テスト", "abcdef", 2, 5);
		assertTrue(validation.hasErrorMsg());

		validation.length("テスト", "a", 2, 5);
		assertTrue(validation.hasErrorMsg());
	}

	@Test
	void testIsNumber() {
		assertTrue(validation.isNumber("テスト", "123"));
		assertFalse(validation.isNumber("テスト", "abc"));
		assertFalse(validation.isNumber("テスト", "12.3"));
	}
}