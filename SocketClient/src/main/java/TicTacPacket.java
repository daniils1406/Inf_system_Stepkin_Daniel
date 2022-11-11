import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
public class TicTacPacket {
    private static final byte HEADER_1 = (byte) 0xe4;
    private static final byte HEADER_2 = (byte) 0x15;

    private static final byte FOOTER_1 = (byte) 0x00;
    private static final byte FOOTER_2 = (byte) 0x90;

    private byte type;
    private List<TicTacField> fields = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class TicTacField {
        private byte id;
//        private byte[] content;
        private byte row;
        private byte column;
        public TicTacField(byte id) {
            this.id = id;
        }
    }

    public static boolean compareEOP(byte[] arr, int lastItem) {
        return arr[lastItem - 1] == FOOTER_1 && arr [lastItem] == FOOTER_2;
    }

    public String getValue(int id){
        TicTacField field=getField(id);
        return ""+field.row+""+field.column;
    }


    public void setValue(int id,byte row,byte column){
        TicTacField field;
        boolean fieldIsAlreadyExists=false;
        try {
            field = getField(id);
            fieldIsAlreadyExists=true;
        } catch (IllegalArgumentException e) {
            field = new TicTacField((byte) id);
        }
        field.setRow((byte)row);
        field.setColumn((byte)column);
        if(!fieldIsAlreadyExists){
            getFields().add(field);
        }
    }


    public TicTacField getField(int id){
        Optional<TicTacField> field=getFields().stream().filter((field1)->field1.id==(byte) id).findFirst();
        if(field.isEmpty()){
            throw new IllegalArgumentException("No field with that id");
        }
        return field.get();
    }

    public static TicTacPacket parse(byte[] data){
        if(data[0]!=HEADER_1 && data[1]!=HEADER_2
                || data[data.length-2]!=FOOTER_1 && data[data.length-1]!=FOOTER_2){
            throw new IllegalArgumentException("Unknown packet format");
        }
        System.out.println(Arrays.toString(data));
        byte type=data[2];
        TicTacPacket packet=TicTacPacket.create(type);
        int offset=3;
        while (true){
            if(data.length-offset<3){
                break;
            }
            TicTacField field=new TicTacField(data[offset]);
            field.setRow(data[++offset]);
            field.setColumn(data[++offset]);
            offset++;
            packet.getFields().add(field);
        }
        return packet;
    }

    private TicTacPacket() {
    }

    public static TicTacPacket create(int type) {
        TicTacPacket packet = new TicTacPacket();
        packet.type = (byte) type;
        return packet;
    }

    public byte[] toByteArray() {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[]{HEADER_1, HEADER_2});
            writer.write(type);

            for (TicTacField field : fields) {
                writer.write(field.getId());
                writer.write(field.getRow());
                writer.write(field.getColumn());
//                writer.write(new byte[]{field.getId(), field.getSize()});
//                writer.write(field.getContent());
            }

            writer.write(new byte[]{FOOTER_1, FOOTER_2});
            return writer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}