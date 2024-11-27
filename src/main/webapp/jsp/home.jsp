<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>飲酒を控え隊 - ホーム</title>
<style>
/* カレンダースタイル */
.calendar {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
}

.calendar th, .calendar td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}

.today {
	background-color: #e8f4ff;
}

.monster-cell {
	position: relative;
}

.monster-image {
	max-width: 50px;
	max-height: 50px;
}

/* ユーザー情報 */
.user-info {
	margin-bottom: 20px;
}

/* モンスター情報 */
.monster-info {
	border: 1px solid #ddd;
	padding: 15px;
	margin: 20px 0;
	text-align: center;
}
</style>
</head>
<body>
	<!-- ユーザー情報とログアウト -->
	<div class="user-info">
		<h1>${loginUser.name}さんのホーム画面</h1>
		<a href="${pageContext.request.contextPath}/logout">ログアウト</a> <a
			href="${pageContext.request.contextPath}/user/profile">プロフィール</a>
	</div>

	<!-- 今日のモンスター情報 -->
	<div class="monster-info">
		<h2>今日のモンスター</h2>
		<c:if test="${not empty todayMonster}">
			<img src="${todayMonster.imagePath}" alt="モンスター画像"
				style="max-width: 200px;">
			<p>モンスター名: ${todayMonster.name}</p>
			<p>HP: ${todayMonster.hp}/255</p>
			<c:if test="${not todayMonster.defeated}">
				<a href="${pageContext.request.contextPath}/battle"
					class="battle-button">挑戦する</a>
			</c:if>
			<c:if test="${todayMonster.defeated}">
				<p>討伐済み！</p>
			</c:if>
		</c:if>
	</div>

	<!-- カレンダー表示 -->
	<div class="calendar-container">
		<h2>${currentYear}年${currentMonth}月</h2>
		<table class="calendar">
			<tr>
				<th>日</th>
				<th>月</th>
				<th>火</th>
				<th>水</th>
				<th>木</th>
				<th>金</th>
				<th>土</th>
			</tr>
			<c:forEach items="${calendarData}" var="week">
				<tr>
					<c:forEach items="${week}" var="day">
						<td
							class="${day.isToday ? 'today' : ''} ${day.hasMonster ? 'monster-cell' : ''}">
							${day.dayOfMonth} <c:if test="${day.hasMonster}">
								<div class="monster-info-mini">
									<c:if test="${day.isToday}">
										<a href="${pageContext.request.contextPath}/battle"> <img
											src="${day.monsterImage}" alt="モンスター" class="monster-image">
										</a>
									</c:if>
									<c:if test="${not day.isToday}">
										<img src="${day.monsterImage}" alt="モンスター"
											class="monster-image">
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