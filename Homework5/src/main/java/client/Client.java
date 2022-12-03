package client;

import packet.CryptoPacket;
import packet.MetaPacket;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Client {

    Socket client;

    InputStream inputStream;

    OutputStream outputStream;

    SecretKey key;


    public static Client create(String host,Integer port, int n){

        try {
            Client client1=new Client();
            client1.client=new Socket(host,port);
            client1.inputStream=client1.client.getInputStream();
            client1.outputStream=client1.client.getOutputStream();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(n);
            client1.key=keyGenerator.generateKey();
            return client1;
        } catch (IOException | NoSuchAlgorithmException e) {
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

    public void sendKey(CryptoPacket packet) throws IOException {
        outputStream.write(packet.toByteArray());
        outputStream.flush();
    }

    public void sendMessage(MetaPacket packet) throws IOException {
        byte[] b=packet.toByteArray(packet,key);
        outputStream.write(b);
        outputStream.flush();

        if(packet.getType()!=1 && packet.getSubType()!=2){
            byte[] data = readInput(inputStream);
            MetaPacket responsePacket = MetaPacket.parse(data,key);
        }


    }
}
