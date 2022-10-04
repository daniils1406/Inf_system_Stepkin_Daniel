package app.servlets;

import app.support.Listener;
import app.support.PostgresConnectionProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@WebServlet("/chat")
public class Chat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection = PostgresConnectionProvider.getConnection();
        String createTable = "create table if not exists mess" +
                "(" +
                "id bigserial primary key," +
                "id_of_room INT," +
                "message222 varchar" +
                ");";

        Statement statement = null;
        try {
//            statement = connection.prepareStatement(createTable);
            statement=connection.createStatement();
            statement.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (req.getSession().getAttribute("login")==null || req.getSession().getAttribute("login")=="") {

            resp.sendRedirect("/select");
        }else{


            int e = Integer.parseInt((String) req.getSession().getAttribute("login"));

            String m = req.getParameter("MessageWindow");

            req.setAttribute("nameOfRoom", e);

            HttpSession session = req.getSession();

            Listener.addKey(e);
            Listener.add(session.getId(), e);


            if (m != null) {
                session.setAttribute("messageFromMessageWindow", m);
                session.removeAttribute("messageFromMessageWindow");
            }
            ResultSet resultSet;
            List<String> p=new LinkedList<>();
            String sql="SELECT message222 FROM mess where id_of_room= ?";
            try {
                PreparedStatement statement1= connection.prepareStatement(sql);
                statement1=connection.prepareStatement(sql);
                int q= (int) req.getAttribute("nameOfRoom");
                statement1.setInt(1,q);
                resultSet=statement1.executeQuery();
                while (resultSet.next()){
                    p.add(resultSet.getString("message222"));
                }
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            req.setAttribute("MessagesFromDB",p);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/chat.jsp");
            requestDispatcher.forward(req, resp);
        }
    }


    public static void recordToDataBase(Map<Integer, List<String>> allSessionMessages, Connection connection, int e) {
        Collection<Integer> rooms =  allSessionMessages.keySet();//получил id всех комнат
        for (Integer num : rooms) {
            if(num==e && num!=0){
                List<String> messegesFromSomeRoom = allSessionMessages.get(num);
                PreparedStatement statement = null;
                String sqlInsert = "INSERT INTO mess(id_of_room, message222) VALUES(?,?)";
                for (String message : messegesFromSomeRoom) {
                    try {
                        statement = connection.prepareStatement(sqlInsert);
                        statement.setInt(1, num);
                        statement.setString(2, message);
                        statement.execute();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);

                    }
                }
            }
        }
        for(Integer num: rooms){
            if(num==e && num!=0){
                allSessionMessages.put(num, new LinkedList<>());
            }
        }
    }
}