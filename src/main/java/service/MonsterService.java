package service;

import dto.MonstersDTO;
import util.OpenAIClient;

public class MonsterService {
    private OpenAIClient openAIClient = new OpenAIClient();

    public MonstersDTO generateMonster() throws Exception {
        MonstersDTO monster = new MonstersDTO();
        String name = openAIClient.generateMonsterName(); // モンスター名生成
        byte[] image = openAIClient.generateMonsterImage(name); // モンスター画像生成

        // モンスター情報をセット
		monster.setName(name);
		monster.setHp(100); // 仮のHP
        monster.setImageData(image);

        return monster;
    }
}
