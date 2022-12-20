package Protocol.packet.handlers;

import Protocol.packet.MainPacket;


public class ExitHandler {
    public static MainPacket parse(byte[] data){
        byte type = data[3];
        MainPacket packet = MainPacket.create(type);
        return packet;
    }
}
