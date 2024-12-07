<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/auth.css" rel="stylesheet">
<script src="/RefrainFromDrinkingAlcohol/js/dialog.js"></script>
</head>
<body>
	<div class="auth-container">
		<div class="auth-form">
			<h1>ログイン画面</h1>

			<%-- エラーメッセージ表示 --%>
			<c:if test="${errorMsg != null && not empty errorMsg}">
				<div class="error-message">
					<ul>
						<c:forEach var="msg" items="${errorMsg}">
							<li>${msg}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<form action="/RefrainFromDrinkingAlcohol/login" method="post"
				onsubmit="return confirmLogin();">
				<table>
					<tr>
						<th>ログインID</th>
						<td><input type="text" name="loginId" value="${loginId}"></td>
					</tr>
					<tr>
						<th>名前</th>
						<td><input type="text" name="name" value="${name}"></td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td><input type="password" name="password"></td>
					</tr>
				</table>
				<input type="submit" value="ログイン" class="auth-button">
			</form>

			<a href="/RefrainFromDrinkingAlcohol/register" class="auth-link">
				新規登録はこちら </a>
		</div>
	</div>
</body>
</html>