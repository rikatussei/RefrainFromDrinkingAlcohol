// src/test/java/service/BattleServiceTest.java
package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dto.BattleResultDTO;

class BattleServiceTest {
	@Mock
	private Connection mockConnection;

	@Mock
	private PreparedStatement mockPreparedStatement;

	@Mock
	private ResultSet mockResultSet;

	private BattleService battleService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		battleService = new BattleService();

		// モックの設定
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
	}

	@Test
	void testProcessAttack() throws Exception {
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(0); // 攻撃可能

		BattleResultDTO result = battleService.processAttack(1, false, "テストコメント");

		assertNotNull(result);
		assertTrue(result.getDamage() > 0);
		verify(mockPreparedStatement, times(1)).executeUpdate();
	}

	@Test
	void testCanUserAttack() throws Exception {
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getInt(1)).thenReturn(0);

		assertTrue(battleService.canUserAttack(1));

		when(mockResultSet.getInt(1)).thenReturn(1);
		assertFalse(battleService.canUserAttack(1));
	}
}