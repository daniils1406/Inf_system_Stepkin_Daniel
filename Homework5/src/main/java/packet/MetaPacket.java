package packet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import packet.handlers.GoodByeHandler;
import packet.handlers.HandShackeHendler;
import packet.handlers.JsonHandler;
import packet.handlers.ProtectHandler;

import javax.crypto.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Data
public class MetaPacket {

    private static final byte HEADER_1 = (byte) 0xe4;
    private static final byte HEADER_2 = (byte) 0x15;

    private static final byte FOOTER_1 = (byte) 0x00;
    private static final byte FOOTER_2 = (byte) 0x90;

    byte type = 0;
    byte subType = 0;
    ObjectMapper mapper = new ObjectMapper();

    public static final int META = 1;
    public static final int META_HANDSHAKE = 1;
    public static final int META_GOODBYE = 2;


    public static final int JSON = 2;

    public static final int STANDART = 3;
    public static final int STANDART_ENCRYPTED = 1;

    List<Field> fields = new LinkedList<>();

    /**
     * 1-meta 1-handshake 2-goodbye
     * 2-jsonStandart
     * 3-standart 1-protect
     */

    @Data
    @AllArgsConstructor
    public static class Field {
        byte id;
        byte size;
        byte[] content;

        public Field(byte id) {
            this.id = id;
        }
    }


    public static boolean compareEOP(byte[] arr, int lastItem) {
        return arr[lastItem - 1] == FOOTER_1 && arr[lastItem] == FOOTER_2;
    }


    public static MetaPacket create(int type) {
        MetaPacket metaPacket = new MetaPacket();
        metaPacket.type = (byte) type;
        return metaPacket;
    }

    public static MetaPacket create(int type, int subType) {
        MetaPacket metaPacket = new MetaPacket();
        metaPacket.type = (byte) type;
        metaPacket.subType = (byte) subType;
        return metaPacket;
    }

    private MetaPacket() {
    }




    public byte[] toByteArray(MetaPacket packet, SecretKey key) {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[]{HEADER_1, HEADER_2});
            writer.write(packet.type);
            switch (packet.type) {
                case META:
                    if (packet.subType != 0) {
                        switch (packet.subType) {
                            case META_HANDSHAKE:
                                HandShackeHendler.toByteArray(this,writer);
                                break;
                            case META_GOODBYE:
                                GoodByeHandler.toByteArray(this,writer);
                                break;
                        }
                    }
                    break;
                case JSON:
                    JsonHandler.toByteArray(this,writer);
                    break;
                case STANDART:
                    ProtectHandler.toByteArray(this,writer,key);
                    break;
            }


            writer.write(new byte[]{FOOTER_1, FOOTER_2});
            return writer.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Field getField(int id) {
        Optional<Field> field = getFields().stream()
                .filter(f -> f.getId() == (byte) id)
                .findFirst();
        if (field.isEmpty()) {
            throw new IllegalArgumentException("No field with that id");
        }
        return field.get();
    }


    public void setValue(int id, Object value) {
        boolean fieldIsAlreadyExists = false;
        Field field;
        try {
            field = getField(id);
            fieldIsAlreadyExists = true;
        } catch (IllegalArgumentException e) {
            field = new Field((byte) id);
        }
        if (!fieldIsAlreadyExists) {
            getFields().add(field);
        }
        if (type == JSON) {
           JsonHandler.setValue(field,value);
        } else if (type == STANDART) {
            ProtectHandler.setValue(field,value);
        }
    }


    public static MetaPacket parse(byte[] data, SecretKey key) {
        if (data[0] != HEADER_1 && data[1] != HEADER_2
                || data[data.length - 1] != FOOTER_2 && data[data.length - 2] != FOOTER_1) {
            throw new IllegalArgumentException("Unknown packet format");
        }


        byte type = data[2];
        switch (type) {
            case META:
               return HandShackeHendler.Parse(data);
            case JSON:
                return JsonHandler.Parse(data);
            case STANDART:
                return ProtectHandler.Parse(data,key);
        }
        return null;
    }


    public <T> T getValue(int id, Class<T> clazz) {
        Field field = getField(id);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(field.getContent());
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
