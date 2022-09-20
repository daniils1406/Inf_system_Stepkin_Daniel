package app.servlets.third;

import app.entities.About;
import app.entities.ContentServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ContentServlet(resourcePath = "views/Pict.jsp" , nameOfButton = "Картина", discription ="Порадовать глазки",picture = "recources/kist.png")
@About(nameOfEndPoint = "/pict", nameOfButton = "Картина", discription ="Порадовать глазки",picture = "recources/kist.png")
@WebServlet("/pict")
public class Picture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/Pict.jsp");
        requestDispatcher.forward(req, resp);
    }
}
