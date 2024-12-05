@WebServlet("/admin/monster/*")
public class AdminMonsterController extends HttpServlet {
   private final MonsterService monsterService = new MonsterService();
   private final CommentService commentService = new CommentService();

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       if (!isAdmin(request)) {
           response.sendRedirect(request.getContextPath() + "/admin/login");
           return;
       }

       String action = request.getPathInfo();
       switch (action) {
           case "/list":
               listMonsters(request, response);
               break;
           case "/view":
               viewMonsterDetails(request, response);
               break;
           default:
               response.sendError(HttpServletResponse.SC_NOT_FOUND);
       }
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       if (!isAdmin(request)) {
           response.sendRedirect(request.getContextPath() + "/admin/login");
           return;
       }

       String action = request.getPathInfo();
       switch (action) {
           case "/generate":
               generateMonster(request, response);
               break;
           case "/delete":
               deleteMonster(request, response);
               break;
           case "/deleteComment":
               deleteComment(request, response);
               break;
           default:
               response.sendError(HttpServletResponse.SC_NOT_FOUND);
       }
   }

   private boolean isAdmin(HttpServletRequest request) {
       AppUsersDTO user = (AppUsersDTO) request.getSession().getAttribute("adminUser");
       return user != null && user.isAdmin();
   }

   private void generateMonster(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       try {
           monsterService.generateDailyMonster(true);
           response.sendRedirect(request.getContextPath() + "/admin/monster/list");
       } catch (Exception e) {
           throw new ServletException("モンスター生成に失敗しました", e);
       }
   }

   private void deleteMonster(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       try {
           int monsterId = Integer.parseInt(request.getParameter("id"));
           monsterService.deleteMonster(monsterId);
           response.sendRedirect(request.getContextPath() + "/admin/monster/list");
       } catch (Exception e) {
           throw new ServletException("モンスター削除に失敗しました", e);
       }
   }

   private void deleteComment(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       try {
           int commentId = Integer.parseInt(request.getParameter("commentId"));
           commentService.deleteComment(commentId);
           response.sendRedirect(request.getContextPath() + "/admin/monster/view?id=" + 
               request.getParameter("monsterId"));
       } catch (Exception e) {
           throw new ServletException("コメント削除に失敗しました", e);
       }
   }

   private void listMonsters(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       try {
           List<MonstersDTO> monsters = monsterService.getAllMonsters();
           request.setAttribute("monsters", monsters);
           request.getRequestDispatcher("/jsp/admin/monster/list.jsp")
                 .forward(request, response);
       } catch (Exception e) {
           throw new ServletException("モンスター一覧の取得に失敗しました", e);
       }
   }

   private void viewMonsterDetails(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       try {
           int monsterId = Integer.parseInt(request.getParameter("id"));
           MonstersDTO monster = monsterService.getMonsterById(monsterId);
           List<CommentsDTO> comments = commentService.getCommentsByMonsterId(monsterId);
           
           request.setAttribute("monster", monster);
           request.setAttribute("comments", comments);
           request.getRequestDispatcher("/jsp/admin/monster/view.jsp")
                 .forward(request, response);
       } catch (Exception e) {
           throw new ServletException("モンスター詳細の取得に失敗しました", e);
       }
   }
}