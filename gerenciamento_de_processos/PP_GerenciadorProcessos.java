public class PP_GerenciadorProcessos {
    public static void main(String[] args) {
        PP_Gerenciador gerenciador = new PP_Gerenciador(3);
        gerenciador.criarProcesso(1, "Process1", 2, true, 8);
        gerenciador.criarProcesso(2, "Process2", 4, false, 6);
        gerenciador.criarProcesso(3, "Process3", 1, false, 4);
        gerenciador.criarProcesso(4, "Process4", 4, true, 2);

        gerenciador.printFilaDeProntos();
        gerenciador.run();

        System.out.println("\n Tempo médio de espera " + gerenciador.getTempoDeEsperaMedio());
        gerenciador.printTempoDeTurnaround();
    }
}
