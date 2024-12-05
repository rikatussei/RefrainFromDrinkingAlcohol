package service;

import java.io.IOException;
import java.util.Base64;

import util.OpenAIClient;

/**
 * AI支援機能を提供するサービスクラス
 */
public class AISupportService {
	private final OpenAIClient openAIClient;

	/**
	 * コンストラクタ
	 */
	public AISupportService() {
		this.openAIClient = new OpenAIClient();
	}

	/**
	 * 日次応援メッセージを生成
	 * @param userContext ユーザーのコンテキスト情報
	 * @return 生成された応援メッセージ
	 */
	public String generateDailySupportMessage(String userContext) {
		try {
			return openAIClient.generateSupportMessage(userContext);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "がんばりましょう！"; // フォールバックメッセージ
		}
	}

	/**
	 * モンスター画像を生成
	 * @param monsterName モンスター名
	 * @return Base64エンコードされた画像データ、エラー時はデフォルト画像のパス
	 */
	public String generateMonsterImage(String monsterName) {
		try {
			byte[] imageData = openAIClient.generateMonsterImage(monsterName);
			// byte配列をBase64文字列に変換
			return Base64.getEncoder().encodeToString(imageData);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "/RefrainFromDrinkingAlcohol/images/default-monster.png"; // デフォルト画像パス
		}
	}

	/**
	 * モンスター画像をバイト配列として取得
	 * @param monsterName モンスター名
	 * @return 画像のバイト配列
	 * @throws IOException APIとの通信エラー
	 * @throws InterruptedException 処理の中断
	 */
	public byte[] getMonsterImageBytes(String monsterName) throws IOException, InterruptedException {
		return openAIClient.generateMonsterImage(monsterName);
	}
}