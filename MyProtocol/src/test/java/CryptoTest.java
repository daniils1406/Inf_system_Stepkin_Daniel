//import org.testng.annotations.Test;
//import packet.CryptoPacket;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.NoSuchAlgorithmException;
//
//import static org.testng.AssertJUnit.assertEquals;
//
//public class CryptoTest {
//    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(n);
//        SecretKey key = keyGenerator.generateKey();
//        return key;
//    }
//
//    @Test
//    public void TestCryptoPacket(){
//        SecretKey key;
//        try {
//            key=generateKey(128);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        CryptoPacket packet=CryptoPacket.create(key);
//        byte[] keyPacket=packet.toByteArray();
//
//
//        CryptoPacket packet1=CryptoPacket.parse(keyPacket);
//        byte[] decodedKey = packet1.getKey();
//        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//
//        assertEquals(key,originalKey);
//    }
//}
