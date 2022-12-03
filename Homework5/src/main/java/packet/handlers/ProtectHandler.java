package packet.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import packet.MetaPacket;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ProtectHandler  {



    public static void toByteArray(MetaPacket packet, ByteArrayOutputStream writer,SecretKey key) throws RuntimeException {
        try{
                writer.write(1);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                for (MetaPacket.Field field : packet.getFields()) {

                    byte[] encrypted = cipher.doFinal(field.getContent());
                    writer.write(new byte[]{field.getId(), (byte)encrypted.length});
                    writer.write(encrypted);
                }
        }catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                BadPaddingException | InvalidKeyException e){
            throw new RuntimeException(e);
        }
    }





    public static void setValue(MetaPacket.Field field, Object value) {
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
    }


    public static MetaPacket Parse(byte[] data,SecretKey key){
        int subType = data[3];
        MetaPacket packet = MetaPacket.create(3, subType);
        int offset = 4;
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

                MetaPacket.Field field = new MetaPacket.Field(fieldId, fieldSize, decryptedData);
                packet.getFields().add(field);

                offset += 2 + fieldSize;
            }
        } catch (NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }



}
