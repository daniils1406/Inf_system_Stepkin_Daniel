package app.servlets;

import app.support.Listener;
import app.support.PostgresConnectionProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;

@WebServlet("/select")
public class MainPage extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/selectChat.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //System.out.println(req.getSession().getId());
        String login=req.getParameter("login");
        //String password=req.getParameter("password");
        req.getSession().setAttribute("login", login);
        // req.getSession().setAttribute("password", password);
        if(req.getParameter("beforeLeave")!=null){
            Connection connection= PostgresConnectionProvider.getConnection();
            Chat.recordToDataBase(Listener.getAllSessionMessages(), connection,Integer.parseInt(req.getParameter("id")),req);
            req.getParameter("beforeLeave");
            Collection<String> u=Listener.getUserAndRoom().keySet();
            for(String t:u){
                if(req.getSession().getId().equals(t)){

                    Listener.getUserAndRoom().put(t,0);
                }
            }
        }
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/selectChat.jsp");
        requestDispatcher.forward(req,resp);
    }
}
