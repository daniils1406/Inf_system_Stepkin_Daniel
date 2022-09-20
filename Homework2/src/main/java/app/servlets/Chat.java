package app.servlets;

import app.support.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

import static java.util.Objects.nonNull;

@WebServlet("/chat")
public class Chat extends HttpServlet {

    List<User> users=new LinkedList<>();
    static List<Room>  chats=new LinkedList<>();
    boolean o=true;

    public static List<Room> getChats() {
        return chats;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String e= (String) req.getSession().getAttribute("NameOfRoom");

        Room r=new Room(e);
        if(!chats.isEmpty()) {
            for(int i=0;i< chats.size();i++){
                if(chats.get(i).getIdOfRoom().equals(r.getIdOfRoom())){
                    o=false;
                }
            }
        }
        if (o){
            chats.add(r);
        }
        o=true;




        req.setAttribute("nameOfRoom",e);

        HttpSession session=req.getSession(true);

        //users.add(new User(e,session.getId()));
        boolean p=true;
        int p1=0;
        for(int i=0;i< users.size();i++){
            if(users.get(i).getSession().equals(session.getId())){
                p=false;
                p1=i;
            }
        }
        if(p){
            users.add(new User(e,session.getId()));
        }else{
            users.get(p1).setId(e);
        }


        String messege=req.getParameter("MessegeWindow");
        if(nonNull(messege)){
            session.setAttribute("messege",messege);
            r.add(messege);
        }


        List<String> w=new LinkedList<>();
        if(!users.isEmpty()){
            for(User s: users){
                if(s.getId().equals(e)){
                    w.add(s.getSession());
                }
            }
            req.setAttribute("CurrentUsers", w);
        }


        if(messege!=null){
            setEventListenersData(req);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/chat.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    private void setEventListenersData(HttpServletRequest request) {
        String e= (String) request.getSession().getAttribute("NameOfRoom");
        request.setAttribute("activeSessions", SessionCreatedListener.getTotalActiveSession());
        request.setAttribute("sessioHistory", SessionAttributesChangedListener.getSessionHistory(users,chats,e,request.getSession()));

    }
}