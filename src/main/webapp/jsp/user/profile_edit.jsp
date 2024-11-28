<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>プロフィール編集</title>
    <style>
        /* スタイルの定義 */
        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
        }
        .edit-form {
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="edit-form">
            <h1>プロフィール編集</h1>
            
            <%-- 編集フォーム --%>
            <form action="/RefrainFromDrinkingAlcohol/user/profile/update" method="post">
                <div class="form-group">
                    <label for="name">ユーザー名</label>
                    <input type="text" id="name" name="name" value="${user.name}" required>
                </div>

                <div class="form-group">
                    <label for="password">新しいパスワード（変更する場合のみ）</label>
                    <input type="password" id="password" name="password">
                </div>

                <div class="form-group">
                    <label for="password-confirm">パスワード確認</label>
                    <input type="password" id="password-confirm" name="passwordConfirm">
                </div>

                <button type="submit">更新</button>
                <a href="/RefrainFromDrinkingAlcohol/user/profile">キャンセル</a>
            </form>
        </div>
    </div>

    <%-- エラーメッセージ表示 --%>
    <c:if test="${not empty errorMsg}">
        <div class="error-message">
            <ul>
                <c:forEach var="msg" items="${errorMsg}">
                    <li>${msg}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</body>
</html>