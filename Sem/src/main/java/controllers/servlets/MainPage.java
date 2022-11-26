package controllers.servlets;

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

@WebServlet("")
public class MainPage extends HttpServlet {
    GameService gameService;
    String columnGameForOrder;
    String descOrAsc;
    String columnGameForFilter;
    String filterArgument;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gameService= (GameService) config.getServletContext().getAttribute("gameService");
        columnGameForOrder="id";
        descOrAsc="asc";
        columnGameForFilter=null;
        filterArgument=null;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        if(req.getSession().getAttribute("CurrentUser")!=null){
            User user= (User) req.getSession().getAttribute("CurrentUser");
            List<Long> PurchasedGames=gameService.purchasedGames(user.getId());
            if(!PurchasedGames.isEmpty()){
                req.setAttribute("PurchasedGames",PurchasedGames);
            }
        }
        if(req.getParameter("Filter")!=null){
            columnGameForOrder=req.getParameter("columnGameForOrder");
            descOrAsc=req.getParameter("descOrAsc");
            columnGameForFilter=req.getParameter("columnGameForFilter");
            filterArgument=req.getParameter("filterArgument");
        }
        List<Game> GameList= gameService.getListOfGames(columnGameForOrder,descOrAsc,columnGameForFilter,filterArgument);
        req.setAttribute("GameList",GameList);
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("WEB-INF/views/index.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("CurrentUser")==null){
            resp.sendRedirect("userLogIn");
        }else{
            Long num=Long.parseLong(req.getParameter("SelectedGame"));
            req.getSession().setAttribute("SelectedGame", gameService.findById(num));
            req.getSession().removeAttribute("SelectedGame");
            resp.sendRedirect("/");
        }

    }
}
