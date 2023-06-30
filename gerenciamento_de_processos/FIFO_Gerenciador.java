import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class FIFO_Gerenciador {
    private Queue<FIFO_Processo> filaDeProntos;
    private List<FIFO_Processo> processos;

    public FIFO_Gerenciador() {
        this.filaDeProntos = new LinkedList<>();
        this.processos = new ArrayList<>();
    }

    public void criarProcesso(int id, String name, boolean ioBound, int totalCpuTime) {
        FIFO_Processo processo = new FIFO_Processo(id, name, ioBound, totalCpuTime);
        processos.add(processo);
        filaDeProntos.add(processo);
    }

    public void run() {
        while (!filaDeProntos.isEmpty()) {

            String t;
            for(FIFO_Processo p: filaDeProntos){
                if(p.isIoBound()) t = "IO Bound"; else t = "CPU Bound";
                System.out.println("Nome: " + p.getNome() + ", ID: " + p.getId() + 
                ", Tipo: " + t + ", TT CPU: " + p.getTempoTotalCPU());
            }

            FIFO_Processo processo = filaDeProntos.poll();
            System.out.println("\nProcesso " + processo.getNome() + " está rodando.");

            int tempoRestante = processo.getTempoRestanteCPU();

            for (int i = 0; i < tempoRestante; i++) {
                processo.decrementarTempoRestanteCPU();
                System.out.println("Processo " + processo.getNome() + " executado por 1 unidade de tempo.");
            }

            if (processo.getTempoRestanteCPU() > 0) {
                filaDeProntos.add(processo);
                System.out.println("Processo " + processo.getNome() + " está em preempção.");
            } else {
                System.out.println("Processo " + processo.getNome() + " completo.");
            }
        }
    }


    public void printFilaDeProntos() {
        System.out.println("\nProcessos prontos na fila:");
        for (FIFO_Processo processo : filaDeProntos) {
            System.out.println(processo.getNome());
        }
        System.out.println();
    }

    public double getTempoDeEsperaMedio() {
        int tempoDeEsperaTotal = 0;
        for (FIFO_Processo processo : processos) {
            tempoDeEsperaTotal += processo.getTempoTotalCPU() - processo.getTempoRestanteCPU();
        }
        return (double) tempoDeEsperaTotal / processos.size();
    }

    public void printTempoDeTurnaround() {
        System.out.println("\nTempos de turnaround: ");
        for (FIFO_Processo processo : processos) {
            int turnaroundTime = processo.getTempoTotalCPU();
            System.out.println(processo.getNome() + ": " + turnaroundTime);
        }
    }
}