package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.slf4j.LoggerFactory;

import util.DBUtil;

/**
 * 日替わりモンスター生成のスケジューラークラス
 * 毎日0時にモンスターが生成されていない場合、プリセットモンスターを生成する
 */
public class DailyMonsterScheduler {
	private static final Logger logger = LoggerFactory.getLogger(DailyMonsterScheduler.class);

	/**
	 * プリセットモンスターの定義
	 * {名前, 画像パス, 最小HP, 最大HP}
	 */
	private static final String[][] PRESET_MONSTERS = {
			{ "おさけドラゴン", "/monsters/sake-dragon.png", "150", "200",
					"酒瓶から生まれた赤いドラゴン。水に触れると弱くなる。" },
			{ "ビアゴースト", "/monsters/beer-ghost.png", "100", "180",
					"ビールの泡から生まれた幽霊。炭酸が抜けると力が弱まる。" },
			{ "サケマンダー", "/monsters/sake-mander.png", "120", "190",
					"日本酒に住む妖精。冷やすと眠くなる特徴がある。" },
			{ "ワインプス", "/monsters/wine-imp.png", "110", "170",
					"ワイン樽に住む小鬼。水を注ぐと薄くなって弱体化する。" },
			{ "ウィスキーウルフ", "/monsters/whiskey-wolf.png", "130", "210",
					"ウイスキーの香りを纏った狼。水で割ると大人しくなる。" }
	};

	private final Random random;

	/**
	 * コンストラクタ
	 */
	public DailyMonsterScheduler() {
		this.random = new Random();
	}

	/**
	 * 本日のモンスターが既に存在するか確認
	 * @return モンスターが存在する場合true
	 * @throws SQLException データベースエラー時
	 */
	public boolean checkDailyMonsterExists() throws SQLException {
		String sql = "SELECT COUNT(*) FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			return rs.next() && rs.getInt(1) > 0;
		} catch (SQLException e) {
			logger.error("モンスターの存在確認中にエラーが発生しました", e);
			throw e;
		}
	}

	/**
	 * プリセットモンスターをランダムに生成
	 * @throws SQLException データベースエラー時
	 */
	public void generatePresetMonster() throws SQLException {
		if (checkDailyMonsterExists()) {
			logger.info("本日のモンスターは既に存在します");
			return;
		}

		// トランザクション開始
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			// ランダムにモンスターを選択
			int index = random.nextInt(PRESET_MONSTERS.length);
			String[] monster = PRESET_MONSTERS[index];

			// モンスターの登録
			String sql = "INSERT INTO monsters (name, image_path, hp, spawn_date, defeated, description) " +
					"VALUES (?, ?, ?, CURRENT_DATE, false, ?)";

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, monster[0]); // name
				ps.setString(2, monster[1]); // imagePath

				// HPをランダムに設定
				int minHp = Integer.parseInt(monster[2]);
				int maxHp = Integer.parseInt(monster[3]);
				int hp = minHp + random.nextInt(maxHp - minHp + 1);
				ps.setInt(3, hp);

				ps.setString(4, monster[4]); // description

				ps.executeUpdate();
			}

			conn.commit();
			logger.info("モンスター '{}' を生成しました", monster[0]);

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					logger.error("ロールバック中にエラーが発生しました", ex);
				}
			}
			logger.error("モンスターの生成中にエラーが発生しました", e);
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("データベース接続のクローズに失敗しました", e);
				}
			}
		}
	}

	/**
	 * スケジューラーのメインメソッド
	 */
	public static void main(String[] args) {
		DailyMonsterScheduler scheduler = new DailyMonsterScheduler();
		try {
			scheduler.generatePresetMonster();
		} catch (Exception e) {
			logger.error("スケジューラーの実行中にエラーが発生しました", e);
			System.exit(1);
		}
	}
}