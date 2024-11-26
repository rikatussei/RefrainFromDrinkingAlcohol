package dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * DTO（Data Transfer Object）クラス
 * このクラスはカレンダー情報（calendarsテーブルのデータ）を表現します。
 * DAO（Data Access Object）でデータベースとのやり取りを行う際に使用されます。
 */
public class CalendarsDTO implements Serializable {
	// シリアライズID。DTOクラスをSerializableにすることでデータの直列化が可能になります。
	private static final long serialVersionUID = 1L;

	// フィールド（SQLテーブルのカラムに対応する変数）
	private Date calendarDate; // カレンダーの日付（主キー）
	private int monsterId; // 出現するモンスターのID（外部キー）
	private boolean defeated; // モンスターが倒されたかどうかの状態（true: 倒された, false: 未討伐）

	// ゲッターとセッター（フィールドの値を取得・設定するメソッド）

	/**
	 * カレンダーの日付を取得します。
	 * 
	 * @return カレンダーの日付（java.sql.Date）
	 */
	public Date getCalendarDate() {
		return calendarDate;
	}

	/**
	 * カレンダーの日付を設定します。
	 * 
	 * @param calendarDate カレンダーの日付（java.sql.Date）
	 */
	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
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
	 * モンスターが倒されたかどうかを取得します。
	 * 
	 * @return 倒された場合はtrue、それ以外はfalse
	 */
	public boolean isDefeated() {
		return defeated;
	}

	/**
	 * モンスターが倒されたかどうかを設定します。
	 * 
	 * @param defeated 倒された場合はtrue、それ以外はfalse
	 */
	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	// オブジェクトの状態を文字列として返すメソッド（デバッグやログ出力に便利）

	/**
	 * オブジェクトの状態を文字列形式で返します。
	 * 
	 * @return カレンダーDTOの内容を表す文字列
	 */
	@Override
	public String toString() {
		return "CalendarsDTO [calendarDate=" + calendarDate + ", monsterId=" + monsterId
				+ ", defeated=" + defeated + "]";
	}
}
