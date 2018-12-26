/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leonardo
 */
public class ExeReloc {
    
    public int offset;
    public int segment;
    
    private String toHex(int v) {
        return Integer.toHexString(v).toUpperCase();
    }

    @Override
    public String toString() {
        return "ExeReloc{" + 
                "offset=" + toHex(offset) + 
                ", segment=" + toHex(segment) + '}';
    }
    
}
