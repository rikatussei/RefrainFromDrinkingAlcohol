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
				href="${pageContext.request.contextPath}/logout"
				onclick="return confirmLogout();">ログアウト</a>
		</div>

		<!-- モンスター情報の表示 -->
		<div class="monster-info">
			<h2>今日のモンスター</h2>
			<c:if test="${not empty monster}">
				<div class="monster-details">
					<div class="monster-image">
						<img src="data:image/png;base64,${monster.imageBase64}"
							alt="${monster.name}" />
					</div>
					<div class="monster-stats">
						<p>名前: ${monster.name}</p>
						<p>HP: ${monster.hp}/255</p>
						<div class="hp-bar">
							<div class="hp-value"
								style="width: ${(monster.hp / 255.0) * 100}%"></div>
						</div>
						<c:if test="${not monster.defeated}">
							<a href="${pageContext.request.contextPath}/battle"
								class="battle-button">戦闘する！</a>
						</c:if>
						<c:if test="${monster.defeated}">
							<p class="defeated-message">撃破済み！</p>
						</c:if>
					</div>
				</div>
			</c:if>
			<c:if test="${empty monster}">
				<p>本日のモンスターはまだ生成されていません。</p>
			</c:if>
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
                        // モンスター情報の取得と戦闘ページへの遷移
                        fetch('${pageContext.request.contextPath}/api/daily-monster')
                            .then(response => response.json())
                            .then(monster => {
                                if (monster && !monster.defeated) {
                                    window.location.href = 
                                        '${pageContext.request.contextPath}/battle';
                                } else if (monster && monster.defeated) {
                                    alert('本日のモンスターは既に倒されています');
                                } else {
                                    alert('本日のモンスターはまだ出現していません');
                                }
                            })
                            .catch(error => {
                                console.error('Error:', error);
                                alert('モンスター情報の取得に失敗しました');
                            });
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
                                color: monster.defeated ? 'gray' : 'red',
                                extendedProps: {
                                    defeated: monster.defeated
                                }
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
