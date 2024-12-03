package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Timer;
import java.util.TimerTask;

import service.MonsterService;

public class MonsterBatchJob {
    public static void main(String[] args) {
        Timer timer = new Timer(true); // バックグラウンドで動作
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try (Connection connection = getConnection()) {
                    MonsterService monsterService = new MonsterService();
                    monsterService.generateAndSaveMonster(connection);
                    System.out.println("モンスターが生成されました。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 24 * 60 * 60 * 1000); // 毎日実行（24時間間隔）
    }

    // データベース接続を取得
    private static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/your_database", // DB接続情報を設定
                "your_username", // ユーザー名
                "your_password"  // パスワード
        );
    }
}
