// src/main/java/dto/CommentsDTO.java
package dto;

import java.sql.Timestamp;

public class CommentsDTO {
	private int id;
	private String text;
	private Integer userId;
	private String userName;
	private int monsterId;
	private Timestamp createdAt;
	private Timestamp deletedAt;

	public CommentsDTO(int id, String text, Integer userId, String userName, int monsterId, Timestamp createdAt,
			Timestamp deletedAt) {
		super();
		this.id = id;
		this.text = text;
		this.userId = userId;
		this.userName = userName;
		this.monsterId = monsterId;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}

	// Getters and setters
}