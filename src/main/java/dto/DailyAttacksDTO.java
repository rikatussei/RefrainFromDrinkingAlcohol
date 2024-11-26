package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * DTO（Data Transfer Object）クラス
 * このクラスは攻撃情報（daily_attacksテーブルのデータ）を表現します。
 * DAO（Data Access Object）でデータベースとのやり取りを行う際に使用されます。
 */
public class DailyAttacksDTO implements Serializable {
	// シリアライズID。DTOクラスをSerializableにすることでデータの直列化が可能になります。
	private static final long serialVersionUID = 1L;

	// フィールド（テーブルのカラムに対応する変数）
	private int id; // 主キー
	private Integer userId; // 攻撃を行ったユーザーのID（NULLを許可）
	private int monsterId; // 攻撃対象のモンスターのID
	private Date attackDate; // 攻撃が行われた日付
	private boolean drinking; // 飲酒の有無（true: 飲酒した, false: 飲酒していない）
	private String comment; // 飲酒に関する感想（NULLを許可）
	private int damage; // ダメージ量
	private Timestamp attackAt; // 攻撃が行われた日時

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
	 * 攻撃日を取得します。
	 * 
	 * @return 攻撃日（Date）
	 */
	public Date getAttackDate() {
		return attackDate;
	}

	/**
	 * 攻撃日を設定します。
	 * 
	 * @param attackDate 攻撃日（Date）
	 */
	public void setAttackDate(Date attackDate) {
		this.attackDate = attackDate;
	}

	/**
	 * 飲酒の有無を取得します。
	 * 
	 * @return 飲酒した場合はtrue、それ以外はfalse
	 */
	public boolean isDrinking() {
		return drinking;
	}

	/**
	 * 飲酒の有無を設定します。
	 * 
	 * @param drinking 飲酒した場合はtrue、それ以外はfalse
	 */
	public void setDrinking(boolean drinking) {
		this.drinking = drinking;
	}

	/**
	 * 感想コメントを取得します。
	 * 
	 * @return 感想コメント（文字列）またはNULL
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 感想コメントを設定します。
	 * 
	 * @param comment 感想コメント（文字列）またはNULL
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * ダメージ量を取得します。
	 * 
	 * @return ダメージ量（整数）
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * ダメージ量を設定します。
	 * 
	 * @param damage ダメージ量（整数）
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * 攻撃日時を取得します。
	 * 
	 * @return 攻撃日時（Timestamp）
	 */
	public Timestamp getAttackAt() {
		return attackAt;
	}

	/**
	 * 攻撃日時を設定します。
	 * 
	 * @param attackAt 攻撃日時（Timestamp）
	 */
	public void setAttackAt(Timestamp attackAt) {
		this.attackAt = attackAt;
	}

	// オブジェクトの状態を文字列として返すためのメソッド（デバッグやログ出力に便利）
	@Override
	public String toString() {
		return "DailyAttacksDTO [id=" + id + ", userId=" + userId + ", monsterId=" + monsterId
				+ ", attackDate=" + attackDate + ", drinking=" + drinking + ", comment=" + comment
				+ ", damage=" + damage + ", attackAt=" + attackAt + "]";
	}
}
