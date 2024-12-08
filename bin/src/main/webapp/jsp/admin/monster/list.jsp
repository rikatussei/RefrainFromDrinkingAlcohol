<%-- src/main/webapp/jsp/admin/monster/list.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>モンスター一覧 - 管理画面</title>
<link href="${pageContext.request.contextPath}/css/admin.css"
	rel="stylesheet">
</head>
<body>
	<div class="admin-container">
		<h1>モンスター一覧</h1>

		<div class="action-buttons">
			<form
				action="${pageContext.request.contextPath}/admin/monster/generate"
				method="post">
				<button type="submit" class="btn-primary">新規モンスター生成</button>
			</form>
		</div>

		<div class="monster-list">
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>名前</th>
						<th>HP</th>
						<th>生成日</th>
						<th>状態</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${monsters}" var="monster">
						<tr>
							<td>${monster.id}</td>
							<td>${monster.name}</td>
							<td>${monster.hp}</td>
							<td>${monster.spawnDate}</td>
							<td>${monster.defeated ? '撃破済' : '生存'}</td>
							<td><a
								href="${pageContext.request.contextPath}/admin/monster/view?id=${monster.id}"
								class="btn-view">詳細</a>
								<form
									action="${pageContext.request.contextPath}/admin/monster/delete"
									method="post" style="display: inline;">
									<input type="hidden" name="id" value="${monster.id}">
									<button type="submit" class="btn-delete"
										onclick="return confirm('このモンスターを削除しますか？')">削除</button>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath}/admin/dashboard">ダッシュボードに戻る</a>
		</div>
	</div>
</body>
</html>