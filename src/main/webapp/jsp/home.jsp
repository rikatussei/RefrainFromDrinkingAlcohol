<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<!-- FullCalendar -->
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
<link
	href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/main.min.css'
	rel='stylesheet' />
<!-- カスタムCSS -->
<link href="/RefrainFromDrinkingAlcohol/css/common.css" rel="stylesheet">
<link href="/RefrainFromDrinkingAlcohol/css/home.css" rel="stylesheet">
<script src="/RefrainFromDrinkingAlcohol/js/dialog.js"></script>
</head>
<body>
	<div class="container">
		<!-- ヘッダー部分 -->
		<div class="header">
			<h1>ホーム画面</h1>
			<div class="user-info">
				<span>${dto.name}さん</span>
			</div>
		</div>

		<!-- カレンダー本体 -->
		<div id="calendar"></div>

		<!-- ナビゲーションリンク -->
		<div class="nav-links">
			<a href="/RefrainFromDrinkingAlcohol/user/profile">プロフィール</a> <a
				href="${pageContext.request.contextPath}/logout">ログアウト</a>
		</div>
	</div>

	<!-- カレンダーの初期化スクリプト -->
	<script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',  // 月表示
                locale: 'ja',                 // 日本語化
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: ''
                },
                
                // 日付クリックイベント
                dateClick: function(info) {
                    // 今日の日付を取得
                    var today = new Date();
                    today.setHours(0, 0, 0, 0);
                    
                    // クリックされた日付
                    var clickedDate = info.date;
                    clickedDate.setHours(0, 0, 0, 0);
                    
                    // 今日の日付のみクリック可能
                    if (clickedDate.getTime() === today.getTime()) {
                        window.location.href = '${pageContext.request.contextPath}/battle';
                    }
                },
                
                // イベント（モンスター）の表示
                events: function(info, successCallback, failureCallback) {
                    // サーバーからモンスターデータを取得
                    fetch('${pageContext.request.contextPath}/api/monsters')
                        .then(response => response.json())
                        .then(data => {
                            successCallback(data.map(monster => ({
                                title: monster.name,
                                start: monster.date,
                                allDay: true,
                                color: monster.defeated ? 'gray' : 'red'
                            })));
                        })
                        .catch(error => {
                            console.error('Error fetching monster data:', error);
                            failureCallback(error);
                        });
                }
            });
            
            calendar.render();
        });
    </script>
</body>
</html>