package controllers.servlets;

import models.entities.Game;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import models.entities.User;

import java.util.*;

@WebListener
public class Listener implements HttpSessionAttributeListener {

    static Map<Long, Set<Game>> SetOfSelectedGames=new HashMap<>();

    static Map<Long,List<String>> mapOfOverviewImages=new HashMap<>();

    public static Map<Long,Set<Game>> getSetOfSelectedGames(){
        return SetOfSelectedGames;
    }
    public static Map<Long,List<String>> getMapOfOverviewImages(){return mapOfOverviewImages;}

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        GetSelectedGame(event);
    }

    private void GetSelectedGame(HttpSessionBindingEvent event) {
        if(event.getName()=="SelectedGame"){
            User user= (User) event.getSession().getAttribute("CurrentUser");
            if(user!=null && !SetOfSelectedGames.containsKey(user.getId())){
                SetOfSelectedGames.put(user.getId(),new HashSet<>());
            }
            SetOfSelectedGames.get(user.getId()).add((Game) event.getValue());
        }
        if(event.getName()=="OverviewImg"){
            User user=(User) event.getSession().getAttribute("CurrentUser");
            if(!mapOfOverviewImages.containsKey(user.getId())){
                mapOfOverviewImages.put(user.getId(), new LinkedList<>());
            }
            mapOfOverviewImages.get(user.getId()).add((String) event.getValue());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        GetSelectedGame(event);
    }

}


