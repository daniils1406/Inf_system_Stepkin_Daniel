import java.util.Scanner;

public class UI {
    public static void start(SocketClient client){
        Scanner sc=new Scanner(System.in);
        while (true){
            TicTacPacket packet=TicTacPacket.create(client.number);
            System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
            String turn=sc.nextLine();
            String[] rowAndColumn=turn.split("-");
            packet.setValue(1,(byte)Integer.parseInt(rowAndColumn[0]),(byte)Integer.parseInt(rowAndColumn[1]));
            boolean finish=client.makeTurn(packet);
            if(finish){
                break;
            }
        }
    }

    public static void start(){
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("ВВедите id игорка");
            String id=sc.nextLine();
            TicTacPacket packet=TicTacPacket.create(Integer.parseInt(id));
            SocketClient client=SocketClient.findClient(Integer.parseInt(id));
            System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
            String turn= sc.nextLine();
            String[] rowAndColumn=turn.split("-");
            packet.setValue(1,(byte)Integer.parseInt(rowAndColumn[0]),(byte)Integer.parseInt(rowAndColumn[1]));
            boolean finish=client.makeTurn(packet);
            if(finish){
                break;
            }
        }
    }
}