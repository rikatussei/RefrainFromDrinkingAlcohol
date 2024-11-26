package dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ユーザー情報を管理するDTOクラス
 * 旧UsersDTO.javaからの主な変更点：
 * 1. パスワードのデータ型をintからStringに変更
 * 2. 新しいフィールド（loginId, createdAt, deletedAt, isAdmin）の追加
 * 3. コンストラクタの追加
 */
public class AppUsersDTO implements Serializable {
	// シリアライズバージョンUID
	private static final long serialVersionUID = 1L;

	// フィールド（テーブルのカラムに対応する変数）
	private int id; // ユーザーID (主キー)
	private String loginId; // ログインID (一意制約)
	private String password; // パスワード (VARCHAR)
	private String name; // ユーザー名
	private Timestamp createdAt; // 作成日時
	private Timestamp deletedAt; // 削除日時
	private boolean isAdmin; // 管理者権限

	/**
	 * デフォルトコンストラクタ
	 */
	public AppUsersDTO() {
		// 空のコンストラクタ
	}

	/**
	 * 名前とパスワードを指定して初期化するコンストラクタ
	 * 旧コードとの互換性のため残しています
	 * 
	 * @param name ユーザー名
	 * @param password パスワード
	 */
	public AppUsersDTO(String name, String password, String loginId) {
	    this.name = name;
	    this.password = password;
	    this.loginId = loginId; // loginId を初期化
	}
 
	/**
	 * 全フィールドを指定して初期化するコンストラクタ
	 * DBからのデータ取得時に使用
	 */
	public AppUsersDTO(int id, String loginId, String password, String name,
			Timestamp createdAt, Timestamp deletedAt, boolean isAdmin) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.isAdmin = isAdmin;
	}

	// ゲッターとセッター（既存のコードと同じ）
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * オブジェクトの内容を文字列で返す
	 * デバッグやログ出力時に使用
	 */
	@Override
	public String toString() {
		return "AppUsersDTO [id=" + id +
				", loginId=" + loginId +
				", name=" + name +
				", createdAt=" + createdAt +
				", deletedAt=" + deletedAt +
				", isAdmin=" + isAdmin + "]";
	}
}