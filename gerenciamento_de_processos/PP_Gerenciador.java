import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*Classe que implementa um Gerencisaor de Processos 
conforme o aloritmo de escalonamento Por Prioridade com Preempção*/

public class PP_Gerenciador {
    private Queue<PP_Processo> filaDeProntos;
    private List<PP_Processo> processos;

    public PP_Gerenciador(int quantum) {
        this.filaDeProntos = new LinkedList<>();
        this.processos = new ArrayList<>();
    }

    public void criarProcesso(int id, String nome, int prioridade, boolean ioBound, int tempoTotalCPU) {
        PP_Processo processo = new PP_Processo (id, nome, prioridade, ioBound, tempoTotalCPU);
        processos.add(processo);
        filaDeProntos.add(processo);
    }

    public void run() {
    while (!filaDeProntos.isEmpty()) {
        PP_Processo processoAtual = filaDeProntos.poll();
        System.out.println("Processo " + processoAtual.getNome() + " está executando.");

        int remainingTime = processoAtual.getTempoRestanteCPU();

        for (int i = 0; i < remainingTime; i++) {
            processoAtual.decrementarTempoRestanteCPU();
            System.out.println("Processo " + processoAtual.getNome() + " executado por 1 unidade de tempo.");
        }

        if (processoAtual.getTempoRestanteCPU() > 0) {
            processoAtual.decrementarPrioridade();
            filaDeProntos.add(processoAtual);
            System.out.println("Processo " + processoAtual.getNome() + " está preemptado e sua prioridade foi decrementada.");
        } else {
            System.out.println("Processo " + processoAtual.getNome() + " está completo.");
        }
    }
}

    public void printFilaDeProntos() {
        System.out.println("\nProcessos prontos na fila:");
        for (PP_Processo processo : filaDeProntos) {
            System.out.println(processo.getNome());
        }
        System.out.println();
    }

    public double getTempoDeEsperaMedio() {
        int totalWaitTime = 0;
        for (PP_Processo processo : processos) {
            totalWaitTime += processo.getTempoTotalCPU() - processo.getTempoRestanteCPU();
        }
        return (double) totalWaitTime / processos.size();
    }

    public void printTempoDeTurnaround() {
        System.out.println("\nTempos de turnaround: ");
        for (PP_Processo processo : processos) {
            int turnaroundTime = processo.getTempoTotalCPU();
            System.out.println("Processo " + processo.getNome() + ": " + turnaroundTime);
        }
    }
}
