package validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Validation {
	private List<String> errorMsg;

	public Validation() {
		List<String> errorMsg = new ArrayList<>();
		this.errorMsg = errorMsg;
	}

	public boolean hasErrorMsg() {
		if (errorMsg.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void isNull(String textName, String text) {
		if (text == null || text.isEmpty()) {
			this.errorMsg.add(textName + "が入力されていません");
		}
	}

	public void length(String textName, String text, int min, int max) {
		if (text == null || text.length() < min || text.length() > max) {
			this.errorMsg.add(textName + "は、" + min + "文字以上、" + max + "文字以内で入力してください");
		}
	}

	public void length(String textName, String text, int max) {
		if (text == null || text.length() > max) {
			this.errorMsg.add(textName + "は、" + max + "文字以内で 入力してください");
		}
	}

	public boolean isNumber(String textName, String text) {
		boolean flg = false;
		try {
			Long.parseLong(text);
			flg = true;
		} catch (NumberFormatException e) {
			this.errorMsg.add(textName + "は数値を入力してください");
		}
		return flg;
	}

	public boolean isInteger(String textName, String text) {
		boolean flg = false;
		try {
			Integer.parseInt(text);
			flg = true;
		} catch (NumberFormatException e) {
			this.errorMsg.add(textName + "は数値を入力してください");
		}
		return flg;
	}

	public Date isDate(String textName, String text) {
		Date date = null;
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = sdFormat.parse(text);
		} catch (ParseException e) {
			this.errorMsg.add(textName + "は日付を(yyyy-MM-dd)の形式 で入力してください");
		}
		return date;
	}

	public void addErrorMsg(String msg) {
		errorMsg.add(msg);
	}

	public List<String> getErrorMsg() {
		return errorMsg;
	}
}
