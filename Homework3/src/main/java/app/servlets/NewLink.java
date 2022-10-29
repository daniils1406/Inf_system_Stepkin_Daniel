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

@WebServlet("/link")
public class NewLink extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("Error",true);
        req.setAttribute("DelOrCre",req.getParameter("DelOrCre"));
        List<String> r= (List<String>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();
        Connection connection= PostgresConnectionToDataBase.getConnection();
        boolean allIsInsert=true;
        for(int i=0;i< r.size();i++){
            if(req.getParameter("value"+i+"")!=""){
                w.add(req.getParameter("value"+i+""));
            }else{
                allIsInsert=false;
                req.setAttribute("Error",allIsInsert);
            }
        }
        if(allIsInsert){
            String SQLUpdate=null;
            if(req.getAttribute("DelOrCre").equals("Cre")){
                SQLUpdate="INSERT INTO link (id_of_employee,id_of_position) VALUES (?,?)";
            } else if (req.getAttribute("DelOrCre").equals("Del")) {
                SQLUpdate="DELETE FROM link WHERE id_of_employee=? and id_of_position=?";
            }
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
