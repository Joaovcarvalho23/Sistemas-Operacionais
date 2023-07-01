package threads_concorrentes;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ThreadAcessoDeRecursos thread = new ThreadAcessoDeRecursos();
            thread.start();
        }
    }
}