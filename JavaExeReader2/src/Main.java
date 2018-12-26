

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * http://www.delorie.com/djgpp/doc/exe/
 * 
 * @author leonardo
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        
        DataStream ds = new DataStream("/res/test.exe");
        Exe exe = new Exe(ds);
        System.out.println(exe);

        System.out.println("");
        System.out.println("---");
        System.out.println("original no arquivo:");
        exe.dump("1f40", 16 * 5);
        
        System.out.println("");
        System.out.println("---");
        exe.fixRelocs("d82"); // supondo que o DOS carregou no segmento CS 0D82
        // o correto seria 0d82 + header_paragraphs
        // pois esses valores nao consideram o cabecalho e
        // os valores dos segment é relativo ao inicio dos dados (sem o cabecalho)
        System.out.println("after fix (in this example, it was loaded at segment 0D82) :");
        exe.dump("1f40", 16 * 5);

        // test2.exe
        //exe.dump("0", 16 * 5);
        //exe.fixRelocs("7e0");
        //exe.dump("0", 16 * 5);
    }
    
}
