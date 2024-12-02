<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール - 飲酒を控え隊</title>
<!-- FullCalendar CSS -->
<link
	href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/main.min.css'
	rel='stylesheet' />
<!-- カスタムCSS -->
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/profile.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- プロフィール情報 -->
		<div class="profile-info">
			<h1>${user.name}さんのプロフィール</h1>
			<p>ログインID: ${user.loginId}</p>
			<p>登録日: ${user.createdAt}</p>
			<p>総獲得トークン数: ${totalTokens}</p>

			<div class="nav-links">
				<a href="/RefrainFromDrinkingAlcohol/jsp/user/profile_edit.jsp">プロフィール編集</a>
				<a href="/RefrainFromDrinkingAlcohol/jsp/home.jsp">ホームに戻る</a>
			</div>
		</div>

		<!-- カレンダー表示エリア -->
		<div id="calendar"></div>
	</div>

	<script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ja',
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: ''
                },
                
                // イベントデータの設定
                events: ${battleResults}, // サーバーから取得した戦闘結果データ
                
                // イベント表示のカスタマイズ
                eventContent: function(arg) {
                    // 勝利した場合はトークンアイコンを表示
                    if (arg.event.extendedProps.hasToken) {
                        return {
                            html: '<div class="battle-token" title="勝利トークン"></div>'
                        };
                    }
                }
            });
            
            calendar.render();
        });
    </script>
</body>
</html>