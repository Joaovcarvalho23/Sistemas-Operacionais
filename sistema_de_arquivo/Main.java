import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o tamanho do bloco de memória: ");
        int tamanhoBloco = scanner.nextInt();

        System.out.print("Informe o tamanho da memória: ");
        int tamanhoMemoria = scanner.nextInt();

        SimuladorSistemaArquivos simulador = new SimuladorSistemaArquivos(tamanhoBloco, tamanhoMemoria);

        while (true) {
            System.out.println("\nOpções:");
            System.out.println("1. Criar arquivo");
            System.out.println("2. Excluir arquivo");
            System.out.println("3. Listar arquivos");
            System.out.println("4. Verificar fragmentação");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do diretório: ");
                    String nomeCriarDiretorio = scanner.nextLine();
                    System.out.println("Digite o nome do arquivo para o diretório: ");
                    String nomeCriarArquivo = scanner.nextLine();
                    System.out.println("Informe o tamanho do arquivo em KB: ");
                    int criarTamanhoArquivo = scanner.nextInt();

                    simulador.criarArquivo(nomeCriarDiretorio, nomeCriarArquivo, criarTamanhoArquivo);
                    break;
                case 2:
                    System.out.println("Informe o nome do diretório que contém o arquivo a ser excluído: ");
                    String nomeExcluirDiretorio = scanner.nextLine();
                    System.out.println("Digite o nome do arquivo a ser excluído: ");
                    String nomeExcluirArquivo = scanner.nextLine();

                    simulador.excluirArquivo(nomeExcluirDiretorio, nomeExcluirArquivo);
                    break;
                case 3:
                    System.out.println("Informe o diretório que possui os arquivos desejados: ");
                    String nomeListarDiretorio = scanner.nextLine();
                    
                    simulador.listarArquivos(nomeListarDiretorio);
                    break;
                case 4:
                    simulador.verificarFragmentacao();                    
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}