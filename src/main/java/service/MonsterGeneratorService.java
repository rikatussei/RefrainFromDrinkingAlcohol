package service;

import java.io.IOException;
import java.util.Random;

import dto.MonstersDTO;
import util.ImageOptimizer;
import util.OpenAIClient;

/**
 * モンスター生成を管理するサービスクラス
 */
public class MonsterGeneratorService {
	private final OpenAIClient openAIClient;
	private final Random random;

	// モンスタープロンプトのテンプレート
	private static final String[] MONSTER_PROMPTS = {
			"A cute chibi dragon monster with rosy cheeks, made of crystalline sake drops. " +
					"Soft pastel colors, round shapes, and a gentle expression. Small size (256x256), " +
					"anime style, encouraging sobriety through its transformation from sake to water.",

			"A small ghost-like monster made of beer foam, with cute bubble eyes and a gentle smile. " +
					"Pastel yellow colors, fluffy texture. Small size (256x256), kawaii style, " +
					"transforms into a water spirit when defeated.",

			"A tiny salamander monster with sake-bottle patterns, soft pink colors and a friendly expression. " +
					"Chibi style with round features. Small size (256x256), includes water droplet elements " +
					"to promote hydration."
	};

	// モンスター名のリスト
	private static final String[] MONSTER_NAMES = {
			"おさけドラゴン",
			"ビアゴースト",
			"サケマンダー"
	};

	public MonsterGeneratorService() {
		this.openAIClient = new OpenAIClient();
		this.random = new Random();
	}

	/**
	 * 新しいモンスターを生成
	 */
	public MonstersDTO generateMonster() throws IOException, InterruptedException {
		int index = random.nextInt(MONSTER_PROMPTS.length);
		String prompt = MONSTER_PROMPTS[index];
		String name = MONSTER_NAMES[index];

		// 画像生成
		byte[] rawImageData = openAIClient.generateMonsterImage(prompt);
		byte[] optimizedImage = ImageOptimizer.optimizeImage(rawImageData);

		// DTOの作成
		MonstersDTO monster = new MonstersDTO();
		monster.setName(name);
		monster.setHp(100 + random.nextInt(156)); // 100-255のHP
		monster.setImageData(optimizedImage);

		return monster;
	}
}