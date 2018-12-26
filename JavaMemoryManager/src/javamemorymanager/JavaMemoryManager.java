package javamemorymanager;

/**
 * O jeito que eu consegui implementar um gerenciado de memoria.
 * Eh um jeito bem simples e precisa de uma tabela de realocacao de 10k (!)
 * para funcionar. Cada bloco usa 2 bits: 0=free 1=used_first_block e 
 * 3=used_contiguos_blcok.
 * E cada bloco representa uma alocacao de 16 bytes. Esse eh o tamanho o minimo
 * que se pode alocar a cada chamada.
 * 
 * 655360 bytes disponiveis para uso => 10240 bytes na tabela de relocacao
 * 
 * É bem devagar pois a cada alocação de memoria, o sistema percorre a tabela
 * desde o inicio para ver se encontra um bloco contiguo necessario para
 * atender ao tamanho solicitado.
 * 
 * @author leonardo
 */
public class JavaMemoryManager {
    
    public static int FREE_BLOCK = 0;
    public static int USED_FIRST_BLOCK = 1;
    public static int USED_CONTIGUOS_BLOCK = 3;
    
    private static int[] memory = new int[1024 * 64 * 10];
    private static int[] allocationTable = new int[1024 * 10];
    
    private static int getAllocationStatus(int position) {
        int index = position / 4;
        int shift = (position % 4) * 2;
        int value = (allocationTable[index] >> shift) & 3;
        return value;
    }

    private static void setAllocationStatus(int position, int value) {
        int index = position / 4;
        int shift = (position % 4) * 2;
        int mask = ~(3 << shift);
        allocationTable[index] = (allocationTable[index] & mask) | (value << shift);
    }

    public static int free(int memoryLocation) {
        int block = memoryLocation / 16;
        if ((memoryLocation % 16) != 0 || getAllocationStatus(block) != USED_FIRST_BLOCK) {
            return 0; // invalid memory size or location
        }
        int nextBlockStatus;
        do {
            setAllocationStatus(block, FREE_BLOCK);
            block++;
            nextBlockStatus = getAllocationStatus(block);
        } while (nextBlockStatus == USED_CONTIGUOS_BLOCK);
        return 1;
    }
    
    public static int malloc(int sizeInBytes) {
        if (sizeInBytes <= 0) {
            return 0; // invalid size
        }
        int requiredBlocks = (sizeInBytes / 16) + ((sizeInBytes % 16) > 0 ? 1 : 0);
        int maxBlocks = allocationTable.length * 4; // = 40960
        outer:
        for (int i = 1; i < maxBlocks; i++) { // i=1 porque 0 é reservado para indicar que nao tem mais memoria disponivel
            if (getAllocationStatus(i) == FREE_BLOCK) {
                int lastBlock = i + requiredBlocks;
                if (lastBlock > maxBlocks) {
                    return 0; // no memory available
                }
                for (int i2 = i + 1; i2 < lastBlock; i2++) {
                    if (getAllocationStatus(i2) != FREE_BLOCK) {
                        continue outer;
                    }
                }
                // free contiguos block found
                setAllocationStatus(i, USED_FIRST_BLOCK);
                for (int i2 = i + 1; i2 < lastBlock; i2++) {
                    setAllocationStatus(i2, USED_CONTIGUOS_BLOCK);
                }
                return i * 16; // free memory location
            }
        }
        return 0; // no memory available
    }
    
    public static void printAllocationTable() {
        for (int i = 0; i < 2560; i++) {
            for (int i2 = 0; i2 < 16; i2++) {
                int i3 = i * 16 + i2;
                System.out.printf("%04d=%d ", i3, getAllocationStatus(i3));
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        setAllocationStatus(0, 1);
//        setAllocationStatus(1, 2);
//        setAllocationStatus(2, 2);
//        setAllocationStatus(3, 3);
//        System.out.println(getAllocationStatus(0));
//        System.out.println(getAllocationStatus(1));
//        System.out.println(getAllocationStatus(2));
//        System.out.println(getAllocationStatus(3));
//        
//        setAllocationStatus(4, 1);
//        System.out.println(getAllocationStatus(4));
        
//        System.out.println("malloc(17)=" + malloc(17));
//        int position = malloc(64);
//        System.out.println("malloc(64)=" + position);
//        System.out.println("malloc(130)=" + malloc(130));
//        
//        printAllocationTable();
//        free(position);
//        printAllocationTable();
//        
//        position = malloc(65);
//        System.out.println("malloc(65)=" + position);
//        printAllocationTable();
//        
//        position = malloc(37);
//        System.out.println("malloc(37)=" + position);
//        printAllocationTable();
//
//        position = malloc(8);
//        System.out.println("malloc(8)=" + position);
//        printAllocationTable();
//        
//        free(position);
//        printAllocationTable();

        malloc(655360-(16 * 3));
        printAllocationTable();

        System.out.println("free memory location: " + malloc(32));
        //printAllocationTable();
    }
    
}
