<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録画面</title>
<script src="/RefrainFromDrinkingAlcohol/js/dialog.js"></script>
</head>
<body>
	<h1>会員登録画面</h1>
	<form action="/RefrainFromDrinkingAlcohol/register" method="post"
		onsubmit="return confirmRegistration();">
		<table>
			<tr>
				<th>ログインID</th>
				<td><input type="text" name="loginId"></td>
			</tr>
			<tr>
				<th>名前</th>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="登録">
	</form>
</body>
</html>
