package packet;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Data
public class CryptoPacket {
    private static final byte HEADER_1 = (byte) 0x01;
    private static final byte HEADER_2 = (byte) 0x02;

    private static final byte FOOTER_1 = (byte) 0x03;
    private static final byte FOOTER_2 = (byte) 0x04;

    private SecretKey key;

    List<Field> fields = new LinkedList<>();


    @Data
    @AllArgsConstructor
    private static class Field {
        byte id;
        byte size;
        byte[] content;

        public Field(byte id) {
            this.id = id;
        }
    }



    public byte[] getKey(){
        Field field=getField(1);
        return field.content;
    }

    void setKey(SecretKey key){
        byte[] data = key.getEncoded();
        getFields().add(new Field((byte)1,(byte)data.length,data));
    }



    private CryptoPacket() {
    }

    public static boolean compareEOP(byte[] arr, int lastItem) {
        return arr[lastItem - 1] == FOOTER_1 && arr [lastItem] == FOOTER_2;
    }

    public byte[] toByteArray() {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[] {HEADER_1, HEADER_2});


            setKey(key);


            for (Field field: fields) {
                writer.write(new byte[] {field.getId(), field.getSize()});
                writer.write(field.getContent());
            }

            writer.write(new byte[] {FOOTER_1, FOOTER_2});
            return writer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static CryptoPacket parse(byte[] data) {
        if (data[0] != HEADER_1 && data[1] != HEADER_2
                || data[data.length - 1] != FOOTER_2 && data[data.length - 2] != FOOTER_1) {
            throw new IllegalArgumentException("Unknown packet format");
        }

        CryptoPacket packet = CryptoPacket.create();

        int offset = 2;
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

            Field field = new Field(fieldId, fieldSize, content);
            packet.getFields().add(field);

            offset += 2 + fieldSize;
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
        Field field;
        try {
            field = getField(id);
        } catch (IllegalArgumentException e) {
            field = new Field((byte) id);
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            byte[] data = bos.toByteArray();
            if (data.length > 255) {
                throw new IllegalArgumentException("Too much data sent");
            }
            field.setSize((byte) data.length);
            field.setContent(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getFields().add(field);
    }

    public static CryptoPacket create(){
        return new CryptoPacket();
    }

    public static CryptoPacket create(SecretKey key) {
        CryptoPacket packet = new CryptoPacket();
        packet.key = key;
        return packet;
    }

}
