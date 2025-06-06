package IntroducaoJava.ConcorrrenciaMutithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcorrenciaMultithreading {

    public static void main(String[] args) {
        // Exemplo de criação e gerenciamento de threads
        Thread thread1 = new Thread(new MinhaRunnable(), "Thread-1");
        Thread thread2 = new MinhaThread("Thread-2");

        thread1.start();
        thread2.start();

        // Exemplo de sincronização
        Contador contador = new Contador();
        Thread thread3 = new Thread(new ContadorRunnable(contador), "Thread-3");
        Thread thread4 = new Thread(new ContadorRunnable(contador), "Thread-4");

        thread3.start();
        thread4.start();

        // Exemplo de uso de Executors e thread pools
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new MinhaRunnable());
        executorService.submit(new MinhaRunnable());

        executorService.shutdown();
    }
}

// Implementação de Runnable
class MinhaRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " está executando.");
    }
}

// Extensão da classe Thread
class MinhaThread extends Thread {

    public MinhaThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(getName() + " está executando.");
    }
}

// Classe para demonstração de sincronização
class Contador {

    private int count = 0;

    // Método sincronizado
    public synchronized void incrementar() {
        count++;
        System.out.println(Thread.currentThread().getName() + " - Contador: " + count);
    }

    // Uso de Lock para sincronização
    private final Lock lock = new ReentrantLock();

    public void incrementarComLock() {
        lock.lock();
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " - Contador com Lock: " + count);
        } finally {
            lock.unlock();
        }
    }
}

// Runnable que usa a classe Contador
class ContadorRunnable implements Runnable {

    private final Contador contador;

    public ContadorRunnable(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            contador.incrementar();
            // contador.incrementarComLock(); // Alternativamente, use este método para Lock
        }
    }
}
