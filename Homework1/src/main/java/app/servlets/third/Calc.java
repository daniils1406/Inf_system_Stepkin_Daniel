package app.servlets.third;

import app.entities.About;
import app.entities.ContentServlet;
import app.servlets.SessionCreatedListener;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static java.util.Objects.nonNull;


@ContentServlet(resourcePath = "views/Calculator.jsp", nameOfButton = "Калькулятор",discription = "Калькулятор для ваших подсчетов",picture = "recources/90819.png")
@About(nameOfEndPoint = "/calc", nameOfButton = "Калькулятор", discription = "Калькулятор для ваших подсчетов",picture = "recources/90819.png")
@WebServlet("/calc")
public class Calc extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/Calculator.jsp");
        requestDispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        System.out.println(name);

        HttpSession session = req.getSession(true);
        if (nonNull(name)) {
            session.setAttribute("name",name);
        }

        setEventListenersData(req);
        req.getRequestDispatcher("views/Calculator.jsp").forward(req, resp);
        //doGet(req, resp);

    }



    private void setEventListenersData(HttpServletRequest request) {
        request.setAttribute("activeSessions", SessionCreatedListener.getTotalActiveSession());
        //request.setAttribute("sessionHistory", SessionAttributesChangedListener.getSessionHistory(request.getSession()));
    }
}
