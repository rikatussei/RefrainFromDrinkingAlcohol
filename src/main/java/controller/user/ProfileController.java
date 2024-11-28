package controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dto.AppUsersDTO;

/**
 * プロフィール画面の制御を行うサーブレット
 */
@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * プロフィール画面表示用のGET処理
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // セッションチェック
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("/RefrainFromDrinkingAlcohol/login");
            return;
        }

        try {
            // セッションからユーザー情報を取得
            AppUsersDTO loginUser = (AppUsersDTO) session.getAttribute("loginUser");
            
            // リクエスト属性にユーザー情報を設定
            request.setAttribute("user", loginUser);

            // 戦闘結果データの取得
            List<Map<String, Object>> battleResults = getBattleResults(loginUser.getId());
            Gson gson = new Gson();
            request.setAttribute("battleResults", gson.toJson(battleResults));

            // 総トークン数の取得
            int totalTokens = getTotalTokens(loginUser.getId());
            request.setAttribute("totalTokens", totalTokens);

            // プロフィール画面にフォワード
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user/profile.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/RefrainFromDrinkingAlcohol/error");
        }
    }

    /**
     * ユーザーの戦闘結果データを取得するメソッド
     * @param userId ユーザーID
     * @return 戦闘結果データのリスト
     */
    private List<Map<String, Object>> getBattleResults(int userId) {
        // TODO: 戦闘結果データ取得ロジックを実装
        return new ArrayList<>();
    }

    /**
     * ユーザーの総トークン数を取得するメソッド
     * @param userId ユーザーID
     * @return トークンの総数
     */
    private int getTotalTokens(int userId) {
        // TODO: トークン集計ロジックを実装
        return 0;
    }
}