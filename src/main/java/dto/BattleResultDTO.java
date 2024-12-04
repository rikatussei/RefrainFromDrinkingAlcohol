package dto;

import java.io.Serializable;
import java.sql.Date;

public class BattleResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int damage;
	private boolean defeated;
	private double sentimentScore;
	private boolean drinking;
	private String comment;
	private Date attackDate;
	private String monsterName;

	// コンストラクタ
	public BattleResultDTO() {
	}

	public BattleResultDTO(int damage, boolean defeated, double sentimentScore) {
		this.damage = damage;
		this.defeated = defeated;
		this.sentimentScore = sentimentScore;
	}

	// ゲッター・セッター
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isDefeated() {
		return defeated;
	}

	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}

	public double getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	public boolean isDrinking() {
		return drinking;
	}

	public void setDrinking(boolean drinking) {
		this.drinking = drinking;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getAttackDate() {
		return attackDate;
	}

	public void setAttackDate(Date attackDate) {
		this.attackDate = attackDate;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}
}