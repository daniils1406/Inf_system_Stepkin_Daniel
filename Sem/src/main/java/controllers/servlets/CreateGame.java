package controllers.servlets;

import models.entities.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.game.GameService;
import models.service.user.UserService;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/createGame")
public class CreateGame extends HttpServlet {
    GameService gameService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gameService= (GameService) config.getServletContext().getAttribute("gameService");
        userService= (UserService) config.getServletContext().getAttribute("userService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idOfNewGame=gameService.signUp(req.getParameter("name"),req.getParameter("developer"),req.getParameter("publisher"),req.getParameter("genre"),Long.parseLong(req.getParameter("cost")),req.getParameter("img"));
        User user=(User) req.getSession().getAttribute("CurrentUser");
        if(Listener.getMapOfOverviewImages().get(user.getId())!=null && !Listener.getMapOfOverviewImages().get(user.getId()).isEmpty()){
            gameService.addImgToGame(Listener.getMapOfOverviewImages().get(user.getId()), idOfNewGame);
            Listener.getMapOfOverviewImages().put(user.getId(), new LinkedList<>());
        }
        resp.sendRedirect("/user");
    }
}
