package service;

import java.io.IOException;
import java.util.Random;

import util.OpenAIClient;

/**
 * 応援メッセージの生成と管理を行うサービスクラス
 */
public class SupportMessageService {
	private final OpenAIClient openAIClient;
	private final Random random;

	// 事前定義された応援メッセージのテンプレート
	private static final String[][] SUPPORT_MESSAGES = {
			// 成功時の応援メッセージ
			{
					"今日も素晴らしい選択をしましたね！水分補給で体も喜んでいます！",
					"一日一日の積み重ねが、あなたの新しい習慣を作っています。すごいです！",
					"健康的な選択ができて素敵ですね。これからも一緒に進んでいきましょう！"
			},
			// 困難時の応援メッセージ
			{
					"今日は難しい日だったかもしれませんが、明日は新しいチャンス。一緒に頑張りましょう！",
					"完璧を目指す必要はありません。小さな進歩も大切な一歩です。",
					"つまずいても大丈夫。あなたの挑戦する勇気が素晴らしいです！"
			},
			// 日常的な応援メッセージ
			{
					"新しい趣味や楽しみを見つける良いきっかけになりますね！",
					"心と体の健康のために、一緒に頑張っていきましょう！",
					"毎日の小さな選択が、大きな変化を生み出します。応援しています！"
			}
	};

	public SupportMessageService() {
		this.openAIClient = new OpenAIClient();
		this.random = new Random();
	}

	/**
	 * AIを使用して応援メッセージを生成
	 * @param context ユーザーの状況
	 * @return 生成された応援メッセージ
	 */
	public String generateAIMessage(String context) throws IOException, InterruptedException {
		String prompt = String.format(
				"以下の状況のユーザーに対して、禁酒を応援する温かいメッセージを生成してください：\n%s\n" +
						"メッセージは励ましと共感を含み、50文字程度でお願いします。",
				context);

		try {
			return openAIClient.generateSupportMessage(prompt);
		} catch (Exception e) {
			// APIエラー時はプリセットメッセージを返す
			return getFallbackMessage();
		}
	}

	/**
	 * プリセットされた応援メッセージからランダムに選択
	 * @param messageType メッセージの種類（0: 成功時, 1: 困難時, 2: 日常）
	 * @return 選択された応援メッセージ
	 */
	public String getPresetMessage(int messageType) {
		messageType = Math.min(Math.max(messageType, 0), SUPPORT_MESSAGES.length - 1);
		int index = random.nextInt(SUPPORT_MESSAGES[messageType].length);
		return SUPPORT_MESSAGES[messageType][index];
	}

	/**
	 * フォールバック用のメッセージを取得
	 */
	private String getFallbackMessage() {
		return getPresetMessage(2); // 日常的なメッセージを返す
	}

	/**
	 * ユーザーの状況に基づいて適切なメッセージを生成
	 * @param context ユーザーの状況
	 * @param useAI AIを使用するかどうか
	 * @return 応援メッセージ
	 */
	public String generateSupportMessage(String context, boolean useAI) {
		try {
			if (useAI) {
				return generateAIMessage(context);
			}
		} catch (Exception e) {
			// AIでのメッセージ生成に失敗した場合
			e.printStackTrace();
		}

		// コンテキストに基づいてメッセージタイプを決定
		int messageType = determineMessageType(context);
		return getPresetMessage(messageType);
	}

	/**
	 * コンテキストからメッセージタイプを決定
	 * @param context ユーザーの状況
	 * @return メッセージタイプ
	 */
	private int determineMessageType(String context) {
		// 成功キーワード
		if (context.contains("成功") || context.contains("達成") || context.contains("できた")) {
			return 0; // 成功時のメッセージ
		}
		// 困難キーワード
		else if (context.contains("難しい") || context.contains("つらい") || context.contains("失敗")) {
			return 1; // 困難時のメッセージ
		}
		// それ以外
		return 2; // 日常的なメッセージ
	}
}