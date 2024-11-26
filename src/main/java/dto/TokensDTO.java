package dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO（Data Transfer Object）クラス
 * このクラスはトークン情報（tokensテーブルのデータ）を表現します。
 * DAO（Data Access Object）でデータベースとのやり取りを行う際に使用されます。
 */
public class TokensDTO implements Serializable {
	// シリアライズID。DTOクラスをSerializableにすることでデータの直列化が可能になります。
	private static final long serialVersionUID = 1L;

	// フィールド（テーブルのカラムに対応する変数）
	private int id; // トークンのID（主キー）
	private Integer userId; // トークンを獲得したユーザーのID（NULLを許可）
	private int monsterId; // トークンが関連するモンスターのID
	private Timestamp issuedAt; // トークンが発行された日時（NULLを許可）
	private boolean defeated; // トークンが使用済みかどうか（true: 使用済み, false: 未使用）

	// ゲッターとセッター（フィールドの値を取得・設定するメソッド）

	/**
	 * トークンIDを取得します。
	 * 
	 * @return トークンID（整数）
	 */
	public int getId() {
		return id;
	}

	/**
	 * トークンIDを設定します。
	 * 
	 * @param id トークンID（整数）
	 */
	public void setId(int id) {
		this.id = id;
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
	 * トークン発行日時を取得します。
	 * 
	 * @return トークン発行日時（Timestamp）またはNULL
	 */
	public Timestamp getIssuedAt() {
		return issuedAt;
	}

	/**
	 * トークン発行日時を設定します。
	 * 
	 * @param issuedAt トークン発行日時（Timestamp）またはNULL
	 */
	public void setIssuedAt(Timestamp issuedAt) {
		this.issuedAt = issuedAt;
	}

	/**
	 * トークンが使用済みかどうかを取得します。
	 * 
	 * @return 使用済みの場合はtrue、それ以外はfalse
	 */
	public boolean isDefeated() {
		return defeated;
	}

	/**
	 * トークンが使用済みかどうかを設定します。
	 * 
	 * @param defeated 使用済みの場合はtrue、それ以外はfalse
	 */
	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	// オブジェクトの状態を文字列として返すためのメソッド（デバッグやログ出力に便利）

	/**
	 * オブジェクトの状態を文字列形式で返します。
	 * 
	 * @return トークンDTOの内容を表す文字列
	 */
	@Override
	public String toString() {
		return "TokensDTO [id=" + id + ", userId=" + userId + ", monsterId=" + monsterId
				+ ", issuedAt=" + issuedAt + ", defeated=" + defeated + "]";
	}
}
