// battle/comments_section.jsp - コメント部分
<div class="comments-section">
	<h3>応援コメント</h3>
	<div class="comments-container">
		<c:forEach items="${comments}" var="comment">
			<div
				class="comment-card ${comment.commentType == 'AI' ? 'ai-comment' : 'user-comment'}">
				<div class="comment-header">
					<span class="commenter-name">${comment.commentType == 'AI' ? 'AI' : comment.userName}</span>
					<span class="comment-time"><fmt:formatDate
							value="${comment.createdAt}" pattern="MM/dd HH:mm" /></span>
				</div>
				<div class="comment-text">${comment.text}</div>
			</div>
		</c:forEach>
	</div>

	<form action="${pageContext.request.contextPath}/battle/comment"
		method="post" class="comment-form">
		<input type="hidden" name="monsterId" value="${monster.id}">
		<textarea name="commentText" required></textarea>
		<button type="submit">コメントする</button>
	</form>
</div>