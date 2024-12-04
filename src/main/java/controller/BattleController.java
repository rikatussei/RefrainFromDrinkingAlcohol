package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.AppUsersDTO;
import dto.MonstersDTO;
import service.MonsterService;

/**
 * Servlet implementation class BattleController
 */
//BattleController.java
//場所: src/main/java/controller/BattleController.java

@WebServlet("/battle")
public class BattleController extends HttpServlet {
	private final MonsterService monsterService = new MonsterService();
	private final BattleService battleService = new BattleService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendRedirect("/RefrainFromDrinkingAlcohol/login");
			return;
		}

		try {
			// 今日のモンスター取得
			MonstersDTO monster = monsterService.getDailyMonster();
			request.setAttribute("monster", monster);

			// 戦闘画面表示
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/battle.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/RefrainFromDrinkingAlcohol/error");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		AppUsersDTO user = (AppUsersDTO) session.getAttribute("dto");

		boolean didDrink = Boolean.parseBoolean(request.getParameter("drinking"));
		String comment = request.getParameter("comment");

		try {
			// 攻撃処理実行
			battleService.processAttack(user.getId(), didDrink, comment);
			response.sendRedirect("/RefrainFromDrinkingAlcohol/battle");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "攻撃処理中にエラーが発生しました");
			doGet(request, response);
		}
	}
}

//BattleService.java
//場所: src/main/java/service/BattleService.java

public class BattleService {
	private static final int BASE_ATTACK = 10;
	private static final double DRINKING_PENALTY = 0.5;
	private static final double NO_DRINKING_BONUS = 1.5;

	public void processAttack(int userId, boolean didDrink, String comment) throws Exception {
		try (Connection conn = getConnection()) {
			// 感想の感情分析スコア算出（0.0-1.0）
			double sentimentScore = analyzeSentiment(comment);

			// 攻撃力計算
			int attackPower = calculateAttackPower(didDrink, sentimentScore);

			// 攻撃記録
			recordAttack(conn, userId, didDrink, comment, attackPower);

			// モンスターのHP更新
			updateMonsterHp(conn, attackPower);
		}
	}

	private int calculateAttackPower(boolean didDrink, double sentimentScore) {
		double multiplier = didDrink ? DRINKING_PENALTY : NO_DRINKING_BONUS;
		return (int) (BASE_ATTACK * multiplier * (1 + sentimentScore));
	}

	private void recordAttack(Connection conn, int userId, boolean didDrink,
			String comment, int damage) throws SQLException {
		String sql = "INSERT INTO daily_attacks (user_id, monster_id, attack_date, " +
				"drinking, comment, damage) VALUES (?, " +
				"(SELECT id FROM monsters WHERE spawn_date = CURRENT_DATE), " +
				"CURRENT_DATE, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setBoolean(2, didDrink);
			ps.setString(3, comment);
			ps.setInt(4, damage);
			ps.executeUpdate();
		}
	}
}