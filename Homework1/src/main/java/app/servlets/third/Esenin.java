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

@ContentServlet(resourcePath = "views/Esen.jsp" , nameOfButton = "Стихи",discription ="Насладиться поэзией Есенина",picture = "recources/raskraska-pero32.jpg")
@About(nameOfEndPoint = "/esen", nameOfButton = "Стихи", discription ="Насладиться поэзией Есенина",picture = "recources/raskraska-pero32.jpg")
@WebServlet("/esen")
public class Esenin extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/Esen.jsp");
        requestDispatcher.forward(req, resp);
    }
}
