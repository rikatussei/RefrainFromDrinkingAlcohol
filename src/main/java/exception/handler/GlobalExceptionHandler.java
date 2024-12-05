// src/main/java/exception/handler/GlobalExceptionHandler.java
package exception.handler;

import exception.ApplicationException;
import logging.ApplicationLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionHandler {

	public static void handleException(HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws ServletException, IOException {

		ApplicationLogger.logError("エラーが発生しました", e);

		if (e instanceof ApplicationException) {
			handleApplicationException(request, response, (ApplicationException) e);
		} else {
			handleUnexpectedException(request, response, e);
		}
	}

	private static void handleApplicationException(HttpServletRequest request,
			HttpServletResponse response,
			ApplicationException e) throws ServletException, IOException {
		request.setAttribute("errorCode", e.getErrorCode());
		request.setAttribute("errorMessage", e.getMessage());
		request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
	}

	private static void handleUnexpectedException(HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws ServletException, IOException {
		request.setAttribute("errorCode", "E999");
		request.setAttribute("errorMessage", "予期せぬエラーが発生しました");
		request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
	}}

	// src/main/webapp/WEB-INF/jsp/error.jsp
	<%@page language="java"contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core"prefix="c"%><!DOCTYPE html><html><head><meta charset="UTF-8">
   <title>エラー - 飲酒を控え隊</title><
	link href = "${pageContext.request.contextPath}/css/common.css"rel="stylesheet"><
	link href = "${pageContext.request.contextPath}/css/error.css"rel="stylesheet"></head><body><div class="error-container"><h1>エラーが発生しました</h1><div class="error-details"><p>エラーコード:${errorCode}</p><p>エラーメッセージ:${errorMessage}</p></div><div class="error-actions"><
a href = "${pageContext.request.contextPath}/home"class="btn-primary">ホームに戻る</a></div></div></body></html
>