// src/main/java/service/MonsterService.java

package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.MonstersDTO;
import dto.PresetMonsterDTO;
import util.DBUtil;

public class MonsterService {
	private final PresetMonsterManager presetManager = PresetMonsterManager.getInstance();

	public MonstersDTO generateDailyMonster(boolean force) throws SQLException {
		if (!force && isDailyMonsterExists()) {
			return getTodayMonster();
		}

		try {
			return generateMonsterWithAPI();
		} catch (Exception e) {
			return generatePresetMonster();
		}
	}

	private MonstersDTO generatePresetMonster() {
		try {
			PresetMonsterDTO preset = presetManager.getRandomMonster();
			byte[] imageData = presetManager.getMonsterImage(preset);

			MonstersDTO monster = new MonstersDTO();
			monster.setName(preset.getName());
			monster.setHp(preset.getBaseHp());
			monster.setImageData(imageData);
			monster.setDefeated(false);

			saveMonster(monster);
			return monster;
		} catch (Exception e) {
			throw new RuntimeException("プリセットモンスター生成失敗", e);
		}
	}

	private boolean isDailyMonsterExists() throws SQLException {
		String sql = "SELECT COUNT(*) FROM monsters WHERE DATE(spawn_date) = CURRENT_DATE";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			return rs.next() && rs.getInt(1) > 0;
		}
	}

	private MonstersDTO getTodayMonster() throws SQLException {
		String sql = "SELECT * FROM monsters WHERE DATE(spawn_date) = CURRENT_DATE";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return createMonsterFromResultSet(rs);
			}
		}
		return null;
	}

	private void saveMonster(MonstersDTO monster) throws SQLException {
		String sql = "INSERT INTO monsters (name, hp, image_data, spawn_date, defeated) VALUES (?, ?, ?, CURRENT_DATE, false)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, monster.getName());
			ps.setInt(2, monster.getHp());
			ps.setBytes(3, monster.getImageData());
			ps.executeUpdate();
		}
	}

	private MonstersDTO createMonsterFromResultSet(ResultSet rs) throws SQLException {
		MonstersDTO monster = new MonstersDTO();
		monster.setId(rs.getInt("id"));
		monster.setName(rs.getString("name"));
		monster.setHp(rs.getInt("hp"));
		monster.setImageData(rs.getBytes("image_data"));
		monster.setSpawnDate(rs.getDate("spawn_date"));
		monster.setDefeated(rs.getBoolean("defeated"));
		return monster;
	}
}