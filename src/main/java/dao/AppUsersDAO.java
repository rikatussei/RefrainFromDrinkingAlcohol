package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.AppUsersDTO;

public class AppUsersDAO {
	//DBの接続情報 ※DB_URL部分のポート番号(5432)とデータベース名(0816jdbc)は必要に応じて書き換える
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/0816jdbc";
	private static final String DB_USER = "postgres";
	private static final String DB_PASS = "password";

	/**
	 * loginメソッド
	 * usersテーブルにデータを表で1件だけ取得する
	 * 引数にUsersDTO型が必要
	 * 戻り値はUsersDTO型
	 **/
	public AppUsersDTO login(AppUsersDTO login) {
		//変数dtoをnullで定義 
		AppUsersDTO dto = null;

		//try-with-resouce文()内の変数connは処理終了後に自動で閉じられる
		//テーブル情報を反映
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
			//SELECT 文の準備(?はnameとpasswordの2つ) 

			String sql = "SELECT id, login_id, password, name, created_at, deleted_at, is_admin "
					+ "FROM appusers "
					+ "WHERE login_id = ? AND password = ? AND deleted_at IS NULL";

			PreparedStatement ps = conn.prepareStatement(sql);

			//?のセット
			ps.setString(1, login.getLoginId());
			ps.setString(2, login.getPassword());

			//SQL の実行 
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					dto = new AppUsersDTO();
					dto.setId(rs.getInt("id"));
					dto.setLoginId(rs.getString("login_id"));
					dto.setName(rs.getString("name"));
					dto.setPassword(rs.getString("password"));
					dto.setCreatedAt(rs.getTimestamp("created_at"));
					dto.setDeletedAt(rs.getTimestamp("deleted_at"));
					dto.setAdmin(rs.getBoolean("is_admin"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラーが発生しました", e);
		}
		return dto;
	}

	/**
	 * 新規ユーザーを登録するメソッド
	 * @param user 登録するユーザー情報
	 * @return 登録成功時は1、失敗時は0
	 */
	public int register(AppUsersDTO user) {
		String sql = "INSERT INTO appusers (login_id, password, name, created_at) "
				+ "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラーが発生しました", e);
		}
	}

	/**
	 * ログインIDの重複をチェックするメソッド
	 * @param loginId チェックするログインID
	 * @return 重複している場合はtrue
	 */
	public boolean isLoginIdExists(String loginId) {
		String sql = "SELECT COUNT(*) FROM appusers WHERE login_id = ? AND deleted_at IS NULL";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, loginId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラーが発生しました", e);
		}
		return false;
	}

	/**
	 * ユーザーを論理削除するメソッド
	 * @param userId 削除するユーザーのID
	 * @return 削除成功時は1、失敗時は0
	 */
	public int deleteUser(int userId) {
		String sql = "UPDATE appusers SET deleted_at = CURRENT_TIMESTAMP "
				+ "WHERE id = ? AND deleted_at IS NULL";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラーが発生しました", e);

		}

	}

	/**
	 * ユーザー情報を更新するメソッド
	 * @param user 更新するユーザー情報
	 * @return 更新成功時は1、失敗時は0
	 */
	public int updateUser(AppUsersDTO user) {
		String sql = "UPDATE appusers SET name = ? ";

		// パスワードが設定されている場合は更新に含める
		if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
			sql += ", password = ? ";
		}
		sql += "WHERE id = ? AND deleted_at IS NULL";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			int paramIndex = 1;
			ps.setString(paramIndex++, user.getName());

			if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
				ps.setString(paramIndex++, user.getPassword());
			}

			ps.setInt(paramIndex, user.getId());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("データベースエラーが発生しました", e);
		}
	}
}