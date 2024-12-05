<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>戦闘画面 - 飲酒を控え隊</title>
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/battle.css" rel="stylesheet">
<!-- コメント機能用のCSS -->
<link href="/RefrainFromDrinkingAlcohol/css/comments.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- モンスター情報表示部分 -->
		<div class="monster-display">
			<h2>${monster.name}</h2>
			<div class="monster-image">
				<img src="data:image/png;base64,${monster.imageBase64}"
					alt="${monster.name}">
			</div>
			<div class="hp-bar">
				<div class="hp-value" style="width: ${(monster.hp / 255.0) * 100}%">
					HP: ${monster.hp}/255</div>
			</div>
		</div>

		<!-- 攻撃フォーム -->
		<div class="battle-form">
			<form id="attackForm" action="/RefrainFromDrinkingAlcohol/battle"
				method="post">
				<div class="form-group">
					<label for="drinking">本日の飲酒状況：</label> <select id="drinking"
						name="drinking" required>
						<option value="false">飲酒していない</option>
						<option value="true">飲酒した</option>
					</select>
				</div>

				<div class="form-group">
					<label for="comment">感想や決意：</label>
					<textarea id="comment" name="comment" required
						placeholder="今日の状況や感想、明日への決意などを書いてください"></textarea>
				</div>

				<button type="submit" class="attack-button">攻撃する！</button>
			</form>
		</div>

		<!-- コメント表示・投稿エリア -->
		<div class="comments-section">
			<h3>応援コメント</h3>

			<!-- コメント投稿フォーム -->
			<form id="commentForm" class="comment-form">
				<input type="hidden" id="monsterId" value="${monster.id}">
				<textarea id="commentText" placeholder="応援メッセージを書く" required></textarea>
				<button type="submit">コメントする</button>
			</form>

			<!-- コメント一覧 -->
			<div id="commentsList" class="comments-list">
				<c:forEach items="${comments}" var="comment">
					<div
						class="comment-card ${comment.userId eq sessionScope.dto.id ? 'own-comment' : ''}">
						<div class="comment-header">
							<span class="commenter-name">${comment.userName}</span> <span
								class="comment-time"> <fmt:formatDate
									value="${comment.createdAt}" pattern="yyyy/MM/dd HH:mm" />
							</span>
						</div>
						<div class="comment-content">${comment.text}</div>
						<c:if test="${comment.userId eq sessionScope.dto.id}">
							<button onclick="deleteComment(${comment.id})"
								class="delete-comment">削除</button>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- コメント機能用JavaScript -->
	<script src="/RefrainFromDrinkingAlcohol/js/comments.js"></script>
</body>
</html>
