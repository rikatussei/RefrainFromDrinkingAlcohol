// コメント機能の実装
document.addEventListener('DOMContentLoaded', function() {
	const commentForm = document.getElementById('commentForm');
	const commentsList = document.getElementById('commentsList');

	// コメントフォームの送信処理
	commentForm.addEventListener('submit', async function(e) {
		e.preventDefault();

		const commentText = document.getElementById('commentText').value;
		const monsterId = document.getElementById('monsterId').value;

		try {
			const response = await fetch('/RefrainFromDrinkingAlcohol/api/comments', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					monsterId: monsterId,
					text: commentText
				})
			});

			if (response.ok) {
				const comment = await response.json();
				addCommentToList(comment);
				commentForm.reset();
			} else {
				throw new Error('コメントの投稿に失敗しました');
			}
		} catch (error) {
			console.error('Error:', error);
			alert('コメントの投稿に失敗しました');
		}
	});

	// コメントの削除機能
	window.deleteComment = async function(commentId) {
		if (!confirm('このコメントを削除しますか？')) {
			return;
		}

		try {
			const response = await fetch(`/RefrainFromDrinkingAlcohol/api/comments/${commentId}`, {
				method: 'DELETE'
			});

			if (response.ok) {
				const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
				if (commentElement) {
					commentElement.remove();
				}
			} else {
				throw new Error('コメントの削除に失敗しました');
			}
		} catch (error) {
			console.error('Error:', error);
			alert('コメントの削除に失敗しました');
		}
	};

	// コメントをリストに追加する関数
	function addCommentToList(comment) {
		const commentHtml = `
            <div class="comment-card own-comment" data-comment-id="${comment.id}">
                <div class="comment-header">
                    <span class="commenter-name">${comment.userName}</span>
                    <span class="comment-time">${new Date(comment.createdAt).toLocaleString()}</span>
                </div>
                <div class="comment-content">${comment.text}</div>
                <button onclick="deleteComment(${comment.id})" class="delete-comment">削除</button>
            </div>
        `;
		commentsList.insertAdjacentHTML('afterbegin', commentHtml);
	}
});