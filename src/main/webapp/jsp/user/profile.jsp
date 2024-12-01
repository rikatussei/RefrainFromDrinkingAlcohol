```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>プロフィール - 飲酒を控え隊</title>
        <!-- CSS読み込み -->
    <link rel="stylesheet" href="/RefrainFromDrinkingAlcohol/css/common.css">
    <link rel="stylesheet" href="/RefrainFromDrinkingAlcohol/css/profile.css">
    <!-- FullCalendarのCDN -->
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
    
    <style>
    </style>
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
                <a href="/RefrainFromDrinkingAlcohol/user/profile/edit">プロフィール編集</a>
                <a href="/RefrainFromDrinkingAlcohol/user/home">ホームに戻る</a>
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
```