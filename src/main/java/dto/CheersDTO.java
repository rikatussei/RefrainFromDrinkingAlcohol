package dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO（Data Transfer Object）クラス
 * このクラスは応援コメント情報（cheersテーブルのデータ）を表現します。
 * DAO（Data Access Object）でデータベースとのやり取りを行う際に使用します。
 */
public class CheersDTO implements Serializable {
	// シリアライズID。DTOクラスをSerializableにすることでデータの直列化が可能になります。
	private static final long serialVersionUID = 1L;

	// フィールド（テーブルのカラムに対応する変数）
	private int id; // 主キー
	private String text; // 応援コメントの内容
	private String commentType; // コメントの種類（例: "user", "gpt"）
	private String userComment; // ユーザーが入力した応援コメント
	private String aiComment; // AI（ChatGPT）が生成した応援コメント
	private Integer userId; // コメントを投稿したユーザーのID（NULLを許可）
	private int monsterId; // コメントが関連するモンスターのID
	private Timestamp createdAt; // コメントの作成日時
	private Timestamp updatedAt; // コメントの更新日時
	private Timestamp deletedAt; // コメントが削除された日時（NULLを許可）

	// ゲッター（フィールドの値を取得するメソッド）

	/**
	 * IDを取得します。
	 * 
	 * @return ID（整数）
	 */
	public int getId() {
		return id;
	}

	/**
	 * IDを設定します。
	 * 
	 * @param id ID（整数）
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 応援コメントの内容を取得します。
	 * 
	 * @return コメント内容（文字列）
	 */
	public String getText() {
		return text;
	}

	/**
	 * 応援コメントの内容を設定します。
	 * 
	 * @param text コメント内容（文字列）
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * コメントの種類を取得します。
	 * 
	 * @return コメント種類（文字列）
	 */
	public String getCommentType() {
		return commentType;
	}

	/**
	 * コメントの種類を設定します。
	 * 
	 * @param commentType コメント種類（文字列）
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	/**
	 * ユーザーコメントを取得します。
	 * 
	 * @return ユーザーコメント（文字列）
	 */
	public String getUserComment() {
		return userComment;
	}

	/**
	 * ユーザーコメントを設定します。
	 * 
	 * @param userComment ユーザーコメント（文字列）
	 */
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	/**
	 * AIコメントを取得します。
	 * 
	 * @return AIコメント（文字列）
	 */
	public String getAiComment() {
		return aiComment;
	}

	/**
	 * AIコメントを設定します。
	 * 
	 * @param aiComment AIコメント（文字列）
	 */
	public void setAiComment(String aiComment) {
		this.aiComment = aiComment;
	}

	/**
	 * ユーザーIDを取得します。
	 * 
	 * @return ユーザーID（整数）またはNULL
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * ユーザーIDを設定します。
	 * 
	 * @param userId ユーザーID（整数）またはNULL
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * モンスターIDを取得します。
	 * 
	 * @return モンスターID（整数）
	 */
	public int getMonsterId() {
		return monsterId;
	}

	/**
	 * モンスターIDを設定します。
	 * 
	 * @param monsterId モンスターID（整数）
	 */
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}

	/**
	 * 作成日時を取得します。
	 * 
	 * @return 作成日時（Timestamp）
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * 作成日時を設定します。
	 * 
	 * @param createdAt 作成日時（Timestamp）
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 更新日時を取得します。
	 * 
	 * @return 更新日時（Timestamp）
	 */
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * 更新日時を設定します。
	 * 
	 * @param updatedAt 更新日時（Timestamp）
	 */
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * 削除日時を取得します。
	 * 
	 * @return 削除日時（Timestamp）またはNULL
	 */
	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	/**
	 * 削除日時を設定します。
	 * 
	 * @param deletedAt 削除日時（Timestamp）またはNULL
	 */
	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	// オブジェクトの状態を文字列として返すためのメソッド（デバッグやログ出力に便利）
	@Override
	public String toString() {
		return "CheersDTO [id=" + id + ", text=" + text + ", commentType=" + commentType
				+ ", userComment=" + userComment + ", aiComment=" + aiComment
				+ ", userId=" + userId + ", monsterId=" + monsterId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", deletedAt=" + deletedAt + "]";
	}
}
