// モンスター表示用Reactコンポーネント
class MonsterDisplay extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			hoveredMonsterId: null
		};
	}

	renderMonster(monster) {
		const isHovered = this.state.hoveredMonsterId === monster.id;
		const style = {
			backgroundColor: monster.color,
			transform: isHovered ? 'scale(1.1)' : 'scale(1)',
			transition: 'all 0.3s ease'
		};

		return (
			<div
				key={monster.id}
				className="monster-card"
				onMouseEnter={() => this.setState({ hoveredMonsterId: monster.id })}
				onMouseLeave={() => this.setState({ hoveredMonsterId: null })}
				style={style}
			>
				<div className="monster-face">
					<div className="monster-eyes">
						<div className="eye"></div>
						<div className="eye"></div>
					</div>
					<div className="monster-mouth"></div>
				</div>
				<div className="monster-info">
					<h3>{monster.name}</h3>
					<p>{monster.description}</p>
					<div className="monster-stats">
						<span>HP: {monster.baseStats.hp}</span>
						<span>{monster.baseStats.type}</span>
					</div>
				</div>
			</div>
		);
	}

	render() {
		return (
			<div className="monsters-container">
				{PREDEFINED_MONSTERS.map(monster => this.renderMonster(monster))}
			</div>
		);
	}
}