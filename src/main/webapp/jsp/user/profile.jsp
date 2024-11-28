<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>プロフィール - マイページ</title>
    <style>
        /* スタイルの定義 */
        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
        }
        .profile-section {
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .nav-links {
            margin-top: 20px;
        }
        .nav-links a {
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <%-- プロフィール情報表示セクション --%>
        <div class="profile-section">
            <h1>プロフィール</h1>
            
            <%-- ユーザー情報の表示 --%>
            <div class="user-info">
                <p>ログインID: ${user.loginId}</p>
                <p>ユーザー名: ${user.name}</p>
            </div>

            <%-- 編集ボタン --%>
            <div class="edit-buttons">
                <a href="/RefrainFromDrinkingAlcohol/user/profile/edit">
                    プロフィール編集
                </a>
            </div>
        </div>

        <%-- ナビゲーションリンク --%>
        <div class="nav-links">
            <%-- 直接パスを指定したリンク --%>
            <a href="/RefrainFromDrinkingAlcohol/user/home">ホームに戻る</a>
            <a href="/RefrainFromDrinkingAlcohol/logout">ログアウト</a>
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
    </div>
</body>
</html>