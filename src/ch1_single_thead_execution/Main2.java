package ch1_single_thead_execution;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main2 {
    public static void main(String[] args) {
        // 设置 3 个资源
        BoundedResource resource = new BoundedResource(3);
        // 10 个线程使用资源
        for (int i = 0; i < 10; i++) {
            new UserThread2(resource).start();
        }
    }
}

class Log {
    public static void println(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
    }
}

// 资源个数有限
class BoundedResource {
    private final Semaphore semaphore;
    private final int permits;
    private final static Random random = new Random(314159);

    // 构造 permits 个资源
    public BoundedResource(int permits) {
        this.semaphore = new Semaphore(permits);
        this.permits = permits;
    }

    // 使用资源
    public void use() throws InterruptedException {
        semaphore.acquire();
        try {
            doUse();
        } finally {
            semaphore.release();
        }
    }

    // 实际使用资源
    protected void doUse() throws InterruptedException {
        Log.println("BEGIN: used = " + (permits - semaphore.availablePermits())); 
        Thread.sleep(random.nextInt(500));
        Log.println("END: used = " + (permits - semaphore.availablePermits())); 
    }
}

// 使用资源的线程
class UserThread2 extends Thread {
    private final static Random random = new Random(26535);
    private final BoundedResource resource;

    public UserThread2(BoundedResource resource) {
        this.resource = resource;
    }

    public void run() {
        try {
            while (true ) {
                resource.use();
                Thread.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException e) {}
    }
}
