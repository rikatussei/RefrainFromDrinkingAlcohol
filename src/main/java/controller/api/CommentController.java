package controller.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dto.AppUsersDTO;
import service.CommentService;

/**
 * コメント機能のAPIエンドポイントを提供するサーブレット
 */
@WebServlet("/api/comments/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CommentService commentService = new CommentService();

	/**
	 * コメントの投稿を処理
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		try {
			// リクエストボディの読み取り
			StringBuilder buffer = new StringBuilder();
			String line;
			while ((line = request.getReader().readLine()) != null) {
				buffer.append(line);
			}

			// JSONデータのパース
			JSONObject jsonRequest = new JSONObject(buffer.toString());
			int monsterId = jsonRequest.getInt("monsterId");
			String text = jsonRequest.getString("text");
			AppUsersDTO user = (AppUsersDTO) session.getAttribute("dto");

			// コメントの保存
			JSONObject comment = commentService.addComment(user.getId(), monsterId, text);

			// レスポンスの送信
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(comment.toString());
			out.flush();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * コメントの削除を処理
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		try {
			// URLからコメントIDを取得
			String pathInfo = request.getPathInfo();
			if (pathInfo == null || !pathInfo.matches("/\\d+")) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			int commentId = Integer.parseInt(pathInfo.substring(1));
			AppUsersDTO user = (AppUsersDTO) session.getAttribute("dto");

			// コメントの削除
			boolean success = commentService.deleteComment(commentId, user.getId());
			if (!success) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}