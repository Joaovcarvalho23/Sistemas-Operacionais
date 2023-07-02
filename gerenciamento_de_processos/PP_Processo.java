/*Classe que implementa um Processo com os atributos necessários para serem gerenciados pelo algoritmo Round-robin*/

public class PP_Processo {
    private int id;
    private String nome;
    private int prioridade;
    private boolean ioBound;
    private int tempoTotalCPU;
    private int tempoRestanteCPU;

    public PP_Processo(int id, String nome, int prioridade, boolean ioBound, int tempoTotalCPU) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.ioBound = ioBound;
        this.tempoTotalCPU = tempoTotalCPU;
        this.tempoRestanteCPU = tempoTotalCPU;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public boolean isIoBound() {
        return ioBound;
    }

    public int getTempoTotalCPU() {
        return tempoTotalCPU;
    }

    public int getTempoRestanteCPU() {
        return tempoRestanteCPU;
    }

    public void decrementarTempoRestanteCPU() {
        tempoRestanteCPU--;
    }

    public void decrementarPrioridade() {
        prioridade--;
    }
}
