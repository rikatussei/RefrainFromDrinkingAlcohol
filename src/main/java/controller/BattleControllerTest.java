// src/test/java/controller/BattleControllerTest.java
package controller;

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
import service.BattleService;

class BattleControllerTest {
	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpSession session;

	@Mock
	private RequestDispatcher dispatcher;

	@Mock
	private BattleService battleService;

	private BattleController controller;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		controller = new BattleController();

		when(request.getSession(false)).thenReturn(session);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
	}

	@Test
	void testDoGetWithValidSession() throws Exception {
		AppUsersDTO user = new AppUsersDTO();
		when(session.getAttribute("dto")).thenReturn(user);

		controller.doGet(request, response);

		verify(dispatcher).forward(request, response);
	}
}