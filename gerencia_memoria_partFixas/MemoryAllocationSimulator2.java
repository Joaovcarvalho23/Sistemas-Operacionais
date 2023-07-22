package gerencia_memoria_partFixas;

import java.util.*;

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

public class MemoryAllocationSimulator2 {
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
                    allocateProcessFirstFit();
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
                    System.out.println("Encerrando o simulador de alocação de memória. Até logo!");
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
        System.out.println("2. Alocar processo (First-Fit)");
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

        System.out.println("Processo criado com sucesso!");
    }

    private static void allocateProcessFirstFit() {
        if (processes.isEmpty()) {
            System.out.println("Não existem processos criados!");
            return;
        }

        System.out.print("Digite o ID do processo que deseja alocar: ");
        int id = scanner.nextInt();

        Process process = findProcessById(id);
        if (process == null) {
            System.out.println("Processo não encontrado!");
            return;
        }

        MemoryBlock firstFitBlock = findFirstFitBlock(process.size);
        if (firstFitBlock != null) {
            if (firstFitBlock.size == process.size) {
                firstFitBlock.process = process;
            } else {
                splitMemoryBlock(firstFitBlock, process.size);
            }
            System.out.println("Processo " + process.name + " alocado com sucesso!");
        } else {
            System.out.println("Não há espaço disponível para alocar o processo " + process.name + ".");
        }

        if(firstFitBlock != null){
            if (firstFitBlock.size == process.size) {
                firstFitBlock.process = process;
            } else {
                splitMemoryBlock(firstFitBlock, process.size);
            }
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

    private static MemoryBlock findFirstFitBlock(int processSize) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.process == null && block.size >= processSize) {
                return block;
            }
        }
        return null;
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
        List<MemoryBlock> freeBlocks = new ArrayList<>();
        List<MemoryBlock> allocatedBlocks = new ArrayList<>();

        // Separar blocos livres e alocados
        for (MemoryBlock block : memoryBlocks) {
            if (block.process == null) {
                freeBlocks.add(block);
            } else {
                allocatedBlocks.add(block);
            }
        }

        // Ordenar blocos livres por endereço inicial
        Collections.sort(freeBlocks, Comparator.comparingInt(block -> block.startAddress));

        // Compactar os blocos livres
        int currentAddress = 0;
        for (MemoryBlock block : freeBlocks) {
            block.startAddress = currentAddress;
            currentAddress += block.size;
        }

        // Reorganizar os blocos alocados
        for (MemoryBlock block : allocatedBlocks) {
            block.startAddress = currentAddress;
            currentAddress += block.size;
        }

        System.out.println("Memória compactada com sucesso!");
    }

    private static void removeProcessSwapping() {
        if (processes.isEmpty()) {
            System.out.println("Não existem processos criados!");
            return;
        }

        System.out.print("Digite o ID do processo que deseja remover (swapping): ");
        int id = scanner.nextInt();

        Process processToRemove = findProcessById(id);
        if (processToRemove == null) {
            System.out.println("Processo não encontrado!");
            return;
        }

        MemoryBlock blockToRemove = findMemoryBlockByProcess(processToRemove);
        if (blockToRemove != null) {
            blockToRemove.process = null;
            System.out.println("Processo removido da memória (swapping).");
        } else {
            System.out.println("O processo " + processToRemove.name + " (ID: " + processToRemove.id + ") não está alocado na memória.");
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
