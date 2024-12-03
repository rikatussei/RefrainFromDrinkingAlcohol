package service;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dto.MonstersDTO;
import util.OpenAIClient;

public class MonsterService {
    private OpenAIClient openAIClient = new OpenAIClient();

    // モンスターを生成して保存する
    public void generateAndSaveMonster(Connection connection) throws Exception {
        // モンスター名を生成
        String monsterName = openAIClient.generateMonsterName();

        // モンスター画像を生成
        byte[] imageData = openAIClient.generateMonsterImage(monsterName);

        // データベースに保存
        String insertSQL = "INSERT INTO monsters (name, image_path, hp, spawn_date, defeated) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setString(1, monsterName);
            ps.setBinaryStream(2, new ByteArrayInputStream(imageData)); // 画像データを保存
            ps.setInt(3, 100); // HP（仮の値）
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis())); // 今日の日付
            ps.setBoolean(5, false); // 初期状態は未撃破
            ps.executeUpdate();
        }
    }

	public MonstersDTO generateMonster() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}

