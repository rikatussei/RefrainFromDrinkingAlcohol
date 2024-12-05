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

	// Getters and setters
}