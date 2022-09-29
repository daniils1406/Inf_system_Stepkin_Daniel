package app.servlets;

import app.support.Listener;
import app.support.PostgresDBConnectionDataHolder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


@WebServlet("/chat")
public class Chat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(PostgresDBConnectionDataHolder.URL, PostgresDBConnectionDataHolder.USERNAME, PostgresDBConnectionDataHolder.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Опять бред!");
            throw new RuntimeException(e);
        }
        String createTable = "create table if not exists mess" +
                "(" +
                "id bigserial primary key," +
                "id_of_room INT," +
                "message222 varchar" +
                ");";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        int e = Integer.parseInt((String) req.getSession().getAttribute("login"));

        String m=req.getParameter("MessageWindow");

        req.setAttribute("nameOfRoom", e);

        HttpSession session = req.getSession();

        Listener.addKey(e);
        Listener.add(session.getId(), e);


        if(m!=null){
            session.setAttribute("messageFromMessageWindow", m);
            session.removeAttribute("messageFromMessageWindow");
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/chat.jsp");
        requestDispatcher.forward(req, resp);
    }


    public static void recordToDataBase(Map<Integer, List<String>> allSessionMessages, Connection connection, int e, HttpServletRequest req) {
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
                        System.out.println("Ошибка в наборе данных");
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