// battle/attack_form.jsp - 攻撃フォーム
<div class="attack-form">
	<form action="${pageContext.request.contextPath}/battle" method="post">
		<div class="form-group">
			<label>飲酒状況：</label> <select name="drinking" required>
				<option value="false">飲酒していない</option>
				<option value="true">飲酒した</option>
			</select>
		</div>
		<div class="form-group">
			<label>コメント：</label>
			<textarea name="comment" required></textarea>
		</div>
		<button type="submit">攻撃する！</button>
	</form>
</div>