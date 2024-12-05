package service;

import java.io.IOException;

import util.OpenAIClient;

public class AISupportService {
	private final OpenAIClient openAIClient;

	public AISupportService() {
		this.openAIClient = new OpenAIClient();
	}

	// 応援メッセージ生成
	public String generateDailySupportMessage(String userContext) {
		try {
			return openAIClient.generateSupportMessage(userContext);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "がんばりましょう！"; // フォールバックメッセージ
		}
	}

	// モンスター画像生成
	public String generateMonsterImage(String monsterName) {
		try {
			return openAIClient.generateMonsterImage(monsterName);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "/images/default-monster.png"; // デフォルト画像パス
		}
	}
}