package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import util.DBUtil;

public class CommentService {

	public JSONObject addComment(int userId, int monsterId, String text) throws SQLException {
		String sql = "INSERT INTO cheers (user_id, monster_id, text, comment_type, created_at) " +
				"VALUES (?, ?, ?, 'USER', CURRENT_TIMESTAMP) RETURNING id, created_at";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ps.setInt(2, monsterId);
			ps.setString(3, text);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					JSONObject comment = new JSONObject();
					comment.put("id", rs.getInt("id"));
					comment.put("text", text);
					comment.put("createdAt", rs.getTimestamp("created_at").getTime());
					return comment;
				}
				throw new SQLException("コメントの作成に失敗しました");
			}
		}
	}

	public boolean deleteComment(int commentId, int userId) throws SQLException {
		String sql = "DELETE FROM cheers WHERE id = ? AND user_id = ?";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, commentId);
			ps.setInt(2, userId);

			return ps.executeUpdate() > 0;
		}
	}
}