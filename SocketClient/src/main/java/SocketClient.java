import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class SocketClient {
    Socket clientSocket;
    InputStream reader;
    OutputStream writer;
    int number=0;
    static int id=0;
    public static List<SocketClient> list=new LinkedList<>();

    public static SocketClient findClient(int id) {
        for(SocketClient client: list){
            if(client.number==id){
                return client;
            }
        }
        return null;
    }

    SocketClient(){}

    public static SocketClient create(String host, Integer port) {
        try{
            SocketClient client=new SocketClient();
            client.clientSocket=new Socket(host,port);
            client.reader=client.clientSocket.getInputStream();
            client.writer=client.clientSocket.getOutputStream();
            client.number=++id;
            list.add(client);
            return client;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public SocketClient(String host, Integer port,InputStream reader,OutputStream writer){
        try{
            this.clientSocket=new Socket(host,port);
            this.reader=reader;
            this.writer=writer;
            number=++id;
        }catch (IOException e){
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
            if (counter > 1 && TicPacket.compareEOP(buffer, counter - 1)) {
                break;
            }
        }
        byte[] data = new byte[counter];
        System.arraycopy(buffer, 0, data, 0, counter);
        return data;
    }



    public boolean makeTurn(TicTacPacket packet){
        try{
            writer.write(packet.toByteArray());
            writer.flush();
            byte[] dataTable=readInput(reader);
            TicPacket packetTable=TicPacket.parse(dataTable);
            char[] table=packetTable.getValue(1);
            for(int i=0;i<table.length;i++){
                System.out.print(table[i]);
                if(i%3==2){
                    System.out.println("");
                }
            }
            if(packetTable.getValue(2)!=null){
                System.out.println("Game is over!");
                return true;
            }
            return false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}