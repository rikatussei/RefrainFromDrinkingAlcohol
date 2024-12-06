// src/test/java/controller/admin/AdminMonsterControllerTest.java
package controller.admin;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dto.AppUsersDTO;
import service.MonsterService;

class AdminMonsterControllerTest {
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@Mock
	private RequestDispatcher dispatcher;
	@Mock
	private MonsterService monsterService;

	private AdminMonsterController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new AdminMonsterController();
		when(request.getSession(false)).thenReturn(session);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	}

	@Test
	void testDoGetWithAdminUser() throws Exception {
		AppUsersDTO adminUser = new AppUsersDTO();
		adminUser.setAdmin(true);
		when(session.getAttribute("adminUser")).thenReturn(adminUser);

		controller.doGet(request, response);
		verify(dispatcher).forward(request, response);
	}

	@Test
	void testDoGetWithNonAdminUser() throws Exception {
		AppUsersDTO user = new AppUsersDTO();
		user.setAdmin(false);
		when(session.getAttribute("adminUser")).thenReturn(user);

		controller.doGet(request, response);
		verify(response).sendRedirect(anyString());
	}
}