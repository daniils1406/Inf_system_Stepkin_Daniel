package app.servlets;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("")
public class StartPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("ExitButton")!=null &&(Integer)req.getSession().getAttribute("ExitButton")==1 && req.getSession().getAttribute("login")!="" && req.getSession().getAttribute("login")!=null){
            System.out.println(req.getSession().getAttribute("login"));
            resp.sendRedirect("/chat");
        }else{
            RequestDispatcher requestDispatcher=req.getRequestDispatcher("/WEB-INF/views/index.jsp");
            requestDispatcher.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().setAttribute("NameOfUser",req.getParameter("NameOfUser"));
//        RequestDispatcher requestDispatcher=req.getRequestDispatcher("/WEB-INF/views/index.jsp");
//            requestDispatcher.forward(req,resp);
        System.out.println("на кнопку нажалт");
        resp.sendRedirect("/select");

    }
}
