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
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/newLink")
public class NewLink extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("Error",true);
        List<String> r= (List<String>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();
        Connection connection= PostgresConnectionToDataBase.getConnection();
        boolean allIsInsert=true;
        for(int i=0;i< r.size();i++){
            if(req.getParameter("valueForCreateLink"+i+"")!=""){
                w.add(req.getParameter("valueForCreateLink"+i+""));
            }else{
                allIsInsert=false;
                req.setAttribute("Error",allIsInsert);
            }
        }
        if(allIsInsert){
            String SQLUpdate="INSERT INTO link (id_of_employee,id_of_position) VALUES (?,?)";
            try{
                PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                statement.setInt(1,Integer.parseInt(w.get(1)));
                statement.setInt(2,Integer.parseInt(w.get(2)));
                statement.execute();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        resp.sendRedirect("/change");
    }
}
