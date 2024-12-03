package service;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.MonstersDTO;
import util.OpenAIClient;

public class MonsterService {
	private OpenAIClient openAIClient = new OpenAIClient();

	// モンスターを生成して保存し、生成したモンスターを返す (Connection を渡す場合)
	public MonstersDTO generateMonster(Connection connection) throws Exception {
		String monsterName = openAIClient.generateMonsterName();
		byte[] imageData = openAIClient.generateMonsterImage(monsterName);

		String insertSQL = "INSERT INTO monsters (name, image_path, hp, spawn_date, defeated) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, monsterName);
			ps.setBinaryStream(2, new ByteArrayInputStream(imageData));
			ps.setInt(3, 100);
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setBoolean(5, false);
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					int generatedId = rs.getInt(1);

					MonstersDTO monster = new MonstersDTO();
					monster.setId(generatedId);
					monster.setName(monsterName);
					monster.setImageData(imageData);
					monster.setHp(100);
					return monster;
				}
			}
		}
		throw new Exception("モンスターの生成に失敗しました");
	}

	// Connection を内部で生成するオーバーロードメソッド
	public MonstersDTO generateMonster() throws Exception {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5433/0816jdbc", "postgres", "password")) {
			return generateMonster(conn);
		}
	}
}
