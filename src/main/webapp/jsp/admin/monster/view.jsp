<%-- src/main/webapp/jsp/admin/monster/view.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${monster.name}- モンスター詳細</title>
<link href="${pageContext.request.contextPath}/css/admin.css"
	rel="stylesheet">
</head>
<body>
	<div class="admin-container">
		<h1>モンスター詳細</h1>

		<div class="monster-detail card">
			<div class="monster-image">
				<img src="data:image/png;base64,${monster.imageBase64}"
					alt="${monster.name}">
			</div>
			<div class="monster-info">
				<h2>${monster.name}</h2>
				<p>HP: ${monster.hp}</p>
				<p>生成日: ${monster.spawnDate}</p>
				<p>状態: ${monster.defeated ? '撃破済' : '生存'}</p>
			</div>
		</div>

		<div class="comments-section">
			<h2>コメント一覧</h2>
			<c:forEach items="${comments}" var="comment">
				<div class="comment-card">
					<p class="comment-text">${comment.text}</p>
					<p class="comment-meta">投稿者: ${comment.userName} | 投稿日時:
						${comment.createdAt}</p>
					<form
						action="${pageContext.request.contextPath}/admin/monster/deleteComment"
						method="post" style="display: inline;">
						<input type="hidden" name="commentId" value="${comment.id}">
						<input type="hidden" name="monsterId" value="${monster.id}">
						<button type="submit" class="btn-delete-comment"
							onclick="return confirm('このコメントを削除しますか？')">削除</button>
					</form>
				</div>
			</c:forEach>
		</div>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath}/admin/monster/list">一覧に戻る</a>
		</div>
	</div>
</body>
</html>