<%-- src/main/webapp/jsp/admin/dashboard.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者ダッシュボード</title>
<link href="${pageContext.request.contextPath}/css/admin.css"
	rel="stylesheet">
</head>
<body>
	<div class="admin-container">
		<h1>管理者ダッシュボード</h1>

		<!-- モンスター管理セクション -->
		<section class="monster-management">
			<h2>モンスター管理</h2>
			<form
				action="${pageContext.request.contextPath}/admin/monster/generate"
				method="post">
				<button type="submit" class="btn-primary">新規モンスター生成</button>
			</form>

			<table class="monster-list">
				<thead>
					<tr>
						<th>ID</th>
						<th>名前</th>
						<th>HP</th>
						<th>生成日</th>
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
							<td>
								<form
									action="${pageContext.request.contextPath}/admin/monster/delete"
									method="post" style="display: inline;">
									<input type="hidden" name="id" value="${monster.id}">
									<button type="submit" class="btn-delete"
										onclick="return confirm('このモンスターを削除しますか？')">削除</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
	</div>
</body>
</html>