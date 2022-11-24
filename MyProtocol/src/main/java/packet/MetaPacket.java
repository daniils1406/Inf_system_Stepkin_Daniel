package packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * 4-шифрования
     */

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


    public byte[] toByteArray(MetaPacket packet) {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[]{HEADER_1, HEADER_2});
            writer.write(packet.type);
            switch (packet.type) {
                case META:
                    if (packet.subType != 0) {
                        switch (packet.subType) {
                            case META_HANDSHAKE:
                                String message = null;
                                byte id = 0;
                                if (getFields().size() == 0) {
                                    message = "Hello ";
                                    id = 1;
                                } else if (getFields().size() == 1) {
                                    message = "world!";
                                    id = 2;
                                }
                                writer.write(packet.subType);

                                getFields().add(new Field(id));
                                Field field1 = getField(id);
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
                                for (Field field : fields) {
                                    writer.write(new byte[]{field.getId(), field.getSize()});
                                    writer.write(field.getContent());
                                }
                                break;
                            case META_GOODBYE:
                                writer.write(packet.subType);
                                message = "Close!";
                                setValue(1, message.getBytes());
                                field1 = getField(1);
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
                                for (Field field : fields) {
                                    writer.write(new byte[]{field.getId(), field.getSize()});
                                    writer.write(field.getContent());
                                }
                                break;
                        }
                    }
                    break;
                case JSON:
                    for (Field field : fields) {
                        writer.write(new byte[]{field.getId(), field.getSize()});
                        writer.write(field.getContent());
                    }
                    break;
                case STANDART:
                    if (packet.subType != 0) {
                        writer.write(packet.subType);
                        for (Field field : fields) {
                            writer.write(new byte[]{field.getId(), field.getSize()});
                            writer.write(field.getContent());
                        }
                    }
                    break;
            }


            writer.write(new byte[]{FOOTER_1, FOOTER_2});
            return writer.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                                String message = null;
                                byte id = 0;
                                if (getFields().size() == 0) {
                                    message = "Hello ";
                                    id = 1;
                                } else if (getFields().size() == 1) {
                                    message = "world!";
                                    id = 2;
                                }
                                writer.write(packet.subType);

                                getFields().add(new Field(id));
                                Field field1 = getField(id);
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
                                for (Field field : fields) {
                                    writer.write(new byte[]{field.getId(), field.getSize()});
                                    writer.write(field.getContent());
                                }
                                break;
                            case META_GOODBYE:
                                writer.write(packet.subType);
                                message = "Close!";
                                setValue(1, message.getBytes());
                                field1 = getField(1);
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
                                for (Field field : fields) {
                                    writer.write(new byte[]{field.getId(), field.getSize()});
                                    writer.write(field.getContent());
                                }
                                break;
                        }
                    }
                    break;
                case JSON:
                    for (Field field : fields) {
                        writer.write(new byte[]{field.getId(), field.getSize()});
                        writer.write(field.getContent());
                    }
                    break;
                case STANDART:
                    if (packet.subType != 0) {
                        writer.write(packet.subType);
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE, key);
//                        byte[] encryptedData=cipher.doFinal(data);
                        for (Field field : fields) {
//                            byte[] encrypted = cipher.doFinal(new byte[]{field.getId(), field.getSize()});
                            byte[] encrypted = cipher.doFinal(field.getContent());
                            writer.write(new byte[]{field.getId(), (byte)encrypted.length});
//                            byte[] encrypted = cipher.doFinal(field.getContent());
                            writer.write(encrypted);
                        }
                    }
                    break;
            }


            writer.write(new byte[]{FOOTER_1, FOOTER_2});
            return writer.toByteArray();

        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
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
        } else if (type == STANDART) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                byte[] data = bos.toByteArray();
                if (data.length > 255) {
                    throw new IllegalArgumentException("Too much data sent");
                }

//                Cipher cipher=Cipher.getInstance("AES");
//                cipher.init(Cipher.ENCRYPT_MODE,key);
//                byte[] encryptedData=cipher.doFinal(data);


                field.setSize((byte) data.length);
                field.setContent(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static MetaPacket parse(byte[] data, SecretKey key) {
        if (data[0] != HEADER_1 && data[1] != HEADER_2
                || data[data.length - 1] != FOOTER_2 && data[data.length - 2] != FOOTER_1) {
            throw new IllegalArgumentException("Unknown packet format");
        }


        byte type = data[2];
        MetaPacket packet = MetaPacket.create(type);
        int offset = 0;
        byte subType = 0;
        switch (type) {
            case META:
                subType = data[3];
                packet = MetaPacket.create(type, subType);
                offset = 4;
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

                    Field field = new Field(fieldId, fieldSize, content);
                    packet.getFields().add(field);

                    offset += 2 + fieldSize;
                }
            case JSON:
                offset = 3;
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
            case STANDART:
                subType = data[3];
                packet = MetaPacket.create(type, subType);
                offset = 4;
                try {
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
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
                        byte[] decryptedData;
                        decryptedData = cipher.doFinal(content);

                        Field field = new Field(fieldId, fieldSize, decryptedData);
                        packet.getFields().add(field);

                        offset += 2 + fieldSize;
                    }
                } catch (NoSuchPaddingException | IllegalBlockSizeException |
                         NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }

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
