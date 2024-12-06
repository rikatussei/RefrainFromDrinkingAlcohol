// src/test/java/util/DBUtilTest.java
package util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import util.DBUtil;

class DBUtilTest {

	@Test
	void testGetConnection() {
		assertDoesNotThrow(() -> {
			try (Connection conn = DBUtil.getConnection()) {
				assertNotNull(conn);
				assertFalse(conn.isClosed());
			}
		});
	}

	@Test
	void testConnectionProperties() throws SQLException {
		try (Connection conn = DBUtil.getConnection()) {
			// auto-commitがデフォルトでtrueか確認
			assertTrue(conn.getAutoCommit());

			// トランザクション分離レベルの確認
			int isolationLevel = conn.getTransactionIsolation();
			assertEquals(Connection.TRANSACTION_READ_COMMITTED, isolationLevel);
		}
	}

	@Test
	void testMultipleConnections() {
		assertDoesNotThrow(() -> {
			Connection conn1 = DBUtil.getConnection();
			Connection conn2 = DBUtil.getConnection();

			assertNotNull(conn1);
			assertNotNull(conn2);
			assertNotSame(conn1, conn2);

			conn1.close();
			conn2.close();
		});
	}
}