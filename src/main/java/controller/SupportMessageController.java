package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.AISupportService;

@WebServlet("/api/support-message")
public class SupportMessageController extends HttpServlet {
	private final AISupportService aiService = new AISupportService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userContext = request.getParameter("context");
		String supportMessage = aiService.generateDailySupportMessage(userContext);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("message", supportMessage);

		response.getWriter().write(jsonResponse.toString());
	}
}