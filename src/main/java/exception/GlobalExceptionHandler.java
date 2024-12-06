// src/main/java/exception/GlobalExceptionHandler.java
package exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/error")
public class GlobalExceptionHandler extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	private void processError(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request
				.getAttribute("javax.servlet.error.servlet_name");

		logger.error("Error in {}: {}", servletName, throwable.getMessage(), throwable);

		request.setAttribute("errorMessage", throwable.getMessage());
		request.setAttribute("statusCode", statusCode);
		request.getRequestDispatcher("/WEB-INF/jsp/error.jsp")
				.forward(request, response);
	}
}