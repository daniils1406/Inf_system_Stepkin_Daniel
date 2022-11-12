package controllers.servlets.filteres;

import models.entities.Game;
import models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.game.GameService;

import java.io.IOException;

@WebFilter(urlPatterns = {"/game"})
public class GameFilter extends HttpFilter {
    GameService gameService;
    @Override
    public void init(FilterConfig config) throws ServletException {
        gameService=(GameService)config.getServletContext().getAttribute("gameService");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Game game= gameService.findById(Long.parseLong(req.getParameter("ClickGame")));
        if(game==null){
            res.sendRedirect("/");
            return;
        }
        if(req.getSession().getAttribute("CurrentUser")!=null){
            User user=(User)req.getSession().getAttribute("CurrentUser");
            if(user.isAdmin()){
                req.getSession().setAttribute("ClickGame",Long.parseLong(req.getParameter("ClickGame")));
                res.sendRedirect("/gameAdmin");
                return;
            }
        }
        chain.doFilter(req,res);
    }
}
