package app.servlets;

import app.DataBaseConnection.PostgresConnectionToDataBase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/newPosition")
public class NewPosition extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("Error",true);
        List<String> r= (List<String>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();
        Connection connection= PostgresConnectionToDataBase.getConnection();
        boolean allIsInsert=true;
        for(int i=0;i< r.size();i++){
            if(req.getParameter("value1"+i+"")!=""){
                w.add(req.getParameter("value1"+i+""));
            }else{
                allIsInsert=false;
                req.getSession().setAttribute("Error",allIsInsert);
            }
        }
        if(allIsInsert){
            String SQLCreateEmployee="INSERT INTO position (activity, specialization) VALUES (?,?)";
            try {
                PreparedStatement statement=connection.prepareStatement(SQLCreateEmployee);
                statement.setString(1,w.get(1));
                statement.setString(2,w.get(2));
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(req.getParameter("idOfEmployee")!=""){
                String s=req.getParameter("idOfEmployee");
                String[] o=s.split(",");
                String SQLFindCurrentID="SELECT * FROM position WHERE ID=(SELECT MAX(ID) FROM position)";
                String SQLCreateLink="INSERT INTO link (id_of_employee,id_of_position) VALUES (?,?)";
                for(int i=0;i<o.length;i++){
                    try {
                        PreparedStatement statement=connection.prepareStatement(SQLCreateLink);
                        PreparedStatement statement1=connection.prepareStatement(SQLFindCurrentID);
                        ResultSet resultSet=statement1.executeQuery();
                        resultSet.next();
                        statement.setInt(1,Integer.parseInt(o[i]));
                        statement.setInt(2,Integer.parseInt(resultSet.getString("id")));
                        statement.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        resp.sendRedirect("/change");
    }
}
