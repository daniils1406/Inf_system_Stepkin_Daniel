package app.servlets;


import app.model.Another;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@WebServlet("/")
public class MainPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext sc = getServletContext();

        try (InputStream is = sc.getResourceAsStream("app/recourses/i.webp")) {

            // it is the responsibility of the container to close output stream
            OutputStream os = resp.getOutputStream();

            if (is == null) {

                resp.setContentType("text/plain");
                os.write("Failed to send image".getBytes());
            } else {

                resp.setContentType("image/jpeg");

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {

                    os.write(buffer, 0, bytesRead);
                }
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(req, resp);

        }
    }
}