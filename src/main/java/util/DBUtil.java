// src/main/java/util/DBUtil.java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
	private static final ResourceBundle rb = ResourceBundle.getBundle("database");
	private static final String URL = rb.getString("db.url");
	private static final String USER = rb.getString("db.user");
	private static final String PASSWORD = rb.getString("db.password");

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("PostgreSQLドライバの読み込みに失敗しました", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}