package app.servlets;

import app.DataBaseConnection.PostgresConnectionToDataBase;
import app.entities.Employee;
import app.entities.Link;
import app.entities.Position;
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
import java.util.stream.Stream;

@WebServlet("")
public class MainPage extends HttpServlet {
    String PastSelectForEmployee="SELECT * FROM employee";
    String PastSelectForPosition="SELECT * FROM position";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        PreparedStatement statement =null;
        createTables(connection,req);

        List<Position> positions = new LinkedList<>();
        List<Employee> employees = new LinkedList<>();
        List<Link> links = new LinkedList<>();
        List<List<String>> outList = new LinkedList<>();
        List<Integer> deleteThisFromList=new LinkedList<>();
        String SQLRead;


        System.out.println(req.getParameter("column"));
        if(req.getParameter("column")!=null && req.getParameter("ascendingOrDescending")!=null && req.getParameter("column")!="" && req.getParameter("ascendingOrDescending")!=""){

            SQLRead = "SELECT * FROM employee order by "+req.getParameter("column")+" "+req.getParameter("ascendingOrDescending")+"";
            PastSelectForEmployee=SQLRead;
        }else{
            SQLRead = PastSelectForEmployee;
        }
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getInt(1))
                        .firstName(resultSet.getString(2))
                        .last_name(resultSet.getString(3))
                        .birthday(resultSet.getString(4))
                        .gender(resultSet.getString(5))
                        .education(resultSet.getString(6))
                        .build();
                employees.add(employee);
            }
        } catch (SQLException e) {
            req.setAttribute("ErrorOfFilter","Неверный ввод");
            //throw new RuntimeException(e);
        }



        if(req.getParameter("column1")!=null && req.getParameter("ascendingOrDescending1")!=null && req.getParameter("column1")!="" && req.getParameter("ascendingOrDescending1")!=""){

            SQLRead = "SELECT * FROM position order by "+req.getParameter("column1")+" "+req.getParameter("ascendingOrDescending1");
            PastSelectForPosition=SQLRead;
        }else{
            SQLRead = PastSelectForPosition;
        }
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Position position = Position.builder()
                        .id(resultSet.getInt(1))
                        .activity(resultSet.getString(2))
                        .specialization(resultSet.getString(3))
                        .build();
                positions.add(position);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        SQLRead = "SELECT * FROM link";
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Link link = Link.builder()
                        .idOfEmployee(resultSet.getInt(2))
                        .idOfPosition(resultSet.getInt(3))
                        .build();
                links.add(link);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        req.setAttribute("employees", employees);
        req.setAttribute("positions", positions);

        boolean employeeWithoutWork = true;
        for (Employee e : employees) {
            for (Position p : positions) {
                for (Link l : links) {
                    if (e.getId() == l.getIdOfEmployee() && p.getId() == l.getIdOfPosition()) {
                        employeeWithoutWork = false;
                        List<String> q = new LinkedList<>();
                        q.add(String.valueOf(e.getId()));
                        q.add(e.getFirstName());
                        q.add(e.getLast_name());
                        q.add(e.getBirthday());
                        q.add(e.getGender());
                        q.add(e.getEducation());
                        q.add(String.valueOf(p.getId()));
                        q.add(p.getActivity());
                        q.add(p.getSpecialization());
                        outList.add(q);
                    }
                }
            }
            if (employeeWithoutWork) {
                List<String> q = new LinkedList<>();
                q.add(String.valueOf(e.getId()));
                q.add(e.getFirstName());
                q.add(e.getLast_name());
                q.add(e.getBirthday());
                q.add(e.getGender());
                q.add(e.getEducation());
                outList.add(q);
            }
            employeeWithoutWork = true;
            req.setAttribute("outList", outList);
        }


        if(req.getParameter("Delete")!=null){

            for(int i=0;i< employees.size();i++){
                try {
                    String SQLDelete="DELETE FROM employee WHERE id=?";
                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("emp"+employees.get(i).getId()+"")!=null){
                        deleteThisFromList.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("emp"+employees.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer i: deleteThisFromList){
                    employees.remove(i);
            }
            deleteThisFromList=new LinkedList<>();
            for(int i=0;i< positions.size();i++){
                try {

                    String SQLDelete="DELETE FROM position WHERE id=?";

                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("pos"+positions.get(i).getId()+"")!=null){
                        deleteThisFromList.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("pos"+positions.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer i: deleteThisFromList){
                positions.remove(i);
            }
            resp.sendRedirect("/");
        }else {
            RequestDispatcher requestDispatcher=req.getRequestDispatcher("WEB-INF/views/index.jsp");
            requestDispatcher.forward(req,resp);
        }



    }





    public void createTables(Connection connection,HttpServletRequest req){
        DatabaseMetaData md = null;
        LinkedList<String> p=new LinkedList<>();
        try {
            md = connection.getMetaData();
            ResultSet resultSet = md.getColumns(null, null, "employee", null);
            while (resultSet.next()) {
                p.add(resultSet.getString("COLUMN_NAME"));
            }
            LinkedList<String> r=p;
            req.setAttribute("ColumnsOfEmployeeTable",r);
            p=new LinkedList<>();
            resultSet = md.getColumns(null, null, "position", null);
            while (resultSet.next()) {
                p.add(resultSet.getString("COLUMN_NAME"));
            }
            req.setAttribute("ColumnsOfPositionTable",p);
            req.setAttribute("ColumnsOfLinkTable", Stream.concat(r.stream(),p.stream()).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sqlCreateEmployeeTable = "CREATE TABLE IF NOT EXISTS employee" +
                "(" +
                "id bigserial primary key," +
                "first_name varchar(20)," +
                "last_name varchar(20)," +
                "birthday varchar(10)," +
                "gender varchar(1)," +
                "education varchar(25)" +
                ");";

        String sqlCreatePositionTable = "CREATE TABLE IF NOT EXISTS position" +
                "(" +
                "id bigserial primary key," +
                "activity varchar(50)," +
                "specialization varchar(50)" +
                ");";
        String sqlCreateLinkTable = "CREATE TABLE IF NOT EXISTS link" +
                "(" +
                "id bigserial primary key," +
                "id_of_employee INT," +
                "id_of_position INT" +
                ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreateEmployeeTable);
            statement.execute(sqlCreatePositionTable);
            statement.execute(sqlCreateLinkTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}