package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * カレンダーに関する機能を提供するユーティリティクラス
 */
public class CalendarUtil {

	/**
	 * カレンダーデータを生成するメソッド
	 * @param year 表示する年
	 * @param month 表示する月（1-12）
	 * @return カレンダーデータ（週ごとの日付情報）
	 */
	public static List<List<CalendarDay>> generateCalendar(int year, int month) {
		// カレンダーオブジェクトを取得
		Calendar calendar = Calendar.getInstance();
		// 指定された年月の1日にセット
		calendar.set(year, month - 1, 1); // 月は0-11で扱うため-1する

		// 今日の日付を取得（今日かどうかの判定用）
		Calendar today = Calendar.getInstance();

		// カレンダーデータを格納するリスト
		List<List<CalendarDay>> calendarData = new ArrayList<>();
		// 1週間分のデータを格納するリスト
		List<CalendarDay> weekData = new ArrayList<>();

		// 月の最初の日の曜日を取得（1:日曜日 ～ 7:土曜日）
		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		// 月の最終日を取得
		int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// 月の最初の週の空白を追加
		for (int i = 1; i < firstDayOfWeek; i++) {
			weekData.add(new CalendarDay(0, false, false));
		}

		// 日付を追加
		for (int day = 1; day <= lastDayOfMonth; day++) {
			calendar.set(Calendar.DAY_OF_MONTH, day);

			// 今日の日付かどうかをチェック
			boolean isToday = (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
					calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
					calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH));

			// 日付情報を追加
			weekData.add(new CalendarDay(day, isToday, false));

			// 週末または月末の場合、週のデータを確定
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					day == lastDayOfMonth) {
				calendarData.add(new ArrayList<>(weekData));
				weekData.clear();
			}
		}

		return calendarData;
	}

	/**
	 * カレンダーの日付情報を保持するための内部クラス
	 */
	public static class CalendarDay {
		private int dayOfMonth; // 日付
		private boolean isToday; // 今日かどうか
		private boolean hasMonster; // モンスターが存在するかどうか

		/**
		 * コンストラクタ
		 * @param dayOfMonth 日付（1-31）
		 * @param isToday 今日かどうか
		 * @param hasMonster モンスターが存在するかどうか
		 */
		public CalendarDay(int dayOfMonth, boolean isToday, boolean hasMonster) {
			this.dayOfMonth = dayOfMonth;
			this.isToday = isToday;
			this.hasMonster = hasMonster;
		}

		// getter メソッド
		public int getDayOfMonth() {
			return dayOfMonth;
		}

		public boolean isToday() {
			return isToday;
		}

		public boolean hasMonster() {
			return hasMonster;
		}
	}
}