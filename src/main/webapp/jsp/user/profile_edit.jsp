<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール編集 - 飲酒を控え隊</title>
<!-- カスタムCSS -->
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/profile.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- ヘッダー部分 -->
		<div class="header">
			<h1>プロフィール編集</h1>
		</div>

		<!-- 編集フォーム -->
		<div class="card">
			<form action="/RefrainFromDrinkingAlcohol/user/profile/update"
				method="post">
				<div class="form-group">
					<label for="name">ユーザー名</label> <input type="text" id="name"
						name="name" value="${user.name}" required>
				</div>

				<div class="form-group">
					<label for="password">新しいパスワード（変更する場合のみ）</label> <input
						type="password" id="password" name="password">
				</div>

				<div class="form-group">
					<label for="password-confirm">パスワード確認</label> <input
						type="password" id="password-confirm" name="passwordConfirm">
				</div>

				<div class="button-group">
					<button type="submit" class="btn-primary">更新</button>
					<a href="/RefrainFromDrinkingAlcohol/user/profile"
						class="btn-secondary">キャンセル</a>
				</div>
			</form>

			<!-- エラーメッセージ表示 -->
			<c:if test="${not empty errorMsg}">
				<div class="error-message">
					<ul>
						<c:forEach var="msg" items="${errorMsg}">
							<li>${msg}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>