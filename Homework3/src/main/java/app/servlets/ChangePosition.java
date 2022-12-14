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

@WebServlet("/changeEnt")
public class ChangePosition extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("Error",true);
        req.setAttribute("EmpOrPos",req.getParameter("EmpOrPos"));
        List<String> r= (List<String>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();
        Connection connection= PostgresConnectionToDataBase.getConnection();
        boolean allIsInsert=true;
        for(int i=0;i< r.size();i++){
            if(req.getParameter("value"+i+"")!=""){
                w.add(req.getParameter("value"+i+""));
            }else{
                allIsInsert=false;
                req.getSession().setAttribute("Error",allIsInsert);
            }
        }
        if(allIsInsert){
            if(req.getAttribute("EmpOrPos").equals("Emp")){
                String SQLUpdate="UPDATE employee SET first_name=?,last_name=?, birthday=?, gender=?, education=? WHERE id=?";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.setString(3,w.get(3));
                    statement.setString(4,w.get(4));
                    statement.setString(5,w.get(5));
                    statement.setInt(6,Integer.parseInt(w.get(0)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            } else if (req.getAttribute("EmpOrPos").equals("Pos")) {
                String SQLUpdate="UPDATE position SET activity=?, specialization=? WHERE id=?";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.setInt(3,Integer.parseInt(w.get(0)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }

        }
        resp.sendRedirect("/change");
    }
}
