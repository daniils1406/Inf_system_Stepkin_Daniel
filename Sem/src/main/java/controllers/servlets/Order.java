package controllers.servlets;

import models.entities.Game;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.entities.User;
import models.service.order.OrderService;
import models.service.order.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.*;

@WebServlet("/order")
public class Order extends HttpServlet {
    OrderService orderService=new OrderServiceImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {
        orderService= (OrderService) config.getServletContext().getAttribute("orderService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Long, Set<Game>> SelectedGamesOfAllUsers=Listener.getSetOfSelectedGames();
        User user= (User) req.getSession().getAttribute("CurrentUser");
            req.setAttribute("ListOfSelectedGames",SelectedGamesOfAllUsers.get(user.getId()));
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("WEB-INF/views/order.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user= (User) req.getSession().getAttribute("CurrentUser");
        Map<Long, Set<Game>> SelectedGamesOfAllUsers=Listener.getSetOfSelectedGames();
        if(req.getParameter("Delete")!=null && req.getParameter("gamesToDelete")!=null){
            String[] selectedGames=req.getParameterValues("gamesToDelete");
            orderService.deleteGamesFromBasket(user.getId(), selectedGames);
        }
        if(req.getParameter("buy")!=null){
            Long idOfOrder=orderService.signUp(user.getId());
            Collection<Game> games= SelectedGamesOfAllUsers.get(user.getId());
            for(Game game:games){
                orderService.SignUpGamesFromBasket(idOfOrder,game.getId());
                orderService.SignUpGameToLibrary(user.getId(),game.getId());
            }
            Listener.getSetOfSelectedGames().put(user.getId(),new HashSet<>());
        }
        resp.sendRedirect("/order");
    }
}