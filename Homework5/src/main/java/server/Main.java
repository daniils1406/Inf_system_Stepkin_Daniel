package server;

public class Main {
    public static void main(String[] args) {
        Server server=Server.create(4444);
        server.Message();
    }
}
