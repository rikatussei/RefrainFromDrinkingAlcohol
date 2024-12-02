package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.AppUsersDTO;

/**
 * プロフィール画面の制御を行うサーブレット
 */
@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * プロフィール画面表示用のGET処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("/RefrainFromDrinkingAlcohol/login");
			return;
		}

		try {
			// セッションからユーザー情報を取得
			AppUsersDTO loginUser = (AppUsersDTO) session.getAttribute("loginUser");

			// リクエスト属性にユーザー情報を設定
			request.setAttribute("user", loginUser);

			/* 戦闘結果データの取得は後で実装
			List<Map<String, Object>> battleResults = getBattleResults(loginUser.getId());
			request.setAttribute("battleResults", battleResults);
			*/

			/* トークン数の取得は後で実装
			int totalTokens = getTotalTokens(loginUser.getId());
			request.setAttribute("totalTokens", totalTokens);
			*/

			// プロフィール画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user/profile.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/RefrainFromDrinkingAlcohol/error");
		}
	}

	/*
	 * 以下のメソッドは後で実装
	private List<Map<String, Object>> getBattleResults(int userId) {
	    return new ArrayList<>();
	}
	
	private int getTotalTokens(int userId) {
	    return 0;
	}
	*/
}