public class RR_GerenciadorProcessos {
    public static void main(String[] args) {
        RR_Gerenciador gerenciador = new RR_Gerenciador(3);
        gerenciador.criarProcesso(1, "Process1", 1, true, 8);
        gerenciador.criarProcesso(2, "Process2", 2, false, 6);
        gerenciador.criarProcesso(3, "Process3", 1, false, 4);
        gerenciador.criarProcesso(4, "Process4", 2, true, 2);

        gerenciador.printFilaDeProntos();
        gerenciador.run();

        System.out.println("Average wait time: " + gerenciador.getTempoDeEsperaMedio());
        gerenciador.printTempoDeTurnaround();
    }
}