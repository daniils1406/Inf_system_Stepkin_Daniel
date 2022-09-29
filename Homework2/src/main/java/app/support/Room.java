package app.support;

import java.util.LinkedList;
import java.util.List;

public class Room {
    Integer login;
    String password;
    List<String> messages=new LinkedList<>();

    public Room(Integer login){
        this.login=login;
    }

    public Integer getLogin(){return login;}
    public String getPassword(){return password;}

    public void setLogin(Integer login){
        this.login=login;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void add(String message){
        messages.add(message);
    }

    public void setMessages(List<String> messages){
        this.messages=messages;
    }

    public List<String> getMessages(){return messages;}

    @Override
    public String toString() {
        return "Room{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", messages=" + messages +
                '}';
    }
}
