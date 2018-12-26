
/**
 * http://www.delorie.com/djgpp/doc/exe/
 *
 * @author leonardo
 */
public class ExeHeader {

    public int signature; /* == 0x5a4D */
    public int bytes_in_last_block;
    public int blocks_in_file;
    public int num_relocs;
    public int header_paragraphs;
    public int min_extra_paragraphs;
    public int max_extra_paragraphs;
    public int ss;
    public int sp;
    public int checksum;
    public int ip;
    public int cs;
    public int reloc_table_offset;
    public int overlay_number;

    public ExeHeader(DataStream ds) {
        signature = ds.getNextShort(); /* == 0x5a4D or 23117d */
        bytes_in_last_block = ds.getNextShort();
        blocks_in_file = ds.getNextShort();
        num_relocs = ds.getNextShort();
        header_paragraphs = ds.getNextShort();
        min_extra_paragraphs = ds.getNextShort();
        max_extra_paragraphs = ds.getNextShort();
        ss = ds.getNextShort();
        sp = ds.getNextShort();
        checksum = ds.getNextShort();
        ip = ds.getNextShort();
        cs = ds.getNextShort();
        reloc_table_offset = ds.getNextShort();
        overlay_number = ds.getNextShort();
    }
    
    private String toHex(int v) {
        return Integer.toHexString(v).toUpperCase();
    }
    
    @Override
    public String toString() {
        return "ExeHeader{" + 
                "signature = " + toHex(signature) + "\n" +
                "  bytes_in_last_block = " + bytes_in_last_block + "\n" +
                "  blocks_in_file = " + blocks_in_file + "\n" +
                "  num_relocs = " + num_relocs + "\n" +
                "  header_paragraphs = " + header_paragraphs + "\n" +
                "  min_extra_paragraphs = " + min_extra_paragraphs + "\n" +
                "  max_extra_paragraphs = " + max_extra_paragraphs + "\n" +
                "  ss = " + toHex(ss) + "\n" +
                "  sp = " + toHex(sp) + "\n" +
                "  checksum = " + checksum + "\n" +
                "  ip = " + toHex(ip) + "\n" +
                "  cs = " + toHex(cs) + "\n" +
                "  reloc_table_offset = " + toHex(reloc_table_offset) + "\n" +
                "  overlay_number = " + overlay_number + '}';
    }
    
}
