//package packet;
//
//import packet.MetaPacket;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.security.NoSuchAlgorithmException;
//
//public class Main {
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
//    public static void main(String[] args) {
//
//
//        //4
////        SecretKey key;
////        IvParameterSpec iv;
////        try {
////            key=generateKey(128);
////            iv=generateIv();
////        } catch (NoSuchAlgorithmException e) {
////            throw new RuntimeException(e);
////        }
////        System.out.println(key);
////        System.out.println(iv.toString());
////        CryptoPacket packet=CryptoPacket.create(key,iv);
////        byte[] keyPacket=packet.toByteArray();
////
////
////        CryptoPacket packet1=CryptoPacket.parse(keyPacket);
////        byte[] decodedKey = packet1.getKey();
////        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
////
////        IvParameterSpec ivParameterSpec=new IvParameterSpec(packet1.getIv());
////
////        System.out.println(originalKey.toString());
////        System.out.println(ivParameterSpec.toString());
//
//
//
//
//        // 1-1
//        MetaPacket packet=MetaPacket.create((byte)1,(byte)1);
//        byte[] data= packet.toByteArray(packet);
//
//        MetaPacket proverkaPacket=MetaPacket.parse(data,null);
////        System.out.println(proverkaPacket.getValue(1,String.class));
////        System.out.println(proverkaPacket.getValue(1,String.class));
//        byte[] data1=proverkaPacket.toByteArray(proverkaPacket);
//
//        MetaPacket packet1=MetaPacket.parse(data1,null);
//
//        //1-2
////        MetaPacket packetGoodBye=MetaPacket.create((byte)1,(byte)2);
////        byte[] dataGoodBye= packetGoodBye.toByteArray(packetGoodBye);
////
////        MetaPacket goodByePacket=MetaPacket.parse(dataGoodBye,null,null);
////        System.out.println(goodByePacket.getValue(1,String.class));
//
//
//        //2
////        ObjectMapper mapper=new ObjectMapper();
////        CheckClass checkObject=new CheckClass(1,"JSON");
////
////        MetaPacket packetJSON=MetaPacket.create((byte)2);
////        packetJSON.setValue(1,checkObject,null);
////        byte[] dataJson= packetJSON.toByteArray(packetJSON);
////        System.out.println(Arrays.toString(dataJson));
////
////        MetaPacket jsonPacket=MetaPacket.parse(dataJson,null);
////        CheckClass object= null;
////        System.out.println(jsonPacket.getValue(1,String.class));
////        try {
////            object = mapper.readValue(jsonPacket.getValue(1,String.class), CheckClass.class);
////        } catch (JsonProcessingException e) {
////            throw new RuntimeException(e);
////        }
////        System.out.println(object.toString());
//
//
//
//        //3-1
//
//
//
//
////        SecretKey key;
////        try {
////            key=generateKey(128);
////        } catch (NoSuchAlgorithmException e) {
////            throw new RuntimeException(e);
////        }
////        MetaPacket packet31=MetaPacket.create(3,1);
////        packet31.setValue(1,1, key);
////        byte[] data= packet31.toByteArray(packet31);
////
////        System.out.println(key);
////        CryptoPacket packet=CryptoPacket.create(key);
////        byte[] keyPacket=packet.toByteArray();
////
////
////        CryptoPacket packet1=CryptoPacket.parse(keyPacket);
////        byte[] decodedKey = packet1.getKey();
////        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
////
////
////        System.out.println(originalKey.toString());
////
////
////
////        MetaPacket packetDate=MetaPacket.parse(data,originalKey);
////        System.out.println(packetDate.getValue(1,Integer.class));
//
//    }
//}
