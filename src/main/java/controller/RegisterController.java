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
import util.BreadcrumbItem;

//他のファイルからアクセスするときの名称
@WebServlet("/register")
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//コントローラー実行時に動くdoGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パンくずリストの設定
		List<BreadcrumbItem> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new BreadcrumbItem("ホーム", "/RefrainFromDrinkingAlcohol/"));
		breadcrumbs.add(new BreadcrumbItem("新規登録", null));
		request.setAttribute("breadcrumbItems", breadcrumbs);

		// register.jspにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
		rd.forward(request, response);
	}

	//register.jspの「登録」押下時に動くdoPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			// register.jspの"name"を取得
			String name = request.getParameter("name");

			// register.jspの"password"を取得
			// ※変更点1: パスワードをString型として取得
			String password = request.getParameter("password");

			// リクエストパラメータからログインIDを取得
			String loginId = request.getParameter("loginId");

			// AppUsersDTOのインスタンスを作成
			AppUsersDTO dto = new AppUsersDTO(name, password, loginId); // loginId を渡す

			// registerメソッドの戻り値格納用の変数を0で初期化
			int result = 0;

			//DAO爆誕
			AppUsersDAO dao = new AppUsersDAO();

			//DAOのログインメソッドを実行し、戻り値をDTOに格納
			result = dao.register(dto);

			//レジスターメソッドの戻り値でresultが0でないなら
			if (result != 0) {
				// リクエストスコープにDTOを保存
				request.setAttribute("dto", dto);

				//registerDone.jspにフォワード
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/registerDone.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			HttpSession session = request.getSession();
			session.setAttribute("errorMsg", "エラー発生");
			//register.jspにリダイレクト
			response.sendRedirect("/RefrainFromDrinkingAlcohol/jsp/register.jsp");
		}
	}
}
