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
        boolean waitOfTable;
        if(number==1){
            System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
            String turn=sc.nextLine();
//            turn=String.valueOf(number)+"-"+turn;
            client.makeTurn2(turn);
        }

        while (true){
//            System.out.println("Введите ваш ход в формате \"Строка-столбец\"");
//            turn=sc.nextLine();
//            turn=String.valueOf(number)+"-"+turn;
            boolean finish=client.makeTurn();
            if(finish){
                break;
            }
        }
    }

}