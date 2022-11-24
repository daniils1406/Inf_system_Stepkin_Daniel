//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.testng.annotations.Test;
//import packet.CryptoPacket;
//import packet.MetaPacket;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.NoSuchAlgorithmException;
//
//import static org.testng.AssertJUnit.assertEquals;
//
//
//public class PacketTest {
//
//    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(n);
//        SecretKey key = keyGenerator.generateKey();
//        return key;
//    }
//
//    private static class CheckClass{
//        public int id;
//        public String message;
//
//        public CheckClass(){};
//
//        public CheckClass(int id,String message){
//            this.id=id;
//            this.message=message;
//        }
//
//        @Override
//        public String toString() {
//            return "CheckClass{" +
//                    "id=" + id +
//                    ", message='" + message + '\'' +
//                    '}';
//        }
//    }
//
//
//
//    @Test
//    public void handShakeTest(){
//        MetaPacket packet=MetaPacket.create((byte)1,(byte)1);
//        byte[] data= packet.toByteArray(packet);
//
//        MetaPacket proverkaPacket=MetaPacket.parse(data,null);
//        System.out.println(proverkaPacket.getValue(1,String.class));
//        assertEquals(proverkaPacket.getValue(1,String.class),"Check!");
//    }
//
//
//    @Test
//    public void goodByeTest(){
//        MetaPacket packetGoodBye=MetaPacket.create((byte)1,(byte)2);
//        byte[] dataGoodBye= packetGoodBye.toByteArray(packetGoodBye);
//
//        MetaPacket goodByePacket=MetaPacket.parse(dataGoodBye,null);
//        System.out.println(goodByePacket.getValue(1,String.class));
//        assertEquals(goodByePacket.getValue(1,String.class),"Close!");
//    }
//
//
//    @Test
//    public void jsonTest(){
//        ObjectMapper mapper=new ObjectMapper();
//        CheckClass checkObject=new CheckClass(1,"JSON");
//
//        MetaPacket packetJSON=MetaPacket.create((byte)2);
//        packetJSON.setValue(1,checkObject);
//        byte[] dataJson= packetJSON.toByteArray(packetJSON);
//
//        MetaPacket jsonPacket=MetaPacket.parse(dataJson,null);
//        CheckClass object= null;
//        try {
//            object = mapper.readValue(jsonPacket.getValue(1,String.class), CheckClass.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(object.toString());
////        assertEquals(object,checkObject);
//        assertEquals(jsonPacket.getValue(1,String.class),"{\"id\":1,\"message\":\"JSON\"}");
//    }
//
//
//    @Test
//    public void standartTest(){
//        SecretKey key;
//        try {
//            key=generateKey(128);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        MetaPacket packet31=MetaPacket.create(3,1);
//        int value=1;
//        packet31.setValue(1,value);
//        byte[] data= packet31.toByteArray(packet31);
//
//        CryptoPacket packet=CryptoPacket.create(key);
//        byte[] keyPacket=packet.toByteArray();
//
//
//        CryptoPacket packet1=CryptoPacket.parse(keyPacket);
//        byte[] decodedKey = packet1.getKey();
//        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//
//
//
//
//
//        MetaPacket packetDate=MetaPacket.parse(data,originalKey);
//        int r=packetDate.getValue(1, Integer.class);
//        assertEquals(r,1);
//    }
//
//}
