<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者ログイン</title>
</head>
<body>
	<h1>管理者ログイン画面</h1>

	<%-- エラーメッセージ表示部分 --%>
	<c:if test="${not empty errorMsg}">
		<ul>
			<c:forEach var="msg" items="${errorMsg}">
				<li>${msg}</li>
			</c:forEach>
		</ul>
	</c:if>

	<%-- 管理者ログインフォーム --%>
	<form action="${pageContext.request.contextPath}/admin/login"
		method="post">
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

	<%-- トップページに戻るリンク --%>
	<a href="${pageContext.request.contextPath}/index.jsp">トップページに戻る</a>
</body>
</html>