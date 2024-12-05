package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import util.DBUtil;

/**
 * コメント機能のビジネスロジックを提供するサービスクラス
 */
public class CommentService {

	/**
	 * 新しいコメントを追加
	 * @param userId ユーザーID
	 * @param monsterId モンスターID
	 * @param text コメント内容
	 * @return 保存されたコメント情報
	 * @throws SQLException データベースエラー
	 */
	public JSONObject addComment(int userId, int monsterId, String text) throws SQLException {
		String sql = "INSERT INTO cheers (user_id, monster_id, text, comment_type, created_at) " +
				"VALUES (?, ?, ?, 'USER', CURRENT_TIMESTAMP) " +
				"RETURNING id, created_at, " +
				"(SELECT name FROM appusers WHERE id = ?) as user_name";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.setInt(2, monsterId);
			ps.setString(3, text);
			ps.setInt(4, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					JSONObject comment = new JSONObject();
					comment.put("id", rs.getInt("id"));
					comment.put("text", text);
					comment.put("userId", userId);
					comment.put("userName", rs.getString("user_name"));
					comment.put("createdAt", rs.getTimestamp("created_at").getTime());
					return comment;
				}
				throw new SQLException("コメントの作成に失敗しました");
			}
		}
	}

	/**
	 * コメントを削除
	 * @param commentId コメントID
	 * @param userId 削除を要求したユーザーのID
	 * @return 削除が成功したかどうか
	 * @throws SQLException データベースエラー
	 */
	public boolean deleteComment(int commentId, int userId) throws SQLException {
		// 論理削除のSQL
		String sql = "UPDATE cheers " +
				"SET deleted_at = CURRENT_TIMESTAMP " +
				"WHERE id = ? AND user_id = ? AND deleted_at IS NULL";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, commentId);
			ps.setInt(2, userId);

			int updatedRows = ps.executeUpdate();
			return updatedRows > 0;
		}
	}

	/**
	 * 特定のモンスターへのコメントを取得
	 * @param monsterId モンスターID
	 * @return コメントのJSONObject配列
	 * @throws SQLException データベースエラー
	 */
	public JSONObject[] getCommentsByMonsterId(int monsterId) throws SQLException {
		String sql = "SELECT c.*, u.name as user_name " +
				"FROM cheers c " +
				"JOIN appusers u ON c.user_id = u.id " +
				"WHERE c.monster_id = ? AND c.deleted_at IS NULL " +
				"ORDER BY c.created_at DESC";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, monsterId);

			try (ResultSet rs = ps.executeQuery()) {
				// 結果を配列に格納
				java.util.List<JSONObject> comments = new java.util.ArrayList<>();

				while (rs.next()) {
					JSONObject comment = new JSONObject();
					comment.put("id", rs.getInt("id"));
					comment.put("text", rs.getString("text"));
					comment.put("userId", rs.getInt("user_id"));
					comment.put("userName", rs.getString("user_name"));
					comment.put("createdAt", rs.getTimestamp("created_at").getTime());
					comments.add(comment);
				}

				return comments.toArray(new JSONObject[0]);
			}
		}
	}

	/**
	 * コメントが特定のユーザーのものかどうかを確認
	 * @param commentId コメントID
	 * @param userId ユーザーID
	 * @return ユーザーのコメントの場合true
	 * @throws SQLException データベースエラー
	 */
	public boolean isCommentOwner(int commentId, int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM cheers WHERE id = ? AND user_id = ?";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, commentId);
			ps.setInt(2, userId);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		}
	}
}