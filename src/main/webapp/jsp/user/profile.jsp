<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール - 飲酒を控え隊</title>
<!-- FullCalendar -->
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
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
		<!-- ヘッダー部分 -->
		<div class="header">
			<h1>プロフィール画面</h1>
			<div class="user-info">
				<h2>
					<span>${user.name}さんのプロフィール</span>
				</h2>
			</div>
		</div>

		<!-- プロフィール情報 -->
		<div class="profile-info card">
			<p>ログインID: ${user.loginId}</p>
			<p>登録日: ${user.createdAt}</p>
			<p>総獲得トークン数: ${totalTokens}</p>

			<div class="nav-links">
				<a href="/RefrainFromDrinkingAlcohol/jsp/user/profile_edit.jsp"
					class="btn-primary">プロフィール編集</a> <a
					href="/RefrainFromDrinkingAlcohol/jsp/home.jsp"
					class="btn-secondary">ホームに戻る</a>
			</div>
		</div>

		<!-- カレンダー表示エリア -->
		<div class="calendar-container card">
			<h2>戦闘履歴</h2>
			<div id="calendar"></div>
		</div>
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
                
                // イベント表示のカスタマイズ
                events: function(info, successCallback, failureCallback) {
                    // 戦闘記録データを取得
                    fetch('${pageContext.request.contextPath}/api/battle-history')
                        .then(response => response.json())
                        .then(data => {
                            successCallback(data.map(battle => ({
                                title: battle.hasToken ? '🏆' : '⚔️',
                                start: battle.date,
                                allDay: true,
                                className: battle.hasToken ? 'victory-event' : 'battle-event'
                            })));
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            failureCallback(error);
                        });
                }
            });
            calendar.render();
        });
    </script>
</body>
</html>