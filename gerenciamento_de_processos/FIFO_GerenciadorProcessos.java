public class FIFO_GerenciadorProcessos {
    public static void main(String[] args) {
        FIFO_Gerenciador gerenciador = new FIFO_Gerenciador();
        gerenciador.criarProcesso(1, "Processo1", true, 8);
        gerenciador.criarProcesso(2, "Processo2", false, 6);
        gerenciador.criarProcesso(3, "Processo3", false, 4);
        gerenciador.criarProcesso(4, "Processo4", true, 2);

        gerenciador.printFilaDeProntos();
        gerenciador.run();

        System.out.println("\nTempo médio de espera: " + gerenciador.getTempoDeEsperaMedio());
        gerenciador.printTempoDeTurnaround();
    }
}