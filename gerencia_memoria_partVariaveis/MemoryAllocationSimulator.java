package gerencia_memoria_partVariaveis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Process {
    String name;
    int id;
    int size;

    Process(String name, int id, int size) {
        this.name = name;
        this.id = id;
        this.size = size;
    }
}

class MemoryBlock {
    int startAddress;
    int size;
    Process process;

    MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.process = null;
    }
}

//Alocamento best-fit
public class MemoryAllocationSimulator {
    private static int memorySize;
    private static List<MemoryBlock> memoryBlocks = new ArrayList<>();
    private static List<Process> processes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializa o simulador de alocação de memória
        initializeMemorySimulator();
    }

    private static void initializeMemorySimulator() {
        // Definir o tamanho máximo da memória física (em bytes)
        System.out.print("Digite o tamanho máximo da memória física (em bytes): ");
        memorySize = scanner.nextInt();

        // Cria o bloco de memória inicial, representando toda a memória disponível
        memoryBlocks.add(new MemoryBlock(0, memorySize));

        // Executa o loop principal do simulador
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createProcess();
                    break;
                case 2:
                    allocateProcessBestFit();
                    break;
                case 3:
                    displayMemoryState();
                    break;
                case 4:
                    compactMemory();
                    break;
                case 5:
                    removeProcessSwapping();
                    break;
                case 0:
                    System.out.println("Encerrando o simulador.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (choice != 0);
    }

    private static void displayMenu() {
        System.out.println("\n===== Menu =====");
        System.out.println("1. Criar processo");
        System.out.println("2. Alocar processo (Best-Fit)");
        System.out.println("3. Exibir estado da memória");
        System.out.println("4. Compactar memória");
        System.out.println("5. Remover processo (swapping)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void createProcess() {
        System.out.print("Digite o nome do processo: ");
        String name = scanner.next();

        int nextId = processes.size() + 1;
        System.out.println("ID gerado para o processo: " + nextId);

        System.out.print("Digite o tamanho do processo (em bytes): ");
        int size = scanner.nextInt();

        Process process = new Process(name, nextId, size);
        processes.add(process);

        System.out.println("\n\nProcesso criado com sucesso!");
    }

    //Método para realizar a alocação best-fit
    private static void allocateProcessBestFit() {
        if (processes.isEmpty()) {
            System.out.println("\n\nNão existem processos criados!");
            return;
        }

        System.out.print("Digite o ID do processo que deseja alocar: ");
        int id = scanner.nextInt();

        Process process = findProcessById(id);
        if (process == null) {
            System.out.println("\n\nProcesso não encontrado!");
            return;
        }

        MemoryBlock bestFitBlock = findBestFitBlock(process.size);
        if (bestFitBlock != null) {
            if (bestFitBlock.size == process.size) {
                bestFitBlock.process = process;
            } else {
                splitMemoryBlock(bestFitBlock, process.size);
            }
        } else {
            System.out.println("\n\nNão há espaço disponível para alocar o processo " + process.name + ".");
        }

        if(bestFitBlock != null){
            if (bestFitBlock.size == process.size) {
                bestFitBlock.process = process;
            }
            System.out.println("\n\nProcesso " + process.name + " alocado com sucesso!");
        }

    }

    private static Process findProcessById(int id) {
        for (Process process : processes) {
            if (process.id == id) {
                return process;
            }
        }
        return null;
    }

    private static MemoryBlock findBestFitBlock(int processSize) {
        MemoryBlock bestFitBlock = null;
        for (MemoryBlock block : memoryBlocks) {
            if (block.process == null && block.size >= processSize) {
                if (bestFitBlock == null || block.size < bestFitBlock.size) {
                    bestFitBlock = block;
                }
            }
        }
        return bestFitBlock;
    }

    private static void splitMemoryBlock(MemoryBlock block, int processSize) {
        MemoryBlock newBlock = new MemoryBlock(block.startAddress + processSize, block.size - processSize);
        block.size = processSize;
        block.process = null;
        memoryBlocks.add(memoryBlocks.indexOf(block) + 1, newBlock);
    }

    private static void displayMemoryState() {
        System.out.println("\n===== Estado da Memória =====");
        for (MemoryBlock block : memoryBlocks) {
            System.out.print("Endereço: " + block.startAddress + ", Tamanho: " + block.size);
            if (block.process != null) {
                System.out.print(", Processo: " + block.process.name + " (ID: " + block.process.id + ")");
            } else {
                System.out.print(", Livre");
            }
            System.out.println();
        }
        System.out.println("=============================");
    }

    private static void compactMemory() {
        // Ordenar blocos por endereço inicial
        Collections.sort(memoryBlocks, Comparator.comparingInt(block -> block.startAddress));
    
        // Criar uma lista temporária para armazenar os blocos livres
        List<MemoryBlock> freeBlocks = new ArrayList<>();
    
        // Criar uma variável para acompanhar o endereço atual após a compactação
        int currentAddress = 0;
    
        // Iterar pelos blocos de memória e reorganizar os blocos alocados e livres
        for (MemoryBlock block : memoryBlocks) {
            if (block.process == null) {
                // Se o bloco estiver livre, adicioná-lo à lista de blocos livres temporários
                freeBlocks.add(block);
            } else {
                // Se o bloco estiver alocado, atualizar seu endereço inicial
                block.startAddress = currentAddress;
                currentAddress += block.size;
            }
        }
    
        // Agora, os blocos livres serão colocados no final da memória
        for (MemoryBlock block : freeBlocks) {
            block.startAddress = currentAddress;
            currentAddress += block.size;
        }
    
        System.out.println("\n\nMemória compactada com sucesso!");
    }
    

    private static void removeProcessSwapping() {
        if (processes.isEmpty()) {
            System.out.println("\n\nNão existem processos criados!");
            return;
        }

        System.out.print("Digite o ID do processo que deseja remover (swapping): ");
        int id = scanner.nextInt();

        Process processToRemove = findProcessById(id);
        if (processToRemove == null) {
            System.out.println("\n\nProcesso não encontrado!");
            return;
        }

        MemoryBlock blockToRemove = findMemoryBlockByProcess(processToRemove);
        if (blockToRemove != null) {
            blockToRemove.process = null;
            System.out.println("\n\nProcesso removido da memória (swapping).");
        } else {
            System.out.println("\n\nO processo " + processToRemove.name + " (ID: " + processToRemove.id + ") não está alocado na memória.");
        }
    }

    private static MemoryBlock findMemoryBlockByProcess(Process process) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.process == process) {
                return block;
            }
        }
        return null;
    }
}

