package gerencia_memoria_paginacao;


import java.util.*;

// Enumeração para os tipos de algoritmos de substituição de página
enum ReplacementAlgorithmType {
    FIFO, LRU
}

// Classe que representa uma página
class Page {
    private int id;
    private int processId;
    private int accessTime;

    public Page(int id, int processId) {
        this.id = id;
        this.processId = processId;
    }

    public int getId() {
        return id;
    }

    public int getProcessId() {
        return processId;
    }

    public int getAccessTime() {
        return accessTime;
    }

    @Override
    public String toString() {
        return "Page " + id + " (Process " + processId + ")";
    }
}

// Interface para os algoritmos de substituição de página
interface PageReplacementAlgorithm {
    Page findPageToReplace(List<Page> pages);
}

// Implementação do algoritmo FIFO (First-In-First-Out)
class FIFOAlgorithm implements PageReplacementAlgorithm {
    private int nextPageIndex;

    public FIFOAlgorithm() {
        this.nextPageIndex = 0;
    }

    @Override
    public Page findPageToReplace(List<Page> pages) {
        if (pages.isEmpty()) {
            return null;
        }
        Page pageToReplace = pages.get(nextPageIndex);
        nextPageIndex = (nextPageIndex + 1) % pages.size();
        return pageToReplace;
    }
}

// Implementação do algoritmo LRU (Least Recently Used)
class LRUAlgorithm implements PageReplacementAlgorithm {
    @Override
    public Page findPageToReplace(List<Page> pages) {
        if (pages.isEmpty()) {
            return null;
        }
        Page pageToReplace = pages.get(0);
        for (Page page : pages) {
            if (page.getAccessTime() < pageToReplace.getAccessTime()) {
                pageToReplace = page;
            }
        }
        return pageToReplace;
    }
}

// Classe que representa um processo
class Process {
    private int id;
    private String name;
    private int size;
    private List<Page> pages;

    public Process(int id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.pages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public List<Page> getPages() {
        return pages;
    }
}

// Classe que representa a memória
class Memory {
    private int physicalMemorySize;
    private int pageSize;
    private List<Page> physicalMemory;
    private List<Page> virtualMemory;
    private PageReplacementAlgorithm pageReplacementAlgorithm;

    public Memory(int physicalMemorySize, int virtualMemorySize, int pageSize,
                  PageReplacementAlgorithm pageReplacementAlgorithm) {
        this.physicalMemorySize = physicalMemorySize;
        this.pageSize = pageSize;
        this.physicalMemory = new ArrayList<>();
        this.virtualMemory = new ArrayList<>();
        this.pageReplacementAlgorithm = pageReplacementAlgorithm;
    }

    public void createProcess(Process process) {
        int requiredPages = (int) Math.ceil((double) process.getSize() / pageSize);
        if (requiredPages > getFreeMemoryPages()) {
            Page pageToReplace = pageReplacementAlgorithm.findPageToReplace(physicalMemory);
            if (pageToReplace != null) {
                physicalMemory.remove(pageToReplace);
                virtualMemory.add(pageToReplace);
            }
        }
        allocatePages(process, requiredPages);
    }

    private void allocatePages(Process process, int requiredPages) {
        for (int i = 0; i < requiredPages; i++) {
            Page page = new Page(i, process.getId());
            process.getPages().add(page);
            if (physicalMemory.size() < physicalMemorySize) {
                physicalMemory.add(page);
            } else {
                virtualMemory.add(page);
            }
        }
    }

    public int getFreeMemoryPages() {
        return physicalMemorySize - physicalMemory.size();
    }

    public void printMemoryStatus() {
        System.out.println("Physical Memory:");
        for (Page page : physicalMemory) {
            System.out.println(page);
        }
        System.out.println("\nVirtual Memory:");
        for (Page page : virtualMemory) {
            System.out.println(page);
        }
    }

    public void setPageReplacementAlgorithm(PageReplacementAlgorithm pageReplacementAlgorithm) {
        this.pageReplacementAlgorithm = pageReplacementAlgorithm;
    }
}