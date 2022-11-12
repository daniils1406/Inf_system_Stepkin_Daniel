import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
public class TicPacket {
    private static final byte HEADER_1 = (byte) 0xe1;
    private static final byte HEADER_2 = (byte) 0x17;

    private static final byte FOOTER_1 = (byte) 0x04;
    private static final byte FOOTER_2 = (byte) 0x98;

    private byte type;
    private List<TicField> fields = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class TicField {
        private byte id;
        private byte[] table=new byte[9];
        public TicField(byte id) {
            this.id = id;
        }
    }


    public char[] getValue(int id){
        TicField field;
        try{
            field=getField(id);
        }catch (IllegalArgumentException e){
            return null;
        }
        byte[] b=field.getTable();
//        System.out.println(Arrays.toString(b));
        char[] table=new char[9];
        for(int i=0;i<table.length;i++){
            table[i]=(char) b[i];
        }
        return table;
    }

    public static boolean compareEOP(byte[] arr, int lastItem) {
        return arr[lastItem - 1] == FOOTER_1 && arr [lastItem] == FOOTER_2;
    }

    public void setValue(int id,char[] table){
        TicField field;
        boolean fieldIsAlreadyExists=false;
        try {
            field = getField(id);
            fieldIsAlreadyExists=true;
        } catch (IllegalArgumentException e) {
            field = new TicField((byte) id);
        }
        if(table.length==9){
            for(int i=0;i< table.length;i++){
                field.getTable()[i]=(byte)table[i];
            }
        }else {
            throw new IllegalArgumentException("Not correct table");
        }

        if(!fieldIsAlreadyExists){
            getFields().add(field);
        }
        System.out.println(getFields().toString());
    }


    public TicField getField(int id){
        Optional<TicField> field=getFields().stream().filter((field1)->field1.id==(byte) id).findFirst();
        if(field.isEmpty()){
            throw new IllegalArgumentException("No field with that id");
        }
        return field.get();
    }

    public static TicPacket parse(byte[] data){
        if(data[0]!=HEADER_1 && data[1]!=HEADER_2
                || data[data.length-2]!=FOOTER_1 && data[data.length-1]!=FOOTER_2){
            throw new IllegalArgumentException("Unknown packet format");
        }
        byte type=data[2];
        TicPacket packet=TicPacket.create(type);
        int offset=3;
        while (true){
            if(data.length-offset<3){
                break;
            }
            TicField field=new TicField(data[offset]);
            for(int i=0;i<9;i++){
                field.getTable()[i]=data[++offset];
            }
            offset++;
            packet.getFields().add(field);
        }
        return packet;
    }


    public static TicPacket create(int type) {
        TicPacket packet = new TicPacket();
        packet.type = (byte) type;
        return packet;
    }

    public byte[] toByteArray() {
        try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
            writer.write(new byte[]{HEADER_1, HEADER_2});
            writer.write(type);

            for (TicField field : fields) {
                writer.write(field.getId());
                System.out.println(Arrays.toString(field.getTable()));
                writer.write(field.getTable());
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