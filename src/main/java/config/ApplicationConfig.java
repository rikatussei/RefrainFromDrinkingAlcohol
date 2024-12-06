// src/main/java/config/ApplicationConfig.java
package config;

import org.slf4j.Logger;

public class ApplicationConfig {
	private static final Logger logger = LoggerUtil.getLogger();
	private static final EnvironmentConfig envConfig = new EnvironmentConfig();

	// OpenAI API設定
	public static class OpenAI {
		public static String getApiKey() {
			return envConfig.get("openai.api.key");
		}

		public static String getModel() {
			return envConfig.get("openai.model");
		}

		public static String getDalleModel() {
			return envConfig.get("openai.dalle-model");
		}
	}

	// データベース設定
	public static class Database {
		public static String getUrl() {
			return envConfig.get("db.url");
		}

		public static String getUsername() {
			return envConfig.get("db.username");
		}

		public static String getPassword() {
			return envConfig.get("db.password");
		}
	}

	// ログ設定
	public static class Logging {
		public static String getLogLevel() {
			return envConfig.get("log.level");
		}

		public static String getLogPath() {
			return envConfig.get("log.path");
		}
	}

	// セキュリティ設定
	public static class Security {
		public static int getSessionTimeout() {
			return Integer.parseInt(envConfig.get("session.timeout"));
		}

		public static String getSecretKey() {
			return envConfig.get("security.secret");
		}
	}
}