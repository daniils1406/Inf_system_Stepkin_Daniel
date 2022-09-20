package app.support;

import jakarta.servlet.http.HttpSession;

public class User {
    String id;
    String session;

    public User(String id, String session) {
        this.id=id;
        this.session=session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }
    public String getId(){return id;}
    public void setId(String id){this.id=id;}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", session='" + session + '\'' +
                '}';
    }
}
