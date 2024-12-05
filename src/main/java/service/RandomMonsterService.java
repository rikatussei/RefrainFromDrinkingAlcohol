package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import dto.MonstersDTO;
import util.DBUtil;

/**
 * ランダムなモンスター生成と管理を行うサービスクラス
 */
public class RandomMonsterService {
	// 事前定義されたモンスター名の配列
	private static final String[] PREDEFINED_MONSTERS = {
			"おさけドラゴン", // 火属性の飲酒ドラゴン
			"ビアゴースト", // 風属性のビール幽霊
			"サケマンダー", // 水属性の日本酒サラマンダー
			"ワインプス", // 光属性のワイン妖精
			"ウィスキーウルフ" // 地属性のウィスキー狼
	};

	// モンスターのHP範囲
	private static final int MIN_HP = 100; // 最小HP
	private static final int MAX_HP = 200; // 最大HP

	/**
	 * 現在のモンスター情報を取得する
	 * @return 本日のモンスター情報、存在しない場合はnull
	 * @throws SQLException データベース処理中のエラー
	 */
	public MonstersDTO getCurrentMonster() throws SQLException {
		String sql = "SELECT * FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				MonstersDTO monster = new MonstersDTO();
				monster.setId(rs.getInt("id"));
				monster.setName(rs.getString("name"));
				monster.setHp(rs.getInt("hp"));
				monster.setSpawnDate(rs.getDate("spawn_date"));
				monster.setDefeated(rs.getBoolean("defeated"));
				monster.setImagePath(rs.getString("image_path"));
				monster.setCreatedAt(rs.getTimestamp("created_at"));
				monster.setUpdatedAt(rs.getTimestamp("updated_at"));
				return monster;
			}
			return null;
		}
	}

	/**
	 * 本日のモンスターを生成する必要があるかチェック
	 * @return モンスター生成が必要な場合true
	 * @throws SQLException データベース処理中のエラー
	 */
	public boolean shouldGenerateMonster() throws SQLException {
		String sql = "SELECT COUNT(*) FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1) == 0; // モンスターが存在しない場合true
			}
			return true;
		}
	}

	/**
	 * ランダムなモンスターを生成してデータベースに登録
	 * @throws SQLException データベース処理中のエラー
	 */
	public void generateRandomMonster() throws SQLException {
		// 既にモンスターが存在する場合は生成しない
		if (!shouldGenerateMonster()) {
			return;
		}

		Random random = new Random();
		// ランダムなモンスター名を選択
		String monsterName = PREDEFINED_MONSTERS[random.nextInt(PREDEFINED_MONSTERS.length)];
		// ランダムなHPを生成
		int hp = random.nextInt(MAX_HP - MIN_HP + 1) + MIN_HP;

		String sql = "INSERT INTO monsters (name, hp, spawn_date, defeated, image_path, created_at, updated_at) " +
				"VALUES (?, ?, CURRENT_DATE, false, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, monsterName);
			ps.setInt(2, hp);
			ps.setString(3, "monsters/" + monsterName.toLowerCase().replace(" ", "_") + ".svg");
			ps.executeUpdate();
		}
	}

	/**
	 * モンスターのHPを更新
	 * @param monsterId モンスターID
	 * @param damage 与えるダメージ
	 * @return モンスターが倒されたかどうか
	 * @throws SQLException データベース処理中のエラー
	 */
	public boolean updateMonsterHp(int monsterId, int damage) throws SQLException {
		String sql = "UPDATE monsters SET " +
				"hp = GREATEST(0, hp - ?), " +
				"defeated = (hp - ?) <= 0, " +
				"updated_at = CURRENT_TIMESTAMP " +
				"WHERE id = ? " +
				"RETURNING defeated";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, damage);
			ps.setInt(2, damage);
			ps.setInt(3, monsterId);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getBoolean("defeated");
			}
		}
	}

	/**
	 * 倒されたモンスターの履歴を取得
	 * @param userId ユーザーID
	 * @return 倒したモンスターの数
	 * @throws SQLException データベース処理中のエラー
	 */
	public int getDefeatedMonstersCount(int userId) throws SQLException {
		String sql = "SELECT COUNT(DISTINCT m.id) " +
				"FROM monsters m " +
				"JOIN daily_attacks da ON m.id = da.monster_id " +
				"WHERE m.defeated = true AND da.user_id = ?";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		}
	}
}