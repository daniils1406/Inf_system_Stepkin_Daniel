package Server.test;

import lombok.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

public class SocketServer implements Runnable {

    @RequiredArgsConstructor
    private class Game extends Thread{
        int id;
        boolean wrongTurn = false;
        boolean gameFinished = false;
        char figure;
        int numberOfFigure = 0;
        char[] table;



        public char[] getTable(){
            return table;
        }
        Map<Character, ServerStreams> streams = new HashMap<>();

        public void run(){
            try {
                Game game = null;
                synchronized (serverPool) {
                    System.out.println(currentGame);
                    for (Game game1 : games) {
                        if (game1.id == currentGame) {
                            game = game1;
                        }
                    }
                }
                while (true) {
                    ServerStreams q = game.streams.get(game.figure);
                    System.out.println(q.outputStream);
                    String turnDate = String.valueOf(q.inputStream.readLine());
                    String[] turnLikeMassiv = turnDate.split("-");
                    int row;
                    int column;
                    char[] table = game.getTable();
                    row = Integer.parseInt(turnLikeMassiv[0]);
                    column = Integer.parseInt(turnLikeMassiv[1]);
                    int position = ((row - 1) * 3 + column) - 1;
                    if (table[position] == '_') {
                        table[position] = game.figure;
                        game.figure = switchFigure(game.figure);
                        game.numberOfFigure++;
                    } else {
                        game.wrongTurn = true;
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
                    String fieldString = "";
                    for (int i = 0; i < table.length; i++) {
                        fieldString = fieldString + "" + table[i];
                    }
                    if (game.gameFinished) {
                        fieldString = fieldString + switchFigure(game.figure);
                        System.out.println(fieldString);
                        Collection<Character> keySet = game.streams.keySet();
                        for (Character key : keySet) {
                            q = game.streams.get(key);
                            q.outputStream.println(fieldString);
                            q.outputStream.flush();
                        }
                        System.out.println("Game is over!");
                        table = new char[9];
                        for (int i = 0; i < table.length; i++) {
                            table[i] = '_';
                        }
                        for (Game game1 : games) {
                            if (game1.id == currentGame) {
                                games.remove(game1);
                            }
                        }
                        break;
                    }
                    if (game.wrongTurn) {
                        q = game.streams.get(switchFigure(game.figure));
                    } else {
                        q = game.streams.get(game.figure);
                    }
                    System.out.println(q.outputStream);


                    q.outputStream.println(fieldString);
                    q.outputStream.flush();

                    System.out.println("----------------------------");
                    game.wrongTurn = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static List<Game> games = new LinkedList<>();

    public static int numberOfGame = 0;


    private static class ServerStreams {
        BufferedReader inputStream;
        PrintWriter outputStream;

        public ServerStreams(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = new BufferedReader(new InputStreamReader(inputStream));
            this.outputStream = new PrintWriter(outputStream);
        }
    }


    int currentGame;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ThreadPoolExecutor serverPool;

    private InputStream inputStream = null;
    private OutputStream outputStream = null;


    private SocketServer() {
    }


    public static SocketServer currentServer = null;


    public static SocketServer create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        SocketServer server = new SocketServer();
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        currentServer = server;

        return server;
    }


    public char switchFigure(char figure) {
        if (figure == 'X') {
            figure = '0';
        } else {
            figure = 'X';
        }
        return figure;
    }


    @Override
    public void run() {
        try {
                clientSocket = serverSocket.accept();
                inputStream = clientSocket.getInputStream();
                outputStream = clientSocket.getOutputStream();
                ServerStreams p = new ServerStreams(inputStream, outputStream);

                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter output = new PrintWriter(outputStream);
                String signIn = String.valueOf(input.readLine());
                System.out.println(signIn);
                if (signIn.equals("1")) {
                    Game game = new Game();
                    game.id = numberOfGame;
                    game.figure = 'X';
                    game.streams.put('X', p);
                    game.table = new char[9];
                    for (int i = 0; i < game.table.length; i++) {
                        game.table[i] = '_';
                    }
                    games.add(game);
                    output.println("Вы создали игру с номером " + numberOfGame + " и играете за X");
                    output.flush();
                    numberOfGame++;
                } else if (signIn.equals("2")) {
                    String numOfGame = String.valueOf(input.readLine());
                    Game game = null;
                    for (Game game1 : games) {
                        if (game1.id == Integer.parseInt(numOfGame)) {
                            game = game1;
                        }
                    }
                    if (game != null) {
                        game.streams.put('0', p);
                        output.println("Вы вошли в игру и играете за 0");
                        output.flush();
                        currentGame = Integer.parseInt(numOfGame);
                        game.start();
                    } else {
                        output.write("Ошибка");
                    }
                }

                System.out.println(games.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
