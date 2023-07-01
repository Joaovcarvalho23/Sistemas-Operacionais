import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class RR_Gerenciador {
    private Queue<RR_Processo> filaDeProntos;
    private List<RR_Processo> processos;
    private int quantum;

    public RR_Gerenciador(int quantum) {
        this.filaDeProntos = new LinkedList<>();
        this.processos = new ArrayList<>();
        this.quantum = quantum;
    }

    public void criarProcesso(int id, String nome, int prioridade, boolean ioBound, int tempoTotalCPU) {
        RR_Processo processo = new RR_Processo (id, nome, prioridade, ioBound, tempoTotalCPU);
        processos.add(processo);
        filaDeProntos.add(processo);
    }

    public void run() {
        int lengthProcessos = processos.size();
        int[] temposRestantes = new int[lengthProcessos];

        // Inicializa os tempos restantes dos processos
        for (int i = 0; i < lengthProcessos; i++) {
            temposRestantes[i] = processos.get(i).getTempoRestanteCPU();
        }

        int indiceProcessoAtual = 0; // Índice do processo atualmente em execução

        while (!filaDeProntos.isEmpty()) {

            String t;
            for(RR_Processo p: filaDeProntos){
                if(p.isIoBound()) t = "IO Bound"; else t = "CPU Bound";
                System.out.println("Nome: " + p.getNome() + ", ID: " + p.getId() + ", Prioridade: " 
                + p.getPrioridade() + ", Tipo: " + t + ", TT CPU: " + p.getTempoTotalCPU());
            }

            RR_Processo processoAtual = filaDeProntos.poll();
            indiceProcessoAtual = (indiceProcessoAtual + 1) % lengthProcessos;

            System.out.println("\nProcesso " + processoAtual.getNome() + " está rodando.");

            int timeSlice = Math.min(quantum, temposRestantes[indiceProcessoAtual]);

            for (int i = 0; i < timeSlice; i++) {
                processoAtual.decrementarTempoRestanteCPU();
                System.out.println("Processo " + processoAtual.getNome() + " executado por 1 unidade de tempo.");
            }

            temposRestantes[indiceProcessoAtual] = processoAtual.getTempoRestanteCPU();

            if (processoAtual.getTempoRestanteCPU() > 0) {
                filaDeProntos.add(processoAtual);
                System.out.println("Processo " + processoAtual.getNome() + " está em preempção.");
            } else {
                System.out.println("Processo " + processoAtual.getNome() + " completo.");
            }
        }
    }

    public void printFilaDeProntos() {
        System.out.println("\nProcessos prontos na fila:");
        for (RR_Processo processo : filaDeProntos) {
            System.out.println(processo.getNome());
        }
        System.out.println();
    }

    public double getTempoDeEsperaMedio() {
        int totalWaitTime = 0;
        for (RR_Processo processo : processos) {
            totalWaitTime += processo.getTempoTotalCPU() - processo.getTempoRestanteCPU();
        }
        return (double) totalWaitTime / processos.size();
    }

    public void printTempoDeTurnaround() {
        System.out.println("\nTempos de turnaround: ");
        for (RR_Processo processo : processos) {
            int turnaroundTime = processo.getTempoTotalCPU();
            System.out.println("Process " + processo.getNome() + ": " + turnaroundTime);
        }
    }
}