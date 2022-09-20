package app.support;

import java.util.LinkedList;
import java.util.List;



public class Room {
    String idOfRoom;
    List<String> meseges=new LinkedList<>();

    public Room(String idOfRoom){
        this.idOfRoom=idOfRoom;
    }

    public String getIdOfRoom() {
        return idOfRoom;
    }

    public List<String> getMeseges() {
        return meseges;
    }

    public void setMeseges(List<String> meseges) {
        this.meseges = meseges;
    }

    public void add(String messege){
        meseges.add(messege);
    }
}
