<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<style>
.error-message {
	color: red;
	margin: 10px 0;
}
</style>
</head>
<body>
	<h1>ログイン画面</h1>

	<%-- エラーメッセージの表示（簡略化） --%>
	<c:if test="${not empty errorMsg}">
		<div class="error-message">
			<ul>
				<c:forEach var="msg" items="${errorMsg}">
					<li>${msg}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<!-- ログインフォーム -->
	<form action="${pageContext.request.contextPath}/login" method="post">
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
		<input type="submit" value="ログイン">
	</form>

	<a href="${pageContext.request.contextPath}/register">新規登録はこちら</a>
</body>
</html>