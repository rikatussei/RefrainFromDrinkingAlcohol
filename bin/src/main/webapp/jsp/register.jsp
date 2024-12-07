<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録画面</title>
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/auth.css" rel="stylesheet">
<script src="/RefrainFromDrinkingAlcohol/js/dialog.js"></script>
</head>
<body>
	<div class="auth-container">
		<div class="auth-form">
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
				<input type="submit" value="登録" class="auth-button">
			</form>

			<a href="/RefrainFromDrinkingAlcohol/login" class="auth-link">
				ログイン画面に戻る </a>
		</div>
	</div>
</body>
</html>
