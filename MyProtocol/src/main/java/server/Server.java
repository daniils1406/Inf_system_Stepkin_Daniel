package server;

import packet.CryptoPacket;
import packet.MetaPacket;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private Socket clientSocket;

    private ServerSocket serverSocket;

    private InputStream inputStream;

    private OutputStream outputStream;


    public static Server create(Integer port) {
        try {
            Server server = new Server();
            server.serverSocket = new ServerSocket(port);
            server.clientSocket = server.serverSocket.accept();
            server.inputStream = server.clientSocket.getInputStream();
            server.outputStream = server.clientSocket.getOutputStream();
            return server;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Message() {
        try {
            byte[] byteKey = readInputCrypto(inputStream);
            CryptoPacket packetKey = CryptoPacket.parse(byteKey);
            byte[] key = packetKey.getKey();
            SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
            while (true) {
                byte[] data = readInput(inputStream);
                MetaPacket packet = MetaPacket.parse(data, originalKey);

                byte type = packet.getType();
                if(type==1 && packet.getSubType()==2){
                    break;
                }
                switch (type) {
                    case 1:
                        System.out.println(packet.getValue(1, String.class));
                        break;
                    case 2:
                        System.out.println(packet.getValue(1, String.class));
                        break;
                    case 3:
                        System.out.println(packet.getValue(1, String.class));
                        break;
                }
                byte[] response = packet.toByteArray(packet,originalKey);
                outputStream.write(response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            if (counter > 1 && MetaPacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }

    private byte[] readInputCrypto(InputStream stream) throws IOException {
        int b;
        byte[] buffer = new byte[10];
        int counter = 0;
        while ((b = stream.read()) > -1) {
            buffer[counter++] = (byte) b;
            if (counter >= buffer.length) {
                buffer = extendArray(buffer);
            }
            if (counter > 1 && CryptoPacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }
}
