<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>戦闘画面 - 飲酒を控え隊</title>
<!-- CSRFトークン設定 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- CSS読み込み -->
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/battle.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/comments.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- ヘッダー部分 -->
		<div class="header">
			<h1>戦闘画面</h1>
			<div class="user-info">
				<span>${dto.name}さん</span>
			</div>
		</div>

		<!-- モンスター情報表示部分 -->
		<div class="monster-card">
			<h2>${monster.name}との戦い</h2>
			<div class="monster-display">
				<!-- モンスター画像 -->
				<div class="monster-image">
					<img src="data:image/png;base64,${monster.imageBase64}"
						alt="${monster.name}"
						onerror="this.src='/RefrainFromDrinkingAlcohol/images/default-monster.png'">
				</div>
				<!-- HP表示バー -->
				<div class="hp-bar">
					<div class="hp-value" style="width: ${(monster.hp / 255.0) * 100}%">
						HP: ${monster.hp}/255</div>
				</div>
			</div>
		</div>

		<!-- 戦闘フォーム -->
		<div class="battle-form">
			<h3>飲酒日記を記入して攻撃</h3>
			<form action="/RefrainFromDrinkingAlcohol/battle" method="post"
				id="attackForm">
				<!-- 飲酒状況入力 -->
				<div class="form-group">
					<label for="drinking">今日の飲酒状況：</label> <select id="drinking"
						name="drinking" required>
						<option value="false">飲酒していない</option>
						<option value="true">飲酒した</option>
					</select>
				</div>

				<!-- 感想・決意入力 -->
				<div class="form-group">
					<label for="comment">感想や決意：</label>
					<textarea id="comment" name="comment" required
						placeholder="今日の状況や感想、明日への決意などを書いてください"></textarea>
				</div>

				<!-- 攻撃ボタン -->
				<button type="submit" class="attack-button"
					${canAttack ? '' : 'disabled'}>${canAttack ? '攻撃する！' : '本日の攻撃済み'}
				</button>
			</form>
		</div>

		<!-- 応援メッセージエリア -->
		<div class="support-message-area">
			<h3>今日の応援メッセージ</h3>
			<div id="aiMessage" class="ai-message">${supportMessage}</div>
		</div>

		<!-- コメントセクション -->
		<div class="comments-section">
			<h3>みんなの応援コメント</h3>

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
						class="comment-card ${comment.userId eq sessionScope.dto.id ? 'own-comment' : ''}"
						data-comment-id="${comment.id}">
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

		<!-- ナビゲーションリンク -->
		<div class="nav-links">
			<a href="/RefrainFromDrinkingAlcohol/home" class="btn-secondary">ホームに戻る</a>
			<a href="/RefrainFromDrinkingAlcohol/user/profile"
				class="btn-secondary">プロフィール</a>
		</div>
	</div>

	<!-- JavaScript -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/RefrainFromDrinkingAlcohol/js/comments.js"></script>
	<script>
        // 戦闘フォーム送信前の確認
        document.getElementById('attackForm').addEventListener('submit', function(e) {
            if (!confirm('この内容で攻撃しますか？')) {
                e.preventDefault();
            }
        });

        // エラーメッセージ表示
        <c:if test="${not empty errorMsg}">
            alert('${errorMsg}');
        </c:if>
    </script>
</body>
</html>