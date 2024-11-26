package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.AppUsersDTO;

/**
 * 管理者権限チェック用フィルター
 * /admin/以下のURLにアクセスがあった際に権限チェックを行う
 */
@WebFilter("/admin/*") // 管理者用URLパターンを指定
public class AdminAuthFilter implements Filter {

	/**
	 * フィルター初期化時の処理
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// 初期化が必要な場合はここに記述
	}

	/**
	 * フィルター終了時の処理
	 */
	public void destroy() {
		// リソースの解放が必要な場合はここに記述
	}

	/**
	 * フィルター本体の処理
	 * リクエスト時に実行される
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

		// ServletRequestをHttpServletRequestにキャスト
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// リクエストURLの取得
		String requestURI = httpRequest.getRequestURI();

		// ログイン処理は除外（無限ループ防止）
		if (requestURI.contains("/admin/login")) {
			chain.doFilter(request, response);
			return;
		}

		// セッションの取得（新規作成はしない）
		HttpSession session = httpRequest.getSession(false);

		// セッションチェック
		if (session == null || session.getAttribute("adminUser") == null) {
			// セッションがない場合は管理者ログイン画面へリダイレクト
			httpResponse.sendRedirect(httpRequest.getContextPath() +
					"/jsp/admin/login.jsp");
			return;
		}

		// 管理者権限チェック
		AppUsersDTO user = (AppUsersDTO) session.getAttribute("adminUser");
		if (!user.isAdmin()) {
			// 管理者権限がない場合は管理者ログイン画面へリダイレクト
			httpResponse.sendRedirect(httpRequest.getContextPath() +
					"/jsp/admin/login.jsp");
			return;
		}

		// 権限チェックOKの場合は、次の処理（サーブレットなど）へ
		chain.doFilter(request, response);
	}
}