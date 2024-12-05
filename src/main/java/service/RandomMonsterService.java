package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import util.DBUtil;

public class RandomMonsterService {
	private static final String[] PREDEFINED_MONSTERS = {
			"おさけドラゴン", "ビアゴースト", "サケマンダー", "ワインプス", "ウィスキーウルフ"
	};

	private static final int MIN_HP = 100;
	private static final int MAX_HP = 200;

	public boolean shouldGenerateMonster() throws SQLException {
		String sql = "SELECT COUNT(*) FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1) == 0;
			}
			return true;
		}
	}

	public void generateRandomMonster() throws SQLException {
		if (!shouldGenerateMonster()) {
			return;
		}

		Random random = new Random();
		String monsterName = PREDEFINED_MONSTERS[random.nextInt(PREDEFINED_MONSTERS.length)];
		int hp = random.nextInt(MAX_HP - MIN_HP + 1) + MIN_HP;

		String sql = "INSERT INTO monsters (name, hp, spawn_date, defeated, image_path) " +
				"VALUES (?, ?, CURRENT_DATE, false, ?)";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, monsterName);
			ps.setInt(2, hp);
			ps.setString(3, "monsters/" + monsterName.toLowerCase() + ".svg");
			ps.executeUpdate();
		}
	}
}