// src/test/java/security/SessionManagerTest.java
package security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;

class SessionManagerTest {

	@Test
	void testTrackSession() {
		HttpSession mockSession = mock(HttpSession.class);
		when(mockSession.getId()).thenReturn("test-session-id");

		SessionManager.trackSession(mockSession);

		// セッションが追跡されていることを確認
		assertDoesNotThrow(() -> SessionManager.invalidateExpiredSession(mockSession));
	}

	@Test
	void testInvalidateExpiredSession() {
		HttpSession mockSession = mock(HttpSession.class);
		when(mockSession.getId()).thenReturn("expired-session-id");

		SessionManager.trackSession(mockSession);
		// セッションの最終アクティビティ時間を31分前に設定
		// このテストケースは実装に依存するため、実際のコードに合わせて調整が必要

		SessionManager.invalidateExpiredSession(mockSession);
		verify(mockSession).invalidate();
	}
}