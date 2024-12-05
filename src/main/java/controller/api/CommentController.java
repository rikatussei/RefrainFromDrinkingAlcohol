package controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dto.AppUsersDTO;
import service.CommentService;

@WebServlet("/api/comments/*")
public class CommentController extends HttpServlet {
	private final CommentService commentService = new CommentService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		AppUsersDTO user = (AppUsersDTO) session.getAttribute("dto");

		// リクエストボディからJSONを読み取り
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = request.getReader().readLine()) != null) {
			buffer.append(line);
		}

		JSONObject jsonRequest = new JSONObject(buffer.toString());
		int monsterId = jsonRequest.getInt("monsterId");
		String text = jsonRequest.getString("text");

		try {
			JSONObject comment = commentService.addComment(user.getId(), monsterId, text);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(comment.toString());
			out.flush();
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		AppUsersDTO user = (AppUsersDTO) session.getAttribute("dto");

		// URLからコメントIDを抽出
		String pathInfo = request.getPathInfo();
		String[] pathParts = pathInfo.split("/");
		if (pathParts.length < 2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int commentId = Integer.parseInt(pathParts[1]);

		try {
			boolean success = commentService.deleteComment(commentId, user.getId());
			if (!success) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}