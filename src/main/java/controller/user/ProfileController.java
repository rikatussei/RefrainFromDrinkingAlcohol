package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AppUsersDAO;
import dto.AppUsersDTO;

/**
 * プロフィール編集画面の制御を行うサーブレット
 */
@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 編集画面表示用のGET処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションチェック
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("dto") == null) {
			response.sendRedirect("/RefrainFromDrinkingAlcohol/login");
			return;
		}

		try {
			// セッションからユーザー情報を取得
			AppUsersDTO loginUser = (AppUsersDTO) session.getAttribute("dto");

			// リクエスト属性にユーザー情報を設定
			request.setAttribute("user", loginUser);

			/* 後で実装予定：戦闘履歴の取得
			List<BattleHistoryDTO> battleHistory = getBattleHistory(loginUser.getId());
			request.setAttribute("battleHistory", battleHistory);
			*/

			/* 後で実装予定：獲得トークンの取得
			List<TokenDTO> userTokens = getUserTokens(loginUser.getId());
			request.setAttribute("userTokens", userTokens);
			*/
			// 編集画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user/profile.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/RefrainFromDrinkingAlcohol/error");
		}
	}

	/**
	 * プロフィール更新用のPOST処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 文字エンコーディングの設定
		request.setCharacterEncoding("UTF-8");

		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("/RefrainFromDrinkingAlcohol/login");
			return;
		}

		try {
			// フォームからの入力値を取得
			String name = request.getParameter("name");
			String password = request.getParameter("password");

			// セッションからユーザー情報を取得
			AppUsersDTO loginUser = (AppUsersDTO) session.getAttribute("loginUser");

			// 更新用DTOの作成
			AppUsersDTO updateUser = new AppUsersDTO();
			updateUser.setId(loginUser.getId());
			updateUser.setName(name);

			// パスワードが入力されている場合のみ設定
			if (password != null && !password.trim().isEmpty()) {
				updateUser.setPassword(password);
				/* 後で実装予定：パスワードのハッシュ化
				String hashedPassword = PasswordUtil.hashPassword(password);
				updateUser.setPassword(hashedPassword);
				*/
			}

			// DB更新処理
			AppUsersDAO dao = new AppUsersDAO();
			int result = dao.updateUser(updateUser);

			if (result > 0) {
				// 更新成功時
				// セッションのユーザー情報も更新
				loginUser.setName(name);
				session.setAttribute("loginUser", loginUser);

				/* 後で実装予定：更新履歴の記録
				logUserUpdate(loginUser.getId(), "profile_update");
				*/

				// プロフィール画面にリダイレクト
				response.sendRedirect("/RefrainFromDrinkingAlcohol/user/profile");
			} else {
				// 更新失敗時
				request.setAttribute("errorMsg", "更新に失敗しました");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user/profile_edit.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/RefrainFromDrinkingAlcohol/error");
		}
	}

	/* 後で実装予定：戦闘履歴取得メソッド
	private List<BattleHistoryDTO> getBattleHistory(int userId) {
	    return new ArrayList<>();
	}
	*/

	/* 後で実装予定：トークン取得メソッド
	private List<TokenDTO> getUserTokens(int userId) {
	    return new ArrayList<>();
	}
	*/

	/* 後で実装予定：ユーザー更新ログ記録メソッド
	private void logUserUpdate(int userId, String actionType) {
	    // 更新履歴を記録する処理
	}
	*/
}