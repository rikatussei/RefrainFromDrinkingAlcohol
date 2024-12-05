<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>戦闘画面 - 飲酒を控え隊</title>
<link href="${pageContext.request.contextPath}/css/battle.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/comments.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<%@ include file="battle/monster_section.jsp"%>
		<%@ include file="battle/attack_form.jsp"%>
		<%@ include file="battle/comments_section.jsp"%>
	</div>
</body>
<div class="container">
	<!-- ヘッダー部分 -->
	<div class="header">
		<h1>戦闘画面</h1>
		<div class="user-info">
			<span>${dto.name}さん</span>
		</div>
	</div>

	<!-- モンスター情報 -->
	<div class="monster-card">
		<h2>本日のモンスター</h2>
		<div class="monster-content">
			<div class="monster-image">
				<img src="data:image/png;base64,${monster.imageBase64}"
					alt="${monster.name}" />
			</div>
			<div class="monster-stats">
				<h3>${monster.name}</h3>
				<div class="hp-bar">
					<div class="hp-value"
						style="width: ${(monster.hp / 255.0) * 100}%;">HP:
						${monster.hp}/255</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 攻撃フォーム -->
	<div class="battle-form">
		<h2>飲酒日記を記入して攻撃</h2>

		<c:if test="${not empty errorMsg}">
			<div class="error-message">
				<ul>
					<c:forEach var="msg" items="${errorMsg}">
						<li>${msg}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form action="/RefrainFromDrinkingAlcohol/battle" method="post">
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

			<div class="button-group">
				<button type="submit" class="attack-button"
					${canAttack ? '' : 'disabled'}>${canAttack ? '攻撃する！' : '本日の攻撃済み'}
				</button>
			</div>
		</form>
	</div>

	<!-- 応援コメント一覧 -->
	<div class="comments-section">
		<h2>応援コメント</h2>
		<div class="comments-container">
			<c:forEach items="${comments}" var="comment">
				<div
					class="comment-card ${comment.commentType == 'AI' ? 'ai-comment' : 'user-comment'}">
					<div class="comment-header">
						<span class="commenter-name"> ${comment.commentType == 'AI' ? 'AI' : comment.userName}
						</span> <span class="comment-time"> <fmt:formatDate
								value="${comment.createdAt}" pattern="yyyy/MM/dd HH:mm" />
						</span>
					</div>
					<div class="comment-text">${comment.text}</div>
				</div>
			</c:forEach>
		</div>

		<!-- コメント投稿フォーム -->
		<form action="/RefrainFromDrinkingAlcohol/battle/comment"
			method="post" class="comment-form">
			<div class="form-group">
				<textarea name="commentText" required placeholder="応援コメントを書く"></textarea>
			</div>
			<button type="submit" class="comment-button">コメントする</button>
		</form>
	</div>

	<!-- ナビゲーションリンク -->
	<div class="nav-links">
		<a href="/RefrainFromDrinkingAlcohol/home" class="btn-secondary">ホームに戻る</a>
		<a href="/RefrainFromDrinkingAlcohol/user/profile"
			class="btn-secondary">プロフィール</a>
	</div>
</div>
</body>
</html>