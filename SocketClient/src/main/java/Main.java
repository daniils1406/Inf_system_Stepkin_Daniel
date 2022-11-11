
import Server.test.SocketServer;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    public static void main(String[] args) throws IOException {
//        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
//        Scanner sc=new Scanner(System.in);
//        System.out.println(SocketClient.list.size());
//        SocketClient client=null;
//        System.out.println("Вы начинаете игру? \"Y\" or \"N\" ");
//        if(sc.nextLine().equals("N")){
//            System.out.println(SocketServer.currentServer);
////            client=new SocketClient("localhost",4444,SocketClient.list.get(0).reader,SocketClient.list.get(0).writer);
//            client=new SocketClient("localhost",4444, SocketServer.currentServer.getInputStream(), SocketServer.currentServer.getOutputStream());
//            SocketClient.list.add(client);
//            System.out.println("Создание второго игрока");
//        }else{
//            client=SocketClient.create("localhost",4444);
//        }
////        System.out.println(SocketClient.list.size());
//
//        System.out.println(SocketServer.currentServer);
//        UI.start(client);

        SocketClient client=SocketClient.create("localhost",4444);
        SocketClient client1=new SocketClient("localhost",4444,client.reader,client.writer);

        SocketClient.list.add(client1);

        UI.start();

//        TicTacPacket packet=TicTacPacket.create(1);
//        packet.setValue(1,(byte)1,(byte)1);
//        client.makeTurn(packet);
//
//
//
//        TicTacPacket packet1=TicTacPacket.create(2);
//        packet1.setValue(1,(byte)2,(byte)2);
//        client1.makeTurn(packet1);
//
//
//
//        TicTacPacket packet22=TicTacPacket.create(1);
//        packet22.setValue(1,(byte)2,(byte)2);
//        client.makeTurn(packet22);
//
//        TicTacPacket packet2=TicTacPacket.create(1);
//        packet2.setValue(1,(byte)1,(byte)3);
//        client.makeTurn(packet2);
//
//        TicTacPacket packet3=TicTacPacket.create(2);
//        packet3.setValue(1,(byte)3,(byte)3);
//        client1.makeTurn(packet3);
//
//        TicTacPacket packet4=TicTacPacket.create(1);
//        packet4.setValue(1,(byte)1,(byte)2);
//        client.makeTurn(packet4);
//
//        TicTacPacket packet5=TicTacPacket.create(1);
//        packet5.setValue(1,(byte)2,(byte)3);
//        client.makeTurn(packet5);

//        while (true){
//            TicTacPacket packet=TicTacPacket.create();
//        }
    }
}
