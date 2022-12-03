package client;

import packet.CryptoPacket;
import packet.MetaPacket;

import java.io.IOException;

public class Main {

    private static class CheckClass {
        public int id;
        public String message;


        public CheckClass(int id, String message) {
            this.id = id;
            this.message = message;
        }

        @Override
        public String toString() {
            return "CheckClass{" +
                    "id=" + id +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        try {
            Client client = Client.create("localhost", 4444, 128);
            CryptoPacket keyPacket = CryptoPacket.create(client.key);

            client.sendKey(keyPacket);

            MetaPacket packet = MetaPacket.create((byte) 1, (byte) 1);
            client.sendMessage(packet);


            CheckClass checkObject = new CheckClass(1, "JSON");

            packet = MetaPacket.create((byte) 2);
            packet.setValue(1,checkObject);
            client.sendMessage(packet);




            packet=MetaPacket.create((byte)3,1);
            packet.setValue(1,"hi!");

            client.sendMessage(packet);


            packet=MetaPacket.create((byte)1,2);
            client.sendMessage(packet);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
