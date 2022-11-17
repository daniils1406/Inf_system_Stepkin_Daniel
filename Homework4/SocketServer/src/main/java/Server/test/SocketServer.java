package Server.test;

import lombok.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

public class SocketServer implements Runnable {

    @RequiredArgsConstructor
    @Data
    private class Game{
        int id;
        boolean wrongTurn=false;
        int lastPlayer=0;
        boolean gameFinished=false;
        char figure;
        int numberOfFigure=0;
        char[] table;
        Map<Character, ServerStreams> streams = new HashMap<>();
    }


    public static List<Game> games=new LinkedList<>();

    public static int numberOfGame=0;


    private static class ServerStreams {
        BufferedReader inputStream;
        PrintWriter outputStream;

        public ServerStreams(InputStream inputStream, OutputStream outputStream) {
            this.inputStream=new BufferedReader(new InputStreamReader(inputStream));
            this.outputStream=new PrintWriter(outputStream);
        }
    }

//    static Map<Character, ServerStreams> streams = new HashMap<>();


//    private boolean wrongTurn = false;

//    private int lastPlayer = 0;

//    private  boolean gameFinished = false;

    int currentGame;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ThreadPoolExecutor serverPool;

    private InputStream inputStream = null;
    private OutputStream outputStream = null;

//    int numberOfFigure = 0;

//    private char figure = 'X';

//    private char[] table;

    private SocketServer() {
    }


    public static SocketServer currentServer = null;


    public static SocketServer create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        SocketServer server = new SocketServer();
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        currentServer = server;

//        server.table = new char[9];
//        for (int i = 0; i < server.table.length; i++) {
//            server.table[i] = '_';
//        }
        return server;
    }




    //OLDRUNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN

//    @Override
//    public void run() {
//        try {
//            System.out.println("11111");
//            int activeUser=0;
//            synchronized (serverPool) {
////                clientSocket = serverSocket.accept();
////                inputStream=clientSocket.getInputStream();
////                outputStream=clientSocket.getOutputStream();
////                ServerStreams p = new ServerStreams(inputStream, outputStream);
////
////                BufferedReader input=new BufferedReader(new InputStreamReader(inputStream));
////                PrintWriter output=new PrintWriter(outputStream);
////                String signIn=String.valueOf(input.readLine());
////                int currentGame=0;
////                System.out.println(signIn);
////                if(signIn.equals("1")){
////                    Game game=new Game();
////                    game.id=numberOfGame;
////                    game.figure='X';
////                    game.streams.put('X',p);
////                    game.table=new char[9];
////                    for(int i=0;i<game.table.length;i++){
////                        game.table[i]='_';
////                    }
////                    games.add(game);
////                    output.println("Вы создали игру с номером "+numberOfGame+" и играете за X");
////                    output.flush();
////                    currentGame=numberOfGame;
////                    numberOfGame++;
////                }else if(signIn.equals("2")){
////                    String numOfGame=String.valueOf(input.readLine());
////                    Game game=null;
////                    for(Game game1 : games){
////                        if(game1.id==Integer.parseInt(numOfGame)){
////                            game=game1;
////                        }
////                    }
////                    if(game!=null){
////                        game.streams.put('0',p);
////                        output.println("Вы вошли в игру и играете за 0");
////                        output.flush();
////                        currentGame=Integer.parseInt(numOfGame);
////                    }else{
////                        output.write("Ошибка");
////                        return;
////                    }
////                }
////
////                System.out.println(games.toString());
//                while (true) {
//                    System.out.println(activeUser);
//                    int currentGame=0;
//                    if(activeUser<2){
//                        clientSocket = serverSocket.accept();
//                        activeUser++;
//                        inputStream=clientSocket.getInputStream();
//                        outputStream=clientSocket.getOutputStream();
//                        ServerStreams p = new ServerStreams(inputStream, outputStream);
//
//                        BufferedReader input=new BufferedReader(new InputStreamReader(inputStream));
//                        PrintWriter output=new PrintWriter(outputStream);
//                        String signIn=String.valueOf(input.readLine());
//                        System.out.println(signIn);
//                        if(signIn.equals("1")){
//                            Game game=new Game();
//                            game.id=numberOfGame;
//                            game.figure='X';
//                            game.streams.put('X',p);
//                            game.table=new char[9];
//                            for(int i=0;i<game.table.length;i++){
//                                game.table[i]='_';
//                            }
//                            games.add(game);
//                            output.println("Вы создали игру с номером "+numberOfGame+" и играете за X");
//                            output.flush();
//                            currentGame=numberOfGame;
//                            numberOfGame++;
//                        }else if(signIn.equals("2")){
//                            String numOfGame=String.valueOf(input.readLine());
//                            Game game=null;
//                            for(Game game1 : games){
//                                if(game1.id==Integer.parseInt(numOfGame)){
//                                    game=game1;
//                                }
//                            }
//                            if(game!=null){
//                                game.streams.put('0',p);
//                                output.println("Вы вошли в игру и играете за 0");
//                                output.flush();
//                                currentGame=Integer.parseInt(numOfGame);
//                            }else{
//                                output.write("Ошибка");
//                                return;
//                            }
//                        }
//                    }
//
//                    System.out.println(games.toString());
//
//                    Game game=null;
//                    for(Game game1: games){
//                        if(game1.id==currentGame){
//                            game=game1;
//                        }
//                    }
//                    ServerStreams q = game.streams.get(game.figure);
//                    String turnDate= String.valueOf(q.inputStream.readLine());
//                    System.out.println("Клиент отправил "+turnDate);
//                    String[] turnLikeMassiv=turnDate.split("-");
//                    int idPlayer=Integer.parseInt(turnLikeMassiv[0]);
//                    int row;
//                    int column;
////                    System.out.println(game.figure);
////                    System.out.println(idPlayer);
////                    System.out.println(game.lastPlayer);
//                    char[] table= game.getTable();
//                    if (idPlayer != game.lastPlayer) {
//                        row=Integer.parseInt(turnLikeMassiv[1]);
//                        column=Integer.parseInt(turnLikeMassiv[2]);
//                        System.out.println("Игрок: "+idPlayer+"");
//                        System.out.println("Строка: "+row+"");
//                        System.out.println("Столбец: "+column+"");
//                        int position = ((row - 1) * 3 + column) - 1;
//                        if (table[position] == '_') {
//                            table[position] = game.figure;
//                            if (game.figure == 'X') {
//                                game.figure = '0';
//                            } else {
//                                game.figure = 'X';
//                            }
//                            game.numberOfFigure++;
//                        } else {
//                            game.wrongTurn = true;
//                        }
//                    } else {
//                        game.wrongTurn = true;
//                        System.out.println("Неверный ход");
//                    }
//                    //СДЕЛАТЬ ПРОВЕРКУ НА ПОБЕДУ И ОСТАНОВИТЬ ИГРУ В СЛУЧАЕ ВЫИГРЫША ОДНОЙ ИЗ СТОРОН ИЛИ ЗАПОЛНЕНИИ ВСЕЙ ТАБЛИЦЫ БЕЗ 3 В РЯД
//
//                    for (row = 0; row < table.length / 3; row++) {
//                        if (table[3 * row] == table[3 * row + 1] && table[3 * row] == table[3 * row + 2] && (table[3 * row] != '_')) {
//                            game.gameFinished = true;
//                        }
//                    }
//                    for (column = 0; column < table.length / 3; column++) {
//                        if (table[column] == table[3 * 1 + column] && table[column] == table[3 * 2 + column] && (table[column] != '_')) {
//                            game.gameFinished = true;
//                        }
//                    }
//                    if (table[0] == table[4] && table[0] == table[8] && (table[0] != '_')) {
//                        game.gameFinished = true;
//                    }
//                    if (table[2] == table[4] && table[2] == table[6] && (table[2] != '_')) {
//                        game.gameFinished = true;
//                    }
//                    if (game.numberOfFigure == 9) {
//                        game.gameFinished = true;
//                    }
//                    String terribleString="";
//                    for(int i=0;i<table.length;i++){
//                        terribleString=terribleString+""+table[i];
//                    }
//                    System.out.println("Сервер отправляет:"+terribleString);
//                    if (game.gameFinished) {
//                        terribleString=terribleString+"f";
//                        System.out.println(terribleString);
//                        Collection<Character> keySet=game.streams.keySet();
//                        for(Character key: keySet){
//                            q=game.streams.get(key);
//                            q.outputStream.println(terribleString);
//                            q.outputStream.flush();
//                        }
//                        System.out.println("Game is over!");
//                        table=new char[9];
//                        for (int i = 0; i < table.length; i++) {
//                            table[i] = '_';
//                        }
//                        game.lastPlayer=0;
//                        game.gameFinished=false;
//                        game.numberOfFigure=0;
//                        game.wrongTurn=false;
//                        for(Game game1: games){
//                            if(game1.id==currentGame){
//                                games.remove(game1);
//                            }
//                        }
//                        serverPool.notify();
////                        games.remove(currentGame);
//                        break;
//                    }
//                    System.out.println(Arrays.toString(table));
//                    q.outputStream.println(terribleString);
//                    q.outputStream.flush();
//
//                    if (!game.wrongTurn) {
//                        game.lastPlayer = idPlayer;
//                    }
//                    System.out.println("----------------------------");
//                    game.wrongTurn = false;
////                    serverPool.notifyAll();
////                    serverPool.wait();
//                }
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }




    @Override
    public void run() {
        try {
//            int currentGame=;
            Game game=null;
            synchronized (serverPool){
                System.out.println(currentGame);
                for(Game game1: games){
                    if(game1.id==currentGame){
                        game=game1;
                    }
                }
            }
            while (true) {
                ServerStreams q = game.streams.get(game.figure);
                String turnDate= String.valueOf(q.inputStream.readLine());
                System.out.println("Клиент отправил "+turnDate);
                String[] turnLikeMassiv=turnDate.split("-");
                int idPlayer=Integer.parseInt(turnLikeMassiv[0]);
                int row;
                int column;
                char[] table= game.getTable();
                if (idPlayer != game.lastPlayer) {
                    row=Integer.parseInt(turnLikeMassiv[1]);
                    column=Integer.parseInt(turnLikeMassiv[2]);
                    System.out.println("Игрок: "+idPlayer+"");
                    System.out.println("Строка: "+row+"");
                    System.out.println("Столбец: "+column+"");
                    int position = ((row - 1) * 3 + column) - 1;
                    if (table[position] == '_') {
                        table[position] = game.figure;
                        if (game.figure == 'X') {
                            game.figure = '0';
                        } else {
                            game.figure = 'X';
                        }
                        game.numberOfFigure++;
                    } else {
                        game.wrongTurn = true;
                    }
                } else {
                    game.wrongTurn = true;
                    System.out.println("Неверный ход");
                }
                //СДЕЛАТЬ ПРОВЕРКУ НА ПОБЕДУ И ОСТАНОВИТЬ ИГРУ В СЛУЧАЕ ВЫИГРЫША ОДНОЙ ИЗ СТОРОН ИЛИ ЗАПОЛНЕНИИ ВСЕЙ ТАБЛИЦЫ БЕЗ 3 В РЯД

                for (row = 0; row < table.length / 3; row++) {
                    if (table[3 * row] == table[3 * row + 1] && table[3 * row] == table[3 * row + 2] && (table[3 * row] != '_')) {
                        game.gameFinished = true;
                    }
                }
                for (column = 0; column < table.length / 3; column++) {
                    if (table[column] == table[3 * 1 + column] && table[column] == table[3 * 2 + column] && (table[column] != '_')) {
                        game.gameFinished = true;
                    }
                }
                if (table[0] == table[4] && table[0] == table[8] && (table[0] != '_')) {
                    game.gameFinished = true;
                }
                if (table[2] == table[4] && table[2] == table[6] && (table[2] != '_')) {
                    game.gameFinished = true;
                }
                if (game.numberOfFigure == 9) {
                    game.gameFinished = true;
                }
                String terribleString="";
                for(int i=0;i<table.length;i++){
                    terribleString=terribleString+""+table[i];
                }
                System.out.println("Сервер отправляет:"+terribleString);
                if (game.gameFinished) {
                    terribleString=terribleString+"f";
                    System.out.println(terribleString);
                    Collection<Character> keySet=game.streams.keySet();
                    for(Character key: keySet){
                        q=game.streams.get(key);
                        q.outputStream.println(terribleString);
                        q.outputStream.flush();
                    }
                    System.out.println("Game is over!");
                    table=new char[9];
                    for (int i = 0; i < table.length; i++) {
                        table[i] = '_';
                    }
                    game.lastPlayer=0;
                    game.gameFinished=false;
                    game.numberOfFigure=0;
                    game.wrongTurn=false;
                    for(Game game1: games){
                        if(game1.id==currentGame){
                            games.remove(game1);
                        }
                    }
//                        games.remove(currentGame);
                    break;
                }
                System.out.println(Arrays.toString(table));
                q.outputStream.println(terribleString);
                q.outputStream.flush();

                if (!game.wrongTurn) {
                    game.lastPlayer = idPlayer;
                }
                System.out.println("----------------------------");
                game.wrongTurn = false;
//                    serverPool.notifyAll();
//                    serverPool.wait();
            }
//            notify();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void move() {
        try {
//            int currentGame=0;
                while (true) {
                        clientSocket = serverSocket.accept();
                        inputStream=clientSocket.getInputStream();
                        outputStream=clientSocket.getOutputStream();
                        ServerStreams p = new ServerStreams(inputStream, outputStream);

                        BufferedReader input=new BufferedReader(new InputStreamReader(inputStream));
                        PrintWriter output=new PrintWriter(outputStream);
                        String signIn=String.valueOf(input.readLine());
                        System.out.println(signIn);
                        if(signIn.equals("1")){
                            Game game=new Game();
                            game.id=numberOfGame;
                            game.figure='X';
                            game.streams.put('X',p);
                            game.table=new char[9];
                            for(int i=0;i<game.table.length;i++){
                                game.table[i]='_';
                            }
                            games.add(game);
                            output.println("Вы создали игру с номером "+numberOfGame+" и играете за X");
                            output.flush();
//                            currentGame=numberOfGame;
                            numberOfGame++;
                        }else if(signIn.equals("2")){
                            String numOfGame=String.valueOf(input.readLine());
                            Game game=null;
                            for(Game game1 : games){
                                if(game1.id==Integer.parseInt(numOfGame)){
                                    game=game1;
                                }
                            }
                            if(game!=null){
                                game.streams.put('0',p);
                                output.println("Вы вошли в игру и играете за 0");
                                output.flush();
                                currentGame=Integer.parseInt(numOfGame);
                                serverPool.execute(this);
//                                wait(2000);
//                                currentGame=Integer.parseInt(numOfGame);
                            }else{
                                output.write("Ошибка");
                                return;
                            }
                        }

                    System.out.println(games.toString());
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
