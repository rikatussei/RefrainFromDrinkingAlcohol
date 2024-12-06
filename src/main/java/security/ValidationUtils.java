// src/main/java/security/ValidationUtils.java
package security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");

	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

	public static List<String> validateUserInput(String username, String password, String email) {
		List<String> errors = new ArrayList<>();

		if (!isValidUsername(username)) {
			errors.add("ユーザー名は3-16文字の英数字、アンダースコア、ハイフンのみ使用可能です");
		}

		if (!isValidPassword(password)) {
			errors.add("パスワードは8文字以上で、大文字、小文字、数字、特殊文字を含める必要があります");
		}

		if (!isValidEmail(email)) {
			errors.add("無効なメールアドレス形式です");
		}

		return errors;
	}

	public static boolean isValidUsername(String username) {
		return username != null && USERNAME_PATTERN.matcher(username).matches();
	}

	public static boolean isValidPassword(String password) {
		return password != null && PASSWORD_PATTERN.matcher(password).matches();
	}

	public static boolean isValidEmail(String email) {
		return email != null && EMAIL_PATTERN.matcher(email).matches();
	}

	// 入力値のサニタイズ
	public static String sanitizeInput(String input) {
		if (input == null) {
			return null;
		}
		// HTMLタグの除去
		input = input.replaceAll("<[^>]*>", "");
		// 特殊文字のエスケープ
		input = input.replaceAll("[\\\\\";\'%]", "\\\\$0");
		return input;
	}
}