package gerencia_memoria_paginacao;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int physicalMemorySizeMB = 1024; // Tamanho máximo da memória física em MB
        int virtualMemorySizeMB = 2048;  // Tamanho máximo da memória virtual em MB
        int pageSizeKB = 4;              // Tamanho das páginas em KB

        int processCount = 5;            // Quantidade de processos

        // Criação dos processos com tamanho aleatório entre 10 e 100 MB
        List<Process> processes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < processCount; i++) {
            int processSize = (random.nextInt(10) + 1) * 10;
            Process process = new Process(i, "Process_" + i, processSize);
            processes.add(process);
        }

        // Defina aqui o tipo de algoritmo de substituição de página a ser utilizado (FIFO ou LRU)
        ReplacementAlgorithmType algorithmType = ReplacementAlgorithmType.FIFO;

        // Criação do simulador de memória
        Memory memory = new Memory(
                physicalMemorySizeMB * 1024 / pageSizeKB,
                virtualMemorySizeMB * 1024 / pageSizeKB,
                pageSizeKB,
                algorithmType == ReplacementAlgorithmType.FIFO ? new FIFOAlgorithm() : new LRUAlgorithm()
        );

        // Execução da simulação
        for (Process process : processes) {
            memory.createProcess(process);
            memory.printMemoryStatus();
            System.out.println("--------------------------------------");
        }

        // Cálculo da quantidade total de "page miss" para cada algoritmo de substituição
        int pageMissFIFO = virtualMemorySizeMB - memory.getFreeMemoryPages();
        int pageMissLRU = virtualMemorySizeMB - memory.getFreeMemoryPages();

        System.out.println("Total Page Miss (FIFO): " + pageMissFIFO);
        System.out.println("Total Page Miss (LRU): " + pageMissLRU);
    }
}