package app.support;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


@WebListener
public class Listener implements HttpSessionAttributeListener {
    static HashMap<Integer, List<String>> allSessionMessages=new HashMap<>();
    static HashMap<String,Integer> UserAndRoom=new HashMap<>();


    public static HashMap<String,Integer> getUserAndRoom(){return UserAndRoom;}

    public static HashMap<Integer, List<String>> getAllSessionMessages() {
        return allSessionMessages;
    }

    public static void add(String idUser, Integer hisRoom){
        UserAndRoom.put(idUser, hisRoom);
    }

    public static void addKey(Integer e){
        if(!allSessionMessages.containsKey(e)){

            List<String> w=new LinkedList<>();
            allSessionMessages.put(e,w);
        }
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        Collection<String> listOfUsers=  UserAndRoom.keySet();
        for(String user: listOfUsers){
            if(user.equals(event.getSession().getId()) && event.getName()!="login" && event.getName()!="NameOfUser"){
                Integer y=UserAndRoom.get(event.getSession().getId());//ПОЛУЧИЛИ АЙДИ КОМНАТЫ В КОТОРУЮ СЛЕДУЕТ ДОБАВИТЬ СООБЩЕНИЕ
                if(!event.getValue().equals("")){
                    allSessionMessages.get(y).add(event.getSession().getAttribute("NameOfUser")+": "+(String) event.getValue());//ДОБАВИЛИ СООБЩЕНИЕ В НУЖУЮ КОМНАТУ
                }

            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

}
