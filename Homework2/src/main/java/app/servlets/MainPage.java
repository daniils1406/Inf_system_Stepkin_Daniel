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
        if (req.getSession().getAttribute("ExitButton")!=null &&(Integer)req.getSession().getAttribute("ExitButton")==1 && req.getSession().getAttribute("login")!=null && req.getSession().getAttribute("login")!=""){
            resp.sendRedirect("/chat");
        }else if(req.getSession().getAttribute("NameOfUser")!="" && req.getSession().getAttribute("NameOfUser")!=null){
            RequestDispatcher requestDispatcher=req.getRequestDispatcher("/WEB-INF/views/selectChat.jsp");
            requestDispatcher.forward(req,resp);
        }else{
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login=req.getParameter("login");
        req.getSession().setAttribute("login", login);
        if(req.getParameter("beforeLeave")!=null){
            req.getSession().setAttribute("ExitButton",null);
            Connection connection= PostgresConnectionProvider.getConnection();
            Chat.recordToDataBase(Listener.getAllSessionMessages(), connection,Integer.parseInt(req.getParameter("id")));
            //req.getParameter("beforeLeave");
            Collection<String> u=Listener.getUserAndRoom().keySet();
            for(String t:u){
                if(req.getSession().getId().equals(t)){
                    Listener.getUserAndRoom().put(t,0);
                }
            }
        }

        RequestDispatcher requestDispatcher=req.getRequestDispatcher("/WEB-INF/views/selectChat.jsp");
        requestDispatcher.forward(req,resp);
    }
}
