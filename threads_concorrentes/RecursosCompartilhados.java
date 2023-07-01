package threads_concorrentes;

import java.util.concurrent.Semaphore;

public class RecursosCompartilhados {
    public static int recurso1 = 0;
    public static int recurso2 = 0;
    private static Semaphore semaforo1 = new Semaphore(1);
    private static Semaphore semaforo2 = new Semaphore(1);

    public static void acessarRecurso1() {
        try {
            semaforo1.acquire();
            System.out.println("Thread " + Thread.currentThread().getId() + " está acessando o recurso1.");
            Thread.sleep(2000);
            recurso1++;
            System.out.println("Thread " + Thread.currentThread().getId() + " liberou o recurso1.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo1.release();
        }
    }

    public static void acessarRecurso2() {
        try {
            semaforo2.acquire();
            System.out.println("Thread " + Thread.currentThread().getId() + " está acessando o recurso2.");
            Thread.sleep(2000);
            recurso2++;
            System.out.println("Thread " + Thread.currentThread().getId() + " liberou o recurso2.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo2.release();
        }
    }
}
