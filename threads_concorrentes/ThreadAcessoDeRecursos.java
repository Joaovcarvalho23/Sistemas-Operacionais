package threads_concorrentes;

public class ThreadAcessoDeRecursos extends Thread {
    public void run() {
        RecursosCompartilhados.acessarRecurso1();
        RecursosCompartilhados.acessarRecurso2();
    }
}
