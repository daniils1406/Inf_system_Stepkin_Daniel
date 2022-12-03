package packet.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import packet.MetaPacket;

import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class HandShackeHendler {


    public static void toByteArray(MetaPacket packet,ByteArrayOutputStream writer) {
        try{
            String message = null;
            byte id = 0;
            if (packet.getFields().size() == 0) {
                message = "Hello ";
                id = 1;
            } else if (packet.getFields().size() == 1) {
                message = "world!";
                id = 2;
            }
            writer.write(1);

            packet.getFields().add(new MetaPacket.Field(id));
            MetaPacket.Field field1 = packet.getField(id);
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

    public static MetaPacket Parse(byte[] data){
        int subType = data[3];
        MetaPacket packet = MetaPacket.create(1, subType);
        int offset = 4;
        while (true) {
            if (data.length - 2 <= offset) {
                if (subType == 1 && packet.getFields().size() == 2) {
                    if ((packet.getValue(1, String.class) + packet.getValue(2, String.class)).equals("Hello world!")) {
                        System.out.println("Общаемсся по одному пакету");
                    } else {
                        System.out.println("Не по одному пакету");
                    }
                }
                return packet;
            }

            byte fieldId = data[offset];
            byte fieldSize = data[offset + 1];

            byte[] content = new byte[Byte.toUnsignedInt(fieldSize)];
            if (fieldSize != 0) {
                System.arraycopy(data, offset + 2, content, 0, Byte.toUnsignedInt(fieldSize));
            }

            MetaPacket.Field field = new MetaPacket.Field(fieldId, fieldSize, content);
            packet.getFields().add(field);

            offset += 2 + fieldSize;
        }
    }



}
