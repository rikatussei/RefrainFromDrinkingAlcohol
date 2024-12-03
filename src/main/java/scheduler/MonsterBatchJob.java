package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import dto.MonstersDTO;
import service.MonsterService;

public class MonsterBatchJob {
	public static void main(String[] args) {
		try {
			MonsterService monsterService = new MonsterService();
			MonstersDTO monster = monsterService.generateMonster();

			// データベースに接続
			try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/0816jdbc", "postgres",
					"password")) {
				String sql = "INSERT INTO monsters (name, hp, spawn_date, image_path, defeated) VALUES (?, ?, CURRENT_DATE, ?, false)";
				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, monster.getName());
					ps.setInt(2, monster.getHp());
					ps.setBytes(3, monster.getImageData());
					ps.executeUpdate();
				}
			}

			System.out.println("モンスターが生成されました！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
