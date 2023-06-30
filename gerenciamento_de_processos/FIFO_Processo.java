class FIFO_Processo {
    private int id;
    private String nome;
    private boolean ioBound;
    private int tempoTotalCPU;
    private int tempoRestanteCPU;

    public FIFO_Processo(int id, String nome, boolean ioBound, int tempoTotalCPU) {
        this.id = id;
        this.nome = nome;
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
}