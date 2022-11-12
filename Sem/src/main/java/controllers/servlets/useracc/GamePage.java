package controllers.servlets.useracc;

import models.entities.Game;
import models.entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.game.GameService;

import java.io.IOException;
import java.util.List;

@WebServlet("/game")
public class GamePage extends HttpServlet {
    GameService gameService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gameService= (GameService) config.getServletContext().getAttribute("gameService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Game game= gameService.findById(Long.parseLong(req.getParameter("ClickGame")));

        req.setAttribute("images",gameService.getImageOfGame(game.getId()));
        if(req.getSession().getAttribute("CurrentUser")!=null){
            User user= (User) req.getSession().getAttribute("CurrentUser");
            List<Long> PurchasedGames=gameService.purchasedGames(user.getId());
            if(!PurchasedGames.isEmpty()){
                req.setAttribute("PurchasedGames",PurchasedGames);
            }
        }
        req.setAttribute("Game",game);
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("WEB-INF/views/game.jsp");
        requestDispatcher.forward(req,resp);
    }

}
