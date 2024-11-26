package controller.admin;

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

/**
 * 管理者ログイン処理を制御するサーブレット
 */
@WebServlet("/admin/login")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 管理者ログイン画面表示処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 管理者ログイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
     * 管理者ログイン認証処理
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // リクエストパラメータ取得
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        
        // エラーメッセージ用リスト
        List<String> errorMsg = new ArrayList<>();
        
        // 入力チェック
        if (loginId == null || loginId.trim().isEmpty()) {
            errorMsg.add("ログインIDを入力してください");
        }
        if (password == null || password.trim().isEmpty()) {
            errorMsg.add("パスワードを入力してください");
        }
        
        // エラーがある場合は、ログイン画面に戻る
        if (!errorMsg.isEmpty()) {
            request.setAttribute("errorMsg", errorMsg);
            request.setAttribute("loginId", loginId);
            RequestDispatcher dispatcher = 
                request.getRequestDispatcher("/jsp/admin/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        try {
            // ログイン認証処理
            AppUsersDAO dao = new AppUsersDAO();
            AppUsersDTO loginDto = new AppUsersDTO();
            loginDto.setLoginId(loginId);
            loginDto.setPassword(password);
            
            // ユーザー情報を取得
            AppUsersDTO user = dao.login(loginDto);
            
            // 認証成功かつ管理者権限を持っている場合
            if (user != null && user.isAdmin()) {
                // セッションにユーザー情報を保存
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", user);
                
                // 管理者ダッシュボードにリダイレクト
                response.sendRedirect(request.getContextPath() + 
                                    "/admin/dashboard");
            }
            // 認証失敗または管理者権限がない場合
            else {
                errorMsg.add(user == null ? 
                    "ログインIDまたはパスワードが間違っています" : 
                    "管理者権限がありません");
                request.setAttribute("errorMsg", errorMsg);
                request.setAttribute("loginId", loginId);
                RequestDispatcher dispatcher = 
                    request.getRequestDispatcher("/jsp/admin/login.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (Exception e) {
            // システムエラー時の処理
            errorMsg.add("システムエラーが発生しました。管理者に連絡してください。");
            request.setAttribute("errorMsg", errorMsg);
            RequestDispatcher dispatcher = 
                request.getRequestDispatcher("/jsp/admin/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}