package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import dto.BattleResultDTO;
import util.DBUtil;

public class BattleService extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final int BASE_ATTACK = 10;
   private static final double DRINKING_PENALTY = 0.5;
   private static final double NO_DRINKING_BONUS = 1.5;

	public BattleResultDTO processAttack(int userId, boolean didDrink, String comment)
			throws SQLException {
		if (!canUserAttack(userId)) {
			throw new IllegalStateException("本日の攻撃は既に実行済みです");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			int attackPower = calculateAttackPower(didDrink);
			recordAttack(conn, userId, didDrink, comment, attackPower);
			boolean isDefeated = updateMonsterHP(conn, attackPower);

			conn.commit();
			return new BattleResultDTO(attackPower, isDefeated, 0.5);
		} catch (SQLException e) {
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
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT COUNT(*) FROM daily_attacks WHERE user_id = ? AND attack_date = CURRENT_DATE")) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) == 0;
			}
		}
	}

	private int calculateAttackPower(boolean didDrink) {
		double multiplier = didDrink ? DRINKING_PENALTY : NO_DRINKING_BONUS;
		return (int) Math.floor(BASE_ATTACK * multiplier);
	}

	private void recordAttack(Connection conn, int userId, boolean didDrink,
			String comment, int damage) throws SQLException {
		String sql = "INSERT INTO daily_attacks (user_id, monster_id, attack_date, drinking, comment, damage) " +
				"SELECT ?, id, CURRENT_DATE, ?, ?, ? FROM monsters WHERE spawn_date = CURRENT_DATE";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setBoolean(2, didDrink);
			ps.setString(3, comment);
			ps.setInt(4, damage);
			ps.executeUpdate();
		}
	}

	private boolean updateMonsterHP(Connection conn, int damage) throws SQLException {
		String sql = "UPDATE monsters SET hp = GREATEST(0, hp - ?), " +
				"defeated = (hp - ?) <= 0 WHERE spawn_date = CURRENT_DATE RETURNING defeated";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, damage);
			ps.setInt(2, damage);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getBoolean("defeated");
			}
		}
	}
}