// src/main/java/filter/AdminAuthFilter.java
package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.AppUsersDTO;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		if (isLoginRequest(httpRequest)) {
			chain.doFilter(request, response);
			return;
		}

		if (session == null || !isAdminUser(session)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login");
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isLoginRequest(HttpServletRequest request) {
		return request.getRequestURI().contains("/admin/login");
	}

	private boolean isAdminUser(HttpSession session) {
		AppUsersDTO user = (AppUsersDTO) session.getAttribute("adminUser");
		return user != null && user.isAdmin();
	}
}