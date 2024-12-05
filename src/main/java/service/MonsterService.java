package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import dto.MonstersDTO;
import scheduler.DailyMonsterScheduler;
import util.DBUtil;
import util.OpenAIClient;

/**
 * モンスター関連のビジネスロジックを提供するサービスクラス
 */
public class MonsterService {
	private static final Logger logger = LoggerFactory.getLogger(MonsterService.class);
	private final OpenAIClient openAIClient;
	private final DailyMonsterScheduler scheduler;
	private static final int MAX_RETRY_COUNT = 3;

	/**
	 * コンストラクタ
	 */
	public MonsterService() {
		this.openAIClient = new OpenAIClient();
		this.scheduler = new DailyMonsterScheduler();
	}

	/**
	 * 日替わりモンスターを取得または生成
	 * @return 生成されたモンスター情報
	 * @throws Exception 処理中のエラー
	 */
	public MonstersDTO getDailyMonster() throws Exception {
		// 既存のモンスターを確認
		MonstersDTO currentMonster = getCurrentMonster();
		if (currentMonster != null) {
			return currentMonster;
		}

		// APIでの生成を試行
		for (int i = 0; i < MAX_RETRY_COUNT; i++) {
			try {
				MonstersDTO monster = generateMonsterWithAPI();
				if (monster != null) {
					return monster;
				}
			} catch (Exception e) {
				logger.warn("API呼び出しに失敗しました。リトライ {}/{}", i + 1, MAX_RETRY_COUNT, e);
			}
		}

		// API生成失敗時はプリセットモンスターを生成
		logger.info("APIでの生成に失敗したため、プリセットモンスターを生成します");
		scheduler.generatePresetMonster();
		return getCurrentMonster();
	}

	/**
	 * 現在のモンスター情報を取得
	 * @return モンスター情報、存在しない場合はnull
	 * @throws SQLException データベースエラー時
	 */
	public MonstersDTO getCurrentMonster() throws SQLException {
		String sql = "SELECT * FROM monsters WHERE spawn_date = CURRENT_DATE";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return createMonsterFromResultSet(rs);
			}
		}
		return null;
	}

	/**
	 * APIを使用してモンスターを生成
	 * @return 生成されたモンスター情報
	 * @throws Exception 生成処理中のエラー
	 */
	private MonstersDTO generateMonsterWithAPI() throws Exception {
		// モンスター名の生成
		String monsterName = openAIClient.generateMonsterName();

		// 画像生成
		byte[] imageData = openAIClient.generateMonsterImage(monsterName);

		// データベースに保存
		return saveMonster(monsterName, imageData);
	}

	/**
	 * モンスター情報をデータベースに保存
	 * @param name モンスター名
	 * @param imageData 画像データ
	 * @return 保存されたモンスター情報
	 * @throws SQLException データベースエラー時
	 */
	private MonstersDTO saveMonster(String name, byte[] imageData) throws SQLException {
		String sql = "INSERT INTO monsters (name, image_data, hp, spawn_date, defeated) " +
				"VALUES (?, ?, ?, CURRENT_DATE, false) RETURNING *";

		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, name);
				ps.setBytes(2, imageData);
				ps.setInt(3, 100 + new java.util.Random().nextInt(156)); // HP: 100-255

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						conn.commit();
						return createMonsterFromResultSet(rs);
					}
				}
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
		return null;
	}

	/**
	 * ResultSetからMonstersDTOを作成
	 * @param rs データベースの結果セット
	 * @return モンスターDTO
	 * @throws SQLException データベースエラー時
	 */
	private MonstersDTO createMonsterFromResultSet(ResultSet rs) throws SQLException {
		MonstersDTO monster = new MonstersDTO();
		monster.setId(rs.getInt("id"));
		monster.setName(rs.getString("name"));
		monster.setHp(rs.getInt("hp"));
		monster.setSpawnDate(rs.getDate("spawn_date"));
		monster.setDefeated(rs.getBoolean("defeated"));
		monster.setImageData(rs.getBytes("image_data"));
		monster.setCreatedAt(rs.getTimestamp("created_at"));
		monster.setUpdatedAt(rs.getTimestamp("updated_at"));
		return monster;
	}
}