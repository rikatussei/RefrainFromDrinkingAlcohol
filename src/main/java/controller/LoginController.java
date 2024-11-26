package controller;

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
import validation.Validation;

//他のファイルからアクセスするときの名称　※login.jspやnews.jspの「form action="～～"」と一致させる
@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//コントローラー実行時に動くdoGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// login.jspにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		//パスワードをString型で取得
		String passwordStr = request.getParameter("password");

		String loginId = request.getParameter("loginId");

		// Validationインスタンス生成
		Validation validation = new Validation();
		//未入力でないかを確認
		validation.isNull("ユーザー名", name);

		validation.isNull("パスワード", passwordStr);

		//文字数を確認
		validation.length("ユーザー名", name, 2, 20); // 2文字以上20文字以内に設定

		//もしヴァリデーションインスタンスのerrorMsgにエラーメッセージがあるなら
		if (validation.hasErrorMsg()) {
			//リクエストスコープにエラーメッセージのリストを保存(名前は"errorMsg")
			request.setAttribute("errorMsg", validation.getErrorMsg());

			//login.jspにフォワード(※リダイレクトだとエラーメッセージは保持されない)
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
			rd.forward(request, response);

			//処理終了
			return;
		}

		AppUsersDTO login = new AppUsersDTO(name, passwordStr, loginId);
		AppUsersDAO dao = new AppUsersDAO();
		login = dao.login(login);

		if (login != null) {
			HttpSession session = request.getSession();
			session.setAttribute("dto", login);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/home.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect("/RefrainFromDrinkingAlcohol/jsp/login.jsp");
		}
	}

}
