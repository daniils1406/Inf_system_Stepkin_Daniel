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

@WebServlet("/userSendImgToGame")
public class AddImgToGamePage extends HttpServlet {
    UserService userService;
    GameService gameService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService= (UserService) config.getServletContext().getAttribute("userService");
        gameService= (GameService) config.getServletContext().getAttribute("gameService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("OverImg")!=null && req.getParameter("OverImg")!=""){
            req.getSession().setAttribute("OverviewImg",req.getParameter("OverImg"));
            req.getSession().removeAttribute("OverviewImg");
        }

        if(req.getParameter("OverImgToGame")!=null && req.getParameter("OverImgToGame")!=""){
            User user= (User) req.getSession().getAttribute("CurrentUser");
            gameService.addImgToGame(Listener.getMapOfOverviewImages().get(user.getId()), Long.valueOf(req.getParameter("OverImgToGame")));

            Listener.getMapOfOverviewImages().put(user.getId(), new LinkedList<>());
        }
        resp.sendRedirect("/userAdmin");
    }
}
