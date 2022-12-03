import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SocketClient {
    Socket clientSocket;
    BufferedReader reader;
    PrintWriter writer;
    int number=0;
    static int id=0;
    public static List<SocketClient> list=new LinkedList<>();


    SocketClient(){}

    public static SocketClient create(String host, Integer port) {
        try{
            SocketClient client=new SocketClient();
            client.clientSocket=new Socket(host,port);
            client.reader=new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream()));
            client.writer=new PrintWriter(client.clientSocket.getOutputStream());
            client.number=++id;
            list.add(client);
            return client;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public boolean connect(String message){
        try{
            Scanner sc=new Scanner(System.in);
            writer.println(message);
            writer.flush();
            System.out.println("Укажите номер комнаты:");
            message=sc.nextLine();
            writer.println(message);
            writer.flush();
            String response=reader.readLine();
            System.out.println(response);
            if(response.equals("Ошибка")){
                return true;
            }
            return false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public boolean sign(String message){
        try{
            writer.println(message);
            writer.flush();
            String response=reader.readLine();
            System.out.println(response);
            if(response.equals("Ошибка")){
                return true;
            }
            return false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean makeTurn(){
        try{
            Scanner sc=new Scanner(System.in);
            String terribleString=reader.readLine();
            char[] table=new char[9];
            if(terribleString.length()==10){
                System.out.println("Победа "+terribleString.charAt(terribleString.length()-1)+"");
                System.out.println("Game is over!");
                return true;
            }
            for(int i=0;i<terribleString.length();i++){
                table[i]=terribleString.charAt(i);
            }
            for(int i=0;i<table.length;i++){
                System.out.print(table[i]);
                if(i%3==2){
                    System.out.println("");
                }
            }
            String turn;
            while (true){
                System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
                turn=sc.nextLine();
                String[] r=turn.split("-");
                int position=(Integer.parseInt(r[0])-1)*3+(Integer.parseInt(r[1]));
                if(table[position-1]!='_'){
                    System.out.println("Вы сделали неверный ход, повторите попытку");
                }else{
                    break;
                }
            }

            writer.println(turn);
            writer.flush();
            return false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    public boolean makeTurn2(String turn){
        writer.println(turn);
        writer.flush();
        return false;
    }
}