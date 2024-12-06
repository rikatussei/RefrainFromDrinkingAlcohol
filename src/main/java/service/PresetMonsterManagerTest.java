// src/test/java/service/PresetMonsterManagerTest.java
package service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dto.PresetMonsterDTO;

class PresetMonsterManagerTest {
	private final PresetMonsterManager manager = PresetMonsterManager.getInstance();

	@Test
	void testGetRandomMonster() {
		PresetMonsterDTO monster = manager.getRandomMonster();
		assertNotNull(monster);
		assertNotNull(monster.getName());
		assertTrue(monster.getBaseHp() >= 100 && monster.getBaseHp() <= 255);
	}

	@Test
	void testGetMonsterImage() {
		PresetMonsterDTO monster = manager.getRandomMonster();
		assertDoesNotThrow(() -> {
			byte[] imageData = manager.getMonsterImage(monster);
			assertNotNull(imageData);
			assertTrue(imageData.length > 0);
		});
	}
}