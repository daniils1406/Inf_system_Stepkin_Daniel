package app.servlets;

import app.DataBaseConnection.PostgresConnectionToDataBase;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/change")
public class ChangeTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        List<String> p = new LinkedList<>();
        req.setAttribute("CreateNewPosition", req.getParameter("CreateNewPosition"));
        req.setAttribute("CreateNewEmployee", req.getParameter("CreateNewEmployee"));
        req.setAttribute("UpdateEmployee", req.getParameter("UpdateEmployee"));
        req.setAttribute("UpdatePosition", req.getParameter("UpdatePosition"));
        req.setAttribute("CreateLink",req.getParameter("CreateLink"));
        req.setAttribute("DeleteLink",req.getParameter("DeleteLink"));

        DatabaseMetaData md = null;
        try {
            md = connection.getMetaData();
            if (req.getAttribute("CreateNewEmployee") != null || req.getAttribute("UpdateEmployee") != null) {
                ResultSet resultSet = md.getColumns(null, null, "employee", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateNewPosition") != null || req.getAttribute("UpdatePosition") != null) {
                ResultSet resultSet = md.getColumns(null, null, "position", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateLink") != null || req.getAttribute("DeleteLink") != null) {
                ResultSet resultSet = md.getColumns(null, null, "link", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            req.getSession().setAttribute("ColumnsOfTable",p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/CreateOrChange.jsp");
        requestDispatcher.forward(req, resp);
    }


}