// src/test/java/service/MonsterGenerationServiceTest.java
package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dto.MonstersDTO;
import util.OpenAIClient;

class MonsterGenerationServiceTest {
	@Mock
	private OpenAIClient openAIClient;

	private MonsterGenerationService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new MonsterGenerationService();
	}

	@Test
	void testGenerateDailyMonster() throws Exception {
		// モックの設定
		when(openAIClient.generateMonsterName()).thenReturn("テストモンスター");
		when(openAIClient.generateMonsterImage(anyString())).thenReturn(new byte[100]);

		// テスト実行
		MonstersDTO monster = service.generateDailyMonster();

		// 検証
		assertNotNull(monster);
		assertNotNull(monster.getName());
		assertTrue(monster.getHp() >= 100 && monster.getHp() <= 255);
		assertNotNull(monster.getImageData());
	}
}