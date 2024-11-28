```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>プロフィール - 飲酒を控え隊</title>
    
    <!-- FullCalendarのCDN -->
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
    
    <style>
        /* コンテナスタイル */
        .container {
            width: 90%;
            margin: 0 auto;
            padding: 20px;
        }
        
        /* プロフィール情報エリア */
        .profile-info {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        /* カレンダーエリア */
        #calendar {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        
        /* トークン表示用スタイル */
        .battle-token {
            display: inline-block;
            width: 20px;
            height: 20px;
            background: gold;
            border-radius: 50%;
            margin: 2px;
        }
        
        /* ナビゲーションリンク */
        .nav-links {
            margin: 20px 0;
        }
        
        .nav-links a {
            margin-right: 10px;
            text-decoration: none;
            padding: 8px 15px;
            border-radius: 4px;
            background: #4CAF50;
            color: white;
        }
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