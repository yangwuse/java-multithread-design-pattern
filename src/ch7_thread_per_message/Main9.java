package ch7_thread_per_message;

// 7-7
public class Main9 {
    public static void main(String[] args) {
        System.out.println("BEGIN");
        Object obj = new Object();
        Blackhole.enter(obj);
        System.out.println("END");
    }
}

class Blackhole {
    public static void enter(final Object obj) {
        System.out.println("Step 1");
        magic(obj);
        System.out.println("Step 2");
        // 4. 阻塞在 obj 对象上
        synchronized (obj) {
            System.out.println("Step3 (never reached here)");
        }
    }
    public static void magic (final Object obj) {
        Thread main = Thread.currentThread();
        synchronized (main) {
            new Thread(() -> {
                // 2. 获取 obj 锁
                synchronized (obj) {
                    synchronized (main) {
                        // 3. 唤醒在 main 对象上等待的线程
                        main.notify();
                    }
                    while (true) {}
                }
            }).start();
            // 1. 在 main 对象上等待
            try {
                main.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
    }
}
