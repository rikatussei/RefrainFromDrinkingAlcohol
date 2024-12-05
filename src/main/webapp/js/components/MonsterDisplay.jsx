// モンスター表示用Reactコンポーネント
const MonsterDisplay = () => {
	const monsters = [
		{
			id: 1,
			name: "おさけドラゴン",
			color: "text-red-500",
			baseColor: "#ff6b6b",
			description: "飲酒の誘惑を操るドラゴン"
		},
		{
			id: 2,
			name: "ビアゴースト",
			color: "text-yellow-500",
			baseColor: "#ffd93d",
			description: "ビールの泡から生まれた幽霊"
		},
		{
			id: 3,
			name: "サケマンダー",
			color: "text-blue-500",
			baseColor: "#4dabf7",
			description: "日本酒の化身"
		},
		{
			id: 4,
			name: "ワインプス",
			color: "text-purple-500",
			baseColor: "#da77f2",
			description: "ワインの精"
		},
		{
			id: 5,
			name: "ウィスキーウルフ",
			color: "text-amber-700",
			baseColor: "#b45309",
			description: "ウィスキーの守護者"
		}
	];

	return (
		<div className="w-full max-w-6xl mx-auto p-4">
			<h2 className="text-2xl font-bold text-center mb-8">今日の飲酒モンスター</h2>
			<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
				{monsters.map((monster) => (
					<div key={monster.id}
						className="bg-white rounded-lg shadow-lg p-6 transform transition duration-300 hover:scale-105">
						<div className="monster-animation"
							style={{
								'--monster-color': monster.baseColor
							}}>
							<div className="monster-body"></div>
							<div className="monster-eyes"></div>
						</div>
						<h3 className={`text-xl font-bold ${monster.color} text-center mt-4`}>
							{monster.name}
						</h3>
						<p className="text-gray-600 text-center mt-2">
							{monster.description}
						</p>
					</div>
				))}
			</div>
		</div>
	);
};

export default MonsterDisplay;