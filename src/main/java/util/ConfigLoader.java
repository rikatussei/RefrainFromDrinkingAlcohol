package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 設定ファイルを読み込むためのユーティリティクラス
 */
public class ConfigLoader {
	private static final Properties properties = new Properties();
	private static ConfigLoader instance = null;
	private static final String CONFIG_FILE = "config.properties";

	/**
	 * プライベートコンストラクタ
	 * シングルトンパターンを実装
	 */
	private ConfigLoader() {
		loadProperties();
	}

	/**
	 * インスタンスを取得するメソッド
	 * @return ConfigLoaderのインスタンス
	 */
	public static synchronized ConfigLoader getInstance() {
		if (instance == null) {
			instance = new ConfigLoader();
		}
		return instance;
	}

	/**
	 * プロパティファイルを読み込む
	 */
	private void loadProperties() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
			if (input == null) {
				throw new RuntimeException("設定ファイル " + CONFIG_FILE + " が見つかりません");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("設定ファイルの読み込みに失敗しました", e);
		}
	}

	/**
	 * 文字列の設定値を取得
	 * @param key 設定キー
	 * @return 設定値
	 */
	public String get(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new RuntimeException("設定キー " + key + " が見つかりません");
		}
		return value;
	}

	/**
	 * 整数の設定値を取得
	 * @param key 設定キー
	 * @return 設定値（整数）
	 */
	public int getInt(String key) {
		String value = get(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("設定キー " + key + " の値が整数ではありません: " + value);
		}
	}

	/**
	 * 浮動小数点数の設定値を取得
	 * @param key 設定キー
	 * @return 設定値（浮動小数点数）
	 */
	public double getDouble(String key) {
		String value = get(key);
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("設定キー " + key + " の値が数値ではありません: " + value);
		}
	}

	/**
	 * ブーリアンの設定値を取得
	 * @param key 設定キー
	 * @return 設定値（ブーリアン）
	 */
	public boolean getBoolean(String key) {
		String value = get(key);
		return Boolean.parseBoolean(value);
	}
}