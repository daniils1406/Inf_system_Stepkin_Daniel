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

        req.setAttribute("button",req.getParameter("button"));

        DatabaseMetaData md = null;
        if(req.getAttribute("button")!=null){
            System.out.println(req.getAttribute("button"));
            try {
                md = connection.getMetaData();
                if (req.getAttribute("button").equals("CreateEmployee") || req.getAttribute("button").equals("UpdateEmployee")) {
                    System.out.println("1");
                    ResultSet resultSet = md.getColumns(null, null, "employee", null);
                    while (resultSet.next()) {
                        p.add(resultSet.getString("COLUMN_NAME"));
                    }
                }
                if (req.getAttribute("button").equals("CreatePosition") || req.getAttribute("button").equals("UpdatePosition")) {
                    System.out.println("2");
                    ResultSet resultSet = md.getColumns(null, null, "position", null);
                    while (resultSet.next()) {
                        p.add(resultSet.getString("COLUMN_NAME"));
                    }
                }
                if (req.getAttribute("button").equals("CreateLink") || req.getAttribute("button").equals("DeleteLink")) {
                    System.out.println("3");
                    ResultSet resultSet = md.getColumns(null, null, "link", null);
                    while (resultSet.next()) {
                        p.add(resultSet.getString("COLUMN_NAME"));
                    }
                }
                req.getSession().setAttribute("ColumnsOfTable",p);
                System.out.println(p.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/CreateOrChange.jsp");
        requestDispatcher.forward(req, resp);
    }


}