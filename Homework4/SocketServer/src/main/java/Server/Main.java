package Server;

import Server.test.SocketServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        SocketServer server = SocketServer.create(4444, serverPool);


        while (true) {
            synchronized (serverPool) {
                serverPool.execute(server);

                if (serverPool.getActiveCount() >= 4) {
                    serverPool.wait();
                }
            }
        }
    }
}
