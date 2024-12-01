<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録完了画面</title>
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/auth.css" rel="stylesheet">
</head>
<body>
	<div class="auth-container">
		<div class="completion-message">
			<h1>登録完了画面</h1>
			<p>ユーザー名: ${dto.name} で登録しました</p>
			<a href="/RefrainFromDrinkingAlcohol/login" class="auth-link">
				ログイン画面へ </a>
		</div>
	</div>
</body>
</html>
