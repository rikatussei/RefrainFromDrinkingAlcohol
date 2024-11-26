package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * DTO（Data Transfer Object）クラス
 * このクラスはモンスター情報（monstersテーブルのデータ）を表現します。
 * DAO（Data Access Object）でデータベースとのやり取りを行う際に使用します。
 */
public class MonstersDTO implements Serializable {
	// シリアライズID。DTOクラスをSerializableにすることでデータの直列化が可能になります。
	private static final long serialVersionUID = 1L;

	// フィールド（テーブルのカラムに対応する変数）
	private int id; // 主キー
	private String name; // モンスターの名前
	private int hp; // モンスターの体力
	private Date spawnDate; // モンスターの出現日
	private boolean defeated; // モンスターが倒されたかどうか
	private String imagePath; // モンスター画像のファイルパス
	private Timestamp createdAt; // モンスターの作成日時
	private Timestamp updatedAt; // モンスターの更新日時

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
	 * 名前を取得します。
	 * 
	 * @return モンスター名（文字列）
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前を設定します。
	 * 
	 * @param name モンスター名（文字列）
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 体力（HP）を取得します。
	 * 
	 * @return 体力（整数）
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 体力（HP）を設定します。
	 * 
	 * @param hp 体力（整数）
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * 出現日を取得します。
	 * 
	 * @return 出現日（Date）
	 */
	public Date getSpawnDate() {
		return spawnDate;
	}

	/**
	 * 出現日を設定します。
	 * 
	 * @param spawnDate 出現日（Date）
	 */
	public void setSpawnDate(Date spawnDate) {
		this.spawnDate = spawnDate;
	}

	/**
	 * モンスターが倒されたかを取得します。
	 * 
	 * @return 倒された場合はtrue、それ以外はfalse
	 */
	public boolean isDefeated() {
		return defeated;
	}

	/**
	 * モンスターが倒されたかを設定します。
	 * 
	 * @param defeated 倒された場合はtrue、それ以外はfalse
	 */
	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	/**
	 * 画像ファイルパスを取得します。
	 * 
	 * @return 画像パス（文字列）
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * 画像ファイルパスを設定します。
	 * 
	 * @param imagePath 画像パス（文字列）
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	// オブジェクトの状態を文字列として返すためのメソッド（デバッグやログ出力に便利）
	@Override
	public String toString() {
		return "MonstersDTO [id=" + id + ", name=" + name + ", hp=" + hp + ", spawnDate=" + spawnDate
				+ ", defeated=" + defeated + ", imagePath=" + imagePath + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
}
