package Server.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class SocketServer implements Runnable {


    private static class ServerStreams {
        InputStream inputStream;
        OutputStream outputStream;

        public ServerStreams(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }
    }

    static Map<Integer, ServerStreams> streams = new HashMap<>();
    public static int id = 1;


    private boolean wrongTurn = false;

    private int lastPlayer = 0;

    private static boolean gameFinished = false;


    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ThreadPoolExecutor serverPool;

    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    int numberOfFigure = 0;

    private char figure = 'X';

    private char[] table;

    private SocketServer() {

    }


    public static SocketServer currentServer = null;

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public static SocketServer create(Integer port, ThreadPoolExecutor serverPool) throws IOException {
        SocketServer server = new SocketServer();
        server.serverSocket = new ServerSocket(port);
        server.serverPool = serverPool;
        currentServer = server;

        server.table = new char[9];
        for (int i = 0; i < server.table.length; i++) {
            server.table[i] = '_';
        }
        return server;
    }

    private byte[] extendArray(byte[] oldArray) {
        int oldSize = oldArray.length;
        byte[] newArray = new byte[oldSize * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldSize);
        return newArray;
    }


    private byte[] readInput(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) > -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && TicTacPacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }

    @Override
    public void run() {
        try {
            synchronized (serverPool) {
                clientSocket = serverSocket.accept();
//                inputStream = clientSocket.getInputStream();
//                outputStream = clientSocket.getOutputStream();
                ServerStreams p = new ServerStreams(clientSocket.getInputStream(), clientSocket.getOutputStream());
                streams.put(id, p);
                while (true) {
                    System.out.println(id);
                    ServerStreams q = streams.get(id);
                    byte[] turnDate = readInput(q.inputStream);
                    TicTacPacket turnPacket = TicTacPacket.parse(turnDate);
                    int row;
                    int column;
                    if (turnPacket.getType() != lastPlayer) {
                        String turn = turnPacket.getValue(1);
                        row = Integer.parseInt(turn) / 10;
                        column = Integer.parseInt(turn) % 10;
                        int position = ((row - 1) * 3 + column) - 1;
                        if (table[position] == '_') {
                            table[position] = figure;
                            if (figure == 'X') {
                                figure = '0';
                            } else {
                                figure = 'X';
                            }
                            numberOfFigure++;
                        } else {
                            wrongTurn = true;
                        }
                    } else {
                        wrongTurn = true;
                    }
                    //СДЕЛАТЬ ПРОВЕРКУ НА ПОБЕДУ И ОСТАНОВИТЬ ИГРУ В СЛУЧАЕ ВЫИГРЫША ОДНОЙ ИЗ СТОРОН ИЛИ ЗАПОЛНЕНИИ ВСЕЙ ТАБЛИЦЫ БЕЗ 3 В РЯД

                    for (row = 0; row < table.length / 3; row++) {
                        if (table[3 * row] == table[3 * row + 1] && table[3 * row] == table[3 * row + 2] && (table[3 * row] != '_')) {
                            gameFinished = true;
                        }
                    }
                    for (column = 0; column < table.length / 3; column++) {
                        if (table[column] == table[3 * 1 + column] && table[column] == table[3 * 2 + column] && (table[column] != '_')) {
                            gameFinished = true;
                        }
                    }
                    if (table[0] == table[4] && table[0] == table[8] && (table[0] != '_')) {
                        gameFinished = true;
                    }
                    if (table[2] == table[4] && table[2] == table[6] && (table[2] != '_')) {
                        gameFinished = true;
                    }
                    if (numberOfFigure == 9) {
                        gameFinished = true;
                    }
                    TicPacket responsePacket = TicPacket.create(1);
                    responsePacket.setValue(1, table);
                    if (gameFinished) {
                        responsePacket.setValue(2, table);
                        q.outputStream.write(responsePacket.toByteArray());
                        System.out.println("Game is over!");
                        break;
                    }
                    System.out.println(Arrays.toString(table));
                    q.outputStream.write(responsePacket.toByteArray());
                    q.outputStream.flush();

                    if (!wrongTurn) {
                        lastPlayer = turnPacket.getType();
                        if (id == 1) {
                            id = 2;
                        } else {
                            id = 1;
                        }
                    }
                    System.out.println("----------------------------");
                    wrongTurn = false;
                    serverPool.notifyAll();
                    serverPool.wait();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
