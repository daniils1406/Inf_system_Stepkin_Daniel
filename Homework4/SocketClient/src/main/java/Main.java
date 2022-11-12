
import Server.test.SocketServer;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc=new Scanner(System.in);
        SocketClient client=SocketClient.create("localhost",4444);
        System.out.println("Вы первый игрок? \"Y/N\"");
        if(sc.nextLine().equals("Y")){
            UI.start(client,1);
        }else{
            UI.start(client,2);
        }


//        SocketClient client=SocketClient.create("localhost",4444);
//        SocketClient client1=SocketClient.create("localhost",4444);
//
//        UI.start();

    }
}
