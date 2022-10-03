package servlets.content;

import servlets.ContentServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ContentServlet(resourcePath = "/WEB-INF/views/Esen.jsp" , nameOfButton = "Стихи",discription ="Насладиться поэзией Есенина",picture = "recources/pero.jpg")
@WebServlet("/esen")
public class Esenin extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/Esen.jsp");
        requestDispatcher.forward(req, resp);
    }
}
