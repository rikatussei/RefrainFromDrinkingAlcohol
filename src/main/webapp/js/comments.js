/**
 * コメント機能の実装
 */
document.addEventListener('DOMContentLoaded', function() {
	// DOM要素の取得
	const commentForm = document.getElementById('commentForm');
	const commentsList = document.getElementById('commentsList');

	// フォームが存在するか確認
	if (!commentForm || !commentsList) {
		console.error('必要なDOM要素が見つかりません');
		return;
	}

	/**
	 * CSRFトークンの取得
	 * @returns {string} CSRFトークン
	 */
	function getCsrfToken() {
		const tokenMeta = document.querySelector('meta[name="_csrf"]');
		return tokenMeta ? tokenMeta.content : '';
	}

	/**
	 * コメントフォームの送信処理
	 */
	commentForm.addEventListener('submit', async function(e) {
		e.preventDefault();

		// フォームデータの取得と検証
		const commentText = document.getElementById('commentText')?.value?.trim();
		const monsterId = document.getElementById('monsterId')?.value;

		if (!commentText || !monsterId) {
			alert('コメント内容を入力してください');
			return;
		}

		try {
			// APIリクエストの送信
			const response = await fetch('/RefrainFromDrinkingAlcohol/api/comments', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'X-CSRF-TOKEN': getCsrfToken()
				},
				body: JSON.stringify({
					monsterId: monsterId,
					text: commentText
				})
			});

			// レスポンスの処理
			if (response.ok) {
				const comment = await response.json();
				addCommentToList(comment);
				commentForm.reset();
			} else {
				const errorData = await response.json();
				throw new Error(errorData.message || 'コメントの投稿に失敗しました');
			}
		} catch (error) {
			console.error('Error:', error);
			alert(error.message || 'コメントの投稿に失敗しました');
		}
	});

	/**
	 * コメントの削除機能
	 * @param {number} commentId - 削除するコメントのID
	 */
	window.deleteComment = async function(commentId) {
		if (!commentId) {
			console.error('コメントIDが指定されていません');
			return;
		}

		if (!confirm('このコメントを削除しますか？')) {
			return;
		}

		try {
			const response = await fetch(`/RefrainFromDrinkingAlcohol/api/comments/${commentId}`, {
				method: 'DELETE',
				headers: {
					'X-CSRF-TOKEN': getCsrfToken()
				}
			});

			if (response.ok) {
				removeCommentFromList(commentId);
			} else {
				const errorData = await response.json();
				throw new Error(errorData.message || 'コメントの削除に失敗しました');
			}
		} catch (error) {
			console.error('Error:', error);
			alert(error.message || 'コメントの削除に失敗しました');
		}
	};

	/**
	 * コメントをリストに追加する関数
	 * @param {Object} comment - コメントデータ
	 */
	function addCommentToList(comment) {
		if (!comment || !comment.id) {
			console.error('無効なコメントデータです');
			return;
		}

		const commentHtml = `
            <div class="comment-card own-comment" data-comment-id="${comment.id}">
                <div class="comment-header">
                    <span class="commenter-name">${escapeHtml(comment.userName)}</span>
                    <span class="comment-time">
                        ${new Date(comment.createdAt).toLocaleString('ja-JP')}
                    </span>
                </div>
                <div class="comment-content">${escapeHtml(comment.text)}</div>
                <button onclick="deleteComment(${comment.id})" class="delete-comment">
                    削除
                </button>
            </div>
        `;

		commentsList.insertAdjacentHTML('afterbegin', commentHtml);

		// アニメーション効果の追加
		const newComment = commentsList.firstElementChild;
		newComment.style.opacity = '0';
		requestAnimationFrame(() => {
			newComment.style.transition = 'opacity 0.5s ease-in';
			newComment.style.opacity = '1';
		});
	}

	/**
	 * コメントをリストから削除する関数
	 * @param {number} commentId - 削除するコメントのID
	 */
	function removeCommentFromList(commentId) {
		const commentElement = document.querySelector(`[data-comment-id="${commentId}"]`);
		if (commentElement) {
			commentElement.style.transition = 'opacity 0.5s ease-out';
			commentElement.style.opacity = '0';
			setTimeout(() => commentElement.remove(), 500);
		}
	}

	/**
	 * HTML特殊文字のエスケープ処理
	 * @param {string} text - エスケープする文字列
	 * @returns {string} エスケープされた文字列
	 */
	function escapeHtml(text) {
		const div = document.createElement('div');
		div.textContent = text;
		return div.innerHTML;
	}
});