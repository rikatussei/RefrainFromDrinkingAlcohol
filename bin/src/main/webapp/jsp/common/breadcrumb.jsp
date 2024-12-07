<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="breadcrumb">
	<!-- 固定ナビゲーションメニュー -->
	<ul>
		<li><a href="home.jsp">ホーム</a></li>
		<li><a href="profile.jsp">プロフィール</a></li>
		<li><a href="home.jsp">今日のモンスター</a></li>
	</ul>

	<!-- パンくずリスト -->
	<ol>
		<li><a href="/RefrainFromDrinkingAlcohol/">トップ</a></li>
		<c:if test="${not empty breadcrumbItems}">
			<c:forEach items="${breadcrumbItems}" var="item">
				<li><c:if test="${not empty item.url}">
						<a href="${item.url}">${item.text}</a>
					</c:if> <c:if test="${empty item.url}">
                        ${item.text}
                    </c:if></li>
			</c:forEach>
		</c:if>
	</ol>
</nav>
