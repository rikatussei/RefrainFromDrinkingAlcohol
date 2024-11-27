<%-- ホーム画面のJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ホーム画面</title>
</head>
<body>
    <%-- ログインユーザー情報表示部分 --%>
    <div class="user-info">
    <h1>ホーム画面</h1>
    <%-- DTOからユーザー名を取得して表示 --%>
       <p>${dto.name} さんでログイン中</p>
    </div>
	<%-- ナビゲーションリンク --%>
	<div class="navigation">
        <a href="/RefrainFromDrinkingAlcohol/logout">ログアウト</a>
        <a href="${pageContext.request.contextPath}/profile">プロフィール</a>
    </div>
    
    
    <%-- カレンダー表示エリア --%>
    <div class="calendar-area">
        <h2>${year}年${month}月</h2>
        <table border="1">
            <tr>
                <th>日</th>
                <th>月</th>
                <th>火</th>
                <th>水</th>
                <th>木</th>
                <th>金</th>
                <th>土</th>
            </tr>
            <%-- カレンダーデータをループで表示 --%>
            <c:forEach items="${calendarData}" var="week">
                <tr>
                    <c:forEach items="${week}" var="day">
                        <td class="${day.today ? 'today' : ''}">
                            ${day.dayOfMonth}
                            <%-- その日のモンスターが存在する場合 --%>
                            <c:if test="${day.hasMonster}">
                                <div class="monster-info">
                                    <c:if test="${day.today}">
                                        <%-- 当日の場合は戦闘画面へのリンクを表示 --%>
                                        <a href="${pageContext.request.contextPath}/battle">
                                            👾
                                        </a>
                                    </c:if>
                                </div>
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>