package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.BattleResultDTO;
import dto.MonstersDTO;
import util.DBUtil;
import util.OpenAIClient;

public class BattleService {
	private final OpenAIClient openAIClient = new OpenAIClient();
	private static final int BASE_ATTACK = 10;
	private static final double DRINK_PENALTY = 0.5;
	private static final double NO_DRINK_BONUS = 2.0;

	public BattleResultDTO processAttack(int userId, boolean didDrink, String comment)
			throws SQLException, IOException {
		// 攻撃可能かチェック
		if (!canUserAttack(userId)) {
			throw new IllegalStateException("本日の攻撃は既に実行済みです");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			// 感情分析と攻撃力計算
			double sentimentScore = openAIClient.analyzeSentiment(comment);
			int attackPower = calculateAttackPower(didDrink, sentimentScore);

			// 攻撃を記録
			recordAttack(conn, userId, didDrink, comment, attackPower);

			// モンスターのHPを更新し、撃破判定を取得
			boolean isDefeated = updateMonsterHP(conn, attackPower);

			// 撃破時はトークンを発行
			if (isDefeated) {
				issueToken(conn, userId);
			}

			conn.commit();

			// 戦闘結果を返す
			return new BattleResultDTO(attackPower, isDefeated, sentimentScore);
		} catch (SQLException | IOException e) {
			if (conn != null) {
				conn.rollback();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	private boolean canUserAttack(int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM daily_attacks " +
				"WHERE user_id = ? AND attack_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) == 0;
				}
			}
		}
		return true;
	}

	private int calculateAttackPower(boolean didDrink, double sentimentScore) {
		double multiplier = didDrink ? DRINK_PENALTY : NO_DRINK_BONUS;
		double sentimentBonus = sentimentScore * 10;
		return (int) Math.floor(BASE_ATTACK * multiplier + sentimentBonus);
	}

	private void recordAttack(Connection conn, int userId, boolean didDrink,
			String comment, int damage) throws SQLException {
		String sql = "INSERT INTO daily_attacks (user_id, monster_id, attack_date, " +
				"drinking, comment, damage, attack_at) " +
				"SELECT ?, id, CURRENT_DATE, ?, ?, ?, CURRENT_TIMESTAMP " +
				"FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setBoolean(2, didDrink);
			ps.setString(3, comment);
			ps.setInt(4, damage);
			ps.executeUpdate();
		}
	}

	private boolean updateMonsterHP(Connection conn, int damage) throws SQLException {
		String sql = "UPDATE monsters SET " +
				"hp = GREATEST(0, hp - ?), " +
				"defeated = (hp - ?) <= 0, " +
				"updated_at = CURRENT_TIMESTAMP " +
				"WHERE spawn_date = CURRENT_DATE " +
				"RETURNING defeated";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, damage);
			ps.setInt(2, damage);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getBoolean("defeated");
			}
		}
	}

	private void issueToken(Connection conn, int userId) throws SQLException {
		String sql = "INSERT INTO tokens (user_id, monster_id, issued_at) " +
				"SELECT ?, id, CURRENT_TIMESTAMP " +
				"FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		}
	}

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
				monster.setDefeated(rs.getBoolean("defeated"));
				monster.setImagePath(rs.getString("image_path"));
				monster.setCreatedAt(rs.getTimestamp("created_at"));
				monster.setUpdatedAt(rs.getTimestamp("updated_at"));
				return monster;
			}
		}
		return null;
	}

	public List<BattleResultDTO> getBattleHistory(int userId) throws SQLException {
		String sql = "SELECT da.*, m.name as monster_name " +
				"FROM daily_attacks da " +
				"JOIN monsters m ON da.monster_id = m.id " +
				"WHERE da.user_id = ? " +
				"ORDER BY da.attack_date DESC";

		List<BattleResultDTO> history = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BattleResultDTO result = new BattleResultDTO();
					result.setDamage(rs.getInt("damage"));
					result.setDrinking(rs.getBoolean("drinking"));
					result.setComment(rs.getString("comment"));
					result.setAttackDate(rs.getDate("attack_date"));
					result.setMonsterName(rs.getString("monster_name"));
					history.add(result);
				}
			}
		}
		return history;
	}
}