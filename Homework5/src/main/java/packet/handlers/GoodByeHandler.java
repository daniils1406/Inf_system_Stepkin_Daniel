package packet.handlers;

import packet.MetaPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GoodByeHandler  {



    public static void toByteArray(MetaPacket packet,ByteArrayOutputStream writer) {
        try{
            writer.write(2);
            String message = "Close!";
            packet.setValue(1, message.getBytes());
            MetaPacket.Field field1 = packet.getField(1);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(message);
                byte[] data = bos.toByteArray();
                if (data.length > 255) {
                    throw new IllegalArgumentException("Too much data sent");
                }
                field1.setSize((byte) data.length);
                field1.setContent(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (MetaPacket.Field field : packet.getFields()) {
                writer.write(new byte[]{field.getId(), field.getSize()});
                writer.write(field.getContent());
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
