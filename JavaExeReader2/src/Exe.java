
import java.util.ArrayList;
import java.util.List;




/**
 * 1 paragraph = 16 bytes
 * 1 block = 512 bytes
 * 
 * @author leonardo
 */
public class Exe {

    public ExeHeader header;
    public List<ExeReloc> relocs = new ArrayList<ExeReloc>();

    public int[] data;
    
    public Exe(DataStream ds) {
        header = new ExeHeader(ds);
        extractRelocs(ds);
        extractData(ds);
    }
    
    private void extractRelocs(DataStream ds) {
        ds.setPosition(header.reloc_table_offset);
        for (int i = 0; i < header.num_relocs; i++) {
            ExeReloc reloc = new ExeReloc();
            reloc.offset = ds.getNextShort();
            reloc.segment = ds.getNextShort();
            relocs.add(reloc);
        }
    }
    
    private void extractData(DataStream ds) {
        int startOffset = header.header_paragraphs * 16;
        int endOffset = 512 * (header.blocks_in_file - 1) + header.bytes_in_last_block;
        int dataSize = endOffset - startOffset;
        data = new int[dataSize];
        ds.setPosition(startOffset);
        for (int i = 0; i < dataSize; i++) {
            data[i] = ds.getNextByte();
        }
    }

    public void fixRelocs(String loadedSegment) {
        int cs = Integer.parseInt(loadedSegment, 16);
        for (int i = 0; i < header.num_relocs; i++) {
            ExeReloc reloc = relocs.get(i);
            int address = reloc.segment * 16 + reloc.offset;
            int originalData = data[address] + data[address + 1] * 256;
            int correctedData = originalData + cs;
            int low = correctedData & 0xff;
            int high = (correctedData & 0xff00) >> 8;
            data[address] = low;
            data[address + 1] = high;
        }
    }
    
    public void dump(String segment, int size) {
        int cs = Integer.parseInt(segment, 16) * 16;
        int count = 0;
        for (int i = 0; i < size; i++) {
            String hexValue = "00" + Integer.toHexString(data[cs + i]); 
            hexValue = hexValue.substring(hexValue.length() - 2, hexValue.length());
            System.out.print(hexValue + " ");
            count++;
            if (count > 15) {
                count = 0;
                System.out.println();
            }
        }
    }
    
    @Override
    public String toString() {
        return "Exe{" + "header=" + header + ", relocs=" + relocs + '}';
    }
    
}
