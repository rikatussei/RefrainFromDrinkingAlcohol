// 事前定義されたモンスターデータ
const PREDEFINED_MONSTERS = [
	{
		id: 1,
		name: "おさけドラゴン",
		description: "飲酒の誘惑を操るかわいいドラゴン",
		color: "#FF9999",
		baseStats: {
			hp: 150,
			type: "火属性"
		}
	},
	{
		id: 2,
		name: "ビアゴースト",
		description: "ビールの泡から生まれたゆるふわ幽霊",
		color: "#FFDB58",
		baseStats: {
			hp: 120,
			type: "風属性"
		}
	},
	{
		id: 3,
		name: "サケマンダー",
		description: "日本酒が大好きなもふもふサラマンダー",
		color: "#87CEEB",
		baseStats: {
			hp: 180,
			type: "水属性"
		}
	},
	{
		id: 4,
		name: "ワインプス",
		description: "ワインの精から生まれた優雅な妖精",
		color: "#DDA0DD",
		baseStats: {
			hp: 100,
			type: "光属性"
		}
	},
	{
		id: 5,
		name: "ウィスキーウルフ",
		description: "ウィスキーの樽で眠る子狼",
		color: "#DEB887",
		baseStats: {
			hp: 200,
			type: "地属性"
		}
	}
];

export { PREDEFINED_MONSTERS };