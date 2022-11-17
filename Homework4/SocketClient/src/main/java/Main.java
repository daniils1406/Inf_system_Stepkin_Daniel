

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    public static void main(String[] args) throws IOException {

        SocketClient client=SocketClient.create("localhost",4444);
        UI.start(client);

    }
}
