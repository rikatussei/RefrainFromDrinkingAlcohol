<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>飲酒を控え隊</title>
</head>
<body>
    <h1>飲酒を控え隊</h1>
    
    <%-- エラーメッセージ表示部分 --%>
    <c:if test="${not empty errorMsg}">
        <ul>
            <c:forEach var="msg" items="${errorMsg}">
                <li>${msg}</li>
            </c:forEach>
        </ul>
    </c:if>

    <%-- ログイン選択フォーム --%>
    <table>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/jsp/login.jsp">ユーザーログイン</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/jsp/admin/login.jsp">管理者ログイン</a>
            </td>
        </tr>
    </table>
</body>
</html>