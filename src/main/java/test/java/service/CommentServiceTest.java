// src/test/java/service/CommentServiceTest.java
package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dto.CommentsDTO;
import service.CommentService;

class CommentServiceTest {

	@Mock
	private Connection mockConnection;

	@Mock
	private PreparedStatement mockPreparedStatement;

	@Mock
	private ResultSet mockResultSet;

	private CommentService commentService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		commentService = new CommentService();

		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
	}

	@Test
	void testGetCommentsByMonsterId() throws Exception {
		// モックデータの設定
		when(mockResultSet.next()).thenReturn(true, true, false);
		when(mockResultSet.getInt("id")).thenReturn(1, 2);
		when(mockResultSet.getString("text")).thenReturn("テストコメント1", "テストコメント2");
		when(mockResultSet.getInt("user_id")).thenReturn(1, 2);
		when(mockResultSet.getString("user_name")).thenReturn("ユーザー1", "ユーザー2");
		when(mockResultSet.getTimestamp("created_at")).thenReturn(
				new Timestamp(System.currentTimeMillis()));

		List<CommentsDTO> comments = commentService.getCommentsByMonsterId(1);

		assertNotNull(comments);
		assertEquals(2, comments.size());
		assertEquals("テストコメント1", comments.get(0).getText());
		assertEquals("テストコメント2", comments.get(1).getText());
	}

	@Test
	void testDeleteComment() throws Exception {
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);

		commentService.deleteComment(1);

		verify(mockPreparedStatement).setInt(1, 1);
		verify(mockPreparedStatement).executeUpdate();
	}

	@Test
	void testAddComment() throws Exception {
		CommentsDTO comment = new CommentsDTO();
		comment.setText("新しいコメント");
		comment.setUserId(1);
		comment.setMonsterId(1);

		when(mockPreparedStatement.executeUpdate()).thenReturn(1);

		assertDoesNotThrow(() -> commentService.addComment(comment));

		verify(mockPreparedStatement).setString(eq(1), eq("新しいコメント"));
		verify(mockPreparedStatement).setInt(eq(2), eq(1));
		verify(mockPreparedStatement).setInt(eq(3), eq(1));
	}

	@Test
	void testGetCommentsByMonsterIdEmpty() throws Exception {
		when(mockResultSet.next()).thenReturn(false);

		List<CommentsDTO> comments = commentService.getCommentsByMonsterId(1);

		assertNotNull(comments);
		assertTrue(comments.isEmpty());
	}
}