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
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                case 5:
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
