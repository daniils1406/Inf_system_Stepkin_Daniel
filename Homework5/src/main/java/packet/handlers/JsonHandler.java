package packet.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class JsonHandler {

    static ObjectMapper mapper = new ObjectMapper();


    public static void toByteArray(MetaPacket packet, ByteArrayOutputStream writer) {
        try{
            for (MetaPacket.Field field : packet.getFields()) {
                writer.write(new byte[]{field.getId(), field.getSize()});
                writer.write(field.getContent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public static void setValue(MetaPacket.Field field, Object value) {
        String json;
        try {
            json = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(json);
            byte[] data = bos.toByteArray();
            if (data.length > 255) {
                throw new IllegalArgumentException("Too much data sent");
            }
            field.setSize((byte) data.length);
            field.setContent(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MetaPacket Parse(byte[] data){
        MetaPacket packet = MetaPacket.create(2);
        int offset = 3;
        while (true) {
            if (data.length - 2 <= offset) {
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
