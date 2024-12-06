// src/main/java/controller/battle/BattleController.java
package controller.battle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AppUsersDTO;
import dto.MonstersDTO;
import service.BattleService;
import service.MonsterService;

@WebServlet("/battle")
public class BattleController extends HttpServlet {
	private final BattleService battleService = new BattleService();
	private final MonsterService monsterService = new MonsterService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AppUsersDTO user = (AppUsersDTO) request.getSession(false).getAttribute("dto");
			MonstersDTO monster = monsterService.getTodayMonster();

			if (monster == null) {
				monster = monsterService.generateDailyMonster(false);
			}

			request.setAttribute("monster", monster);
			request.setAttribute("canAttack", battleService.canUserAttack(user.getId()));
			request.getRequestDispatcher("/jsp/battle/battle.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("戦闘画面の表示に失敗しました", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AppUsersDTO user = (AppUsersDTO) request.getSession(false).getAttribute("dto");
			boolean didDrink = Boolean.parseBoolean(request.getParameter("drinking"));
			String comment = request.getParameter("comment");

			battleService.processAttack(user.getId(), didDrink, comment);
			response.sendRedirect(request.getContextPath() + "/battle");
		} catch (Exception e) {
			throw new ServletException("攻撃処理に失敗しました", e);
		}
	}
}