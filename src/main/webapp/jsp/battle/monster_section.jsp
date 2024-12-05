// battle/monster_section.jsp - モンスター表示部分
<div class="monster-info">
	<h2>${monster.name}</h2>
	<img src="data:image/png;base64,${monster.imageBase64}"
		alt="${monster.name}">
	<div class="hp-bar">
		<div class="hp-value" style="width: ${(monster.hp / 255.0) * 100}%">
			HP: ${monster.hp}/255</div>
	</div>
</div>