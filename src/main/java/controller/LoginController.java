package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AppUsersDAO;
import dto.AppUsersDTO;
import dto.MonstersDTO;
import service.RandomMonsterService;
import util.BreadcrumbItem;
import validation.Validation;

// URLパターンの指定
@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final RandomMonsterService monsterService;

	// コンストラクタ
	public LoginController() {
		this.monsterService = new RandomMonsterService();
	}

	// GETリクエスト処理（ログイン画面表示）
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パンくずリストの設定
		List<BreadcrumbItem> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new BreadcrumbItem("ホーム", "/RefrainFromDrinkingAlcohol/"));
		breadcrumbs.add(new BreadcrumbItem("ログイン", null));
		request.setAttribute("breadcrumbItems", breadcrumbs);

		// login.jspにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
		rd.forward(request, response);
	}

	// POSTリクエスト処理（ログイン処理）
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストの文字エンコーディング設定
		request.setCharacterEncoding("UTF-8");

		// フォームからのパラメータ取得
		String name = request.getParameter("name");
		String passwordStr = request.getParameter("password");
		String loginId = request.getParameter("loginId");

		// バリデーションインスタンス生成
		Validation validation = new Validation();

		// 入力値の検証
		validation.isNull("ユーザー名", name);
		validation.isNull("パスワード", passwordStr);
		validation.length("ユーザー名", name, 2, 20); // 2文字以上20文字以内

		// バリデーションエラーがある場合
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorMsg", validation.getErrorMsg());
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
			rd.forward(request, response);
			return;
		}

		try {
			// ログイン情報のDTOを作成
			AppUsersDTO login = new AppUsersDTO(name, passwordStr, loginId);
			AppUsersDAO dao = new AppUsersDAO();

			// ログイン認証
			login = dao.login(login);

			// ログイン成功時の処理
			if (login != null) {
				// セッション作成とユーザー情報の保存
				HttpSession session = request.getSession();
				session.setAttribute("dto", login);

				// モンスター生成処理
				try {
					if (monsterService.shouldGenerateMonster()) {
						monsterService.generateRandomMonster();
					}

					// 生成されたモンスター情報の取得
					MonstersDTO monster = monsterService.getCurrentMonster();
					if (monster != null) {
						session.setAttribute("monster", monster);
					}
				} catch (Exception e) {
					// モンスター生成エラーはログに記録するが、ログイン処理は継続
					e.printStackTrace();
					System.err.println("モンスター生成中にエラーが発生しました: " + e.getMessage());
				}

				// ホーム画面へフォワード
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/home.jsp");
				rd.forward(request, response);
			} else {
				// ログイン失敗時はログイン画面へリダイレクト
				List<String> errorMsg = new ArrayList<>();
				errorMsg.add("ログインIDまたはパスワードが間違っています");
				request.setAttribute("errorMsg", errorMsg);
				request.setAttribute("loginId", loginId);
				request.setAttribute("name", name);
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			// システムエラー時の処理
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("errorMsg", "システムエラーが発生しました");
			response.sendRedirect("/RefrainFromDrinkingAlcohol/jsp/error.jsp");
		}
	}
}