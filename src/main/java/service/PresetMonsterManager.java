package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.PresetMonsterDTO;

public class PresetMonsterManager {
	private static PresetMonsterManager instance;
	private static final String BASE_PATH = "/preset_monsters/";
	private static final String IMAGES_PATH = BASE_PATH + "images/";
	private static final String CONFIG_PATH = BASE_PATH + "monsters.json";
	private final List<PresetMonsterDTO> presetMonsters;

	private PresetMonsterManager() {
		this.presetMonsters = loadPresetMonsters();
	}

	public static synchronized PresetMonsterManager getInstance() {
		if (instance == null) {
			instance = new PresetMonsterManager();
		}
		return instance;
	}

	public PresetMonsterDTO getRandomMonster() {
		if (presetMonsters.isEmpty()) {
			throw new IllegalStateException("プリセットモンスターが存在しません");
		}
		return presetMonsters.get(new Random().nextInt(presetMonsters.size()));
	}

	public byte[] getMonsterImage(PresetMonsterDTO monster) throws IOException {
		try (InputStream is = getClass().getResourceAsStream(IMAGES_PATH + monster.getImagePath())) {
			if (is == null) {
				throw new FileNotFoundException("モンスター画像が見つかりません: " + monster.getImagePath());
			}
			return IOUtils.toByteArray(is);
		}
	}

	private List<PresetMonsterDTO> loadPresetMonsters() {
		try (InputStream is = getClass().getResourceAsStream(CONFIG_PATH)) {
			if (is == null) {
				throw new FileNotFoundException("モンスター設定ファイルが見つかりません");
			}

			ObjectMapper mapper = new ObjectMapper();
			PresetMonsterConfig config = mapper.readValue(is, PresetMonsterConfig.class);
			validateMonsters(config.getMonsters());
			return config.getMonsters();
		} catch (IOException e) {
			throw new RuntimeException("プリセットモンスターの読み込みに失敗しました", e);
		}
	}

	private void validateMonsters(List<PresetMonsterDTO> monsters) {
		for (PresetMonsterDTO monster : monsters) {
			if (monster.getName() == null || monster.getImagePath() == null ||
					monster.getBaseHp() < 1 || monster.getBaseHp() > 255) {
				throw new IllegalStateException("無効なモンスター設定: " + monster);
			}
			// 画像ファイルの存在確認
			try (InputStream is = getClass().getResourceAsStream(IMAGES_PATH + monster.getImagePath())) {
				if (is == null) {
					throw new FileNotFoundException("画像ファイルが見つかりません: " + monster.getImagePath());
				}
			} catch (IOException e) {
				throw new RuntimeException("画像ファイルの検証に失敗しました: " + monster.getImagePath(), e);
			}
		}
	}

	private static class PresetMonsterConfig {
		private List<PresetMonsterDTO> monsters;

		public List<PresetMonsterDTO> getMonsters() {
			return monsters;
		}

		public void setMonsters(List<PresetMonsterDTO> monsters) {
			this.monsters = monsters;
		}
	}
}