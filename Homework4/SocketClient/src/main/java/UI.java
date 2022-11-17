import java.util.Scanner;

public class UI {
    public static void start(SocketClient client){
        Scanner sc=new Scanner(System.in);
        System.out.println("1: Создать игру");
        System.out.println("2: Присоеденитсья к существующей");
        String signIn=sc.nextLine();
        int number=Integer.parseInt(signIn);
        if(number==1){
            client.sign(signIn);
        }else{
            if(client.connect(signIn)){
                return;
            };
        }
        while (true){
            System.out.println(number);
            System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
            String turn=sc.nextLine();
            String[] rowAndColumn=turn.split("-");
            turn=String.valueOf(number)+"-"+turn;
            boolean finish=client.makeTurn(turn);
            if(finish){
                break;
            }
        }
    }

}