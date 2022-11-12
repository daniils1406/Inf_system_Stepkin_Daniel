package controllers.servlets;

import models.entities.Game;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.game.GameService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@WebServlet("/gameChange")
public class ChangeGame extends HttpServlet {
    GameService gameService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gameService=(GameService)config.getServletContext().getAttribute("gameService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("newMainImage")!=null && req.getParameter("newMainImage")!=""){
            gameService.saveMainImg(req.getParameter("newMainImage"),Long.parseLong(req.getParameter("nameOfGame")));
            resp.sendRedirect("/");
            return;
        }
        Game game=null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            game= Game.builder()
                    .id(Long.parseLong(req.getParameter("id")))
                    .show(Boolean.parseBoolean(req.getParameter("show")))
                    .date(formatter.parse(req.getParameter("date")))
                    .developer(req.getParameter("developer"))
                    .publisher(req.getParameter("publisher"))
                    .genre(req.getParameter("genre"))
                    .cost(Long.valueOf(req.getParameter("cost")))
                    .name(req.getParameter("name"))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        gameService.update(game);
        resp.sendRedirect("/");
    }
}
