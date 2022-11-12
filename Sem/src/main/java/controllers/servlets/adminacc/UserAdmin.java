package controllers.servlets.adminacc;

import models.entities.Game;
import models.entities.Order;
import models.entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.service.game.GameService;
import models.service.order.OrderService;
import models.service.user.UserService;
import controllers.servlets.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/userAdmin")
public class UserAdmin extends HttpServlet {
    String columnUserForOrder;
    String userDescOrAsc;
    String columnUserForFilter;
    String userFilterArgument;
    String columnGameForOrder;
    String descOrAsc;
    String columnGameForFilter;
    String filterArgument;
    UserService userService;
    GameService gameService;
    OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        columnUserForOrder="id";
        userDescOrAsc="asc";
        columnUserForFilter=null;
        userFilterArgument=null;
        columnGameForOrder="id";
        descOrAsc="asc";
        columnGameForFilter=null;
        filterArgument=null;
        userService= (UserService) config.getServletContext().getAttribute("userService");
        gameService= (GameService) config.getServletContext().getAttribute("gameService");
        orderService=(OrderService) config.getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user= (User) req.getSession().getAttribute("CurrentUser");
        req.setAttribute("AvailableGames",userService.getAvailableGames(user.getId()));
        if(req.getParameter("Filter")!=null){
            columnGameForOrder=req.getParameter("columnGameForOrder");
            descOrAsc=req.getParameter("descOrAsc");
            columnGameForFilter=req.getParameter("columnGameForFilter");
            filterArgument=req.getParameter("filterArgument");
        }
        if(req.getParameter("FilterUser")!=null){
            columnUserForOrder=req.getParameter("columnGameForOrder");
            userDescOrAsc=req.getParameter("descOrAsc");
            columnUserForFilter=req.getParameter("columnGameForFilter");
            userFilterArgument=req.getParameter("filterArgument");
        }
        List<Game> GameList= gameService.getListOfGames(columnGameForOrder,descOrAsc,columnGameForFilter,filterArgument);
        req.setAttribute("GameList",GameList);

        req.setAttribute("OverviewImg", Listener.getMapOfOverviewImages().get(user.getId()));

        req.setAttribute("ListOfUsers",userService.findAllUsers(columnUserForOrder,userDescOrAsc,columnUserForFilter,userFilterArgument));


        List<Order> allOrdersOfTHisUser=orderService.getEntitiesOrdersOfThisUser(user.getId());
        Map<Order,List<Game>> purchasedGames=new HashMap<>();
        for(Order order: allOrdersOfTHisUser){
            purchasedGames.put(order,orderService.getEntitiesGamesOfCertainOrder(order));
        }
        req.setAttribute("history",purchasedGames);

        RequestDispatcher requestDispatcher=req.getRequestDispatcher("WEB-INF/views/userAdmin.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("hideGame")!=null ){
            String[] selectedGames=req.getParameterValues("games");
            if(selectedGames!=null){
                for(int i=0;i<selectedGames.length;i++){
                    gameService.showById(Long.parseLong(selectedGames[i]));
                }
            }
        }
        if(req.getParameter("showGame")!=null ){
            String[] selectedGames=req.getParameterValues("games");
            if(selectedGames!=null){
                for(int i=0;i<selectedGames.length;i++){
                    gameService.hideById(Long.parseLong(selectedGames[i]));
                }
            }
        }

        if(req.getParameter("delete")!=null || req.getParameter("signAsAdmin")!=null || req.getParameter("writeOffAsAdmin")!=null){
            String[] selectedUsers=req.getParameterValues("player");
            if(req.getParameter("delete")!=null && selectedUsers!=null){
                for(int i=0;i<selectedUsers.length;i++){
                    userService.deleteById(Long.parseLong(selectedUsers[i]));
                }
            }
            if(req.getParameter("signAsAdmin")!=null && selectedUsers!=null){
                for(int i=0;i<selectedUsers.length;i++){
                    userService.signAsAdmin(Long.parseLong(selectedUsers[i]));
                }
            }
            if(req.getParameter("writeOffAsAdmin")!=null && selectedUsers!=null){
                for(int i=0;i<selectedUsers.length;i++){
                    userService.signAsUser(Long.parseLong(selectedUsers[i]));
                }
            }
        }
        resp.sendRedirect("/userAdmin");
    }
}