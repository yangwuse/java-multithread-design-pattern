package ch5_producer_consumer;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);
        Thread[] threads = {
            new MakerThread("MakerThread-2", table, 92653),
            new MakerThread("MakerThread-3", table, 58979),
            new MakerThread("MakerThread-1", table, 31415),
            new EaterThread("EaterThread-1", table, 32384),
            new EaterThread("EaterThread-2", table, 62643),
            new EaterThread("EaterThread-3", table, 38327),
            // new ClearThread("ClearThread-0", table),
            // new LazyThread("LazyThread-1", table),
            // new LazyThread("LazyThread-2", table),
            // new LazyThread("LazyThread-3", table),
            // new LazyThread("LazyThread-4", table),
            // new LazyThread("LazyThread-5", table),
            // new LazyThread("LazyThread-6", table),
            // new LazyThread("LazyThread-7", table)
        };
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // try {
        //     Thread.sleep(15000);
        // } catch (InterruptedException e) {
        // }
        // for (int i = 0; i < threads.length; i++) {
        //     threads[i].interrupt();
        // }
    }
}

class MakerThread extends Thread {
    private static int id = 0;
    private final Table table;
    private final Random random;
    public MakerThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000));
                String cake = "[ Cake No." + nextId() + " by " + Thread.currentThread().getName() + " ]";
                table.put(cake);
            }
        } catch (InterruptedException e) {
        }
    }
    private static synchronized int nextId() {
        return id++;
    }
}

class EaterThread extends Thread {
    private final Random random;
    private final Table table;
    public EaterThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }
    public void run() {
        try {
            while (true) {
                String cake = table.take();
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}

class ClearThread extends Thread {
    private final Table table;
    public ClearThread(String name, Table table) {
        super(name);
        this.table = table;
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                System.out.println("=====" + Thread.currentThread().getName() + " clears =====" );
                table.clear();
            }
        } catch (InterruptedException e) {
        }
    }
}

class Table {
    private final String[] buffer;
    private int tail;   // 下次 put 的位置
    private int head;   // 下次 take 的位置
    public Table(int num) {
        buffer = new String[num];
        tail = 0;
        head = 0;
    }
    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts " + cake);
        while (tail - head >= buffer.length) { // 桌子满了
            System.out.println(Thread.currentThread().getName() + " wait BEGIN");
            wait();
            System.out.println(Thread.currentThread().getName() + " wait END");
        }
        buffer[tail++ % 3] = cake;
        notifyAll();
    }
    public synchronized String take() throws InterruptedException {
        while (tail - head <= 0) { // 桌子为空
            System.out.println(Thread.currentThread().getName() + " wait BEGIN");
            wait();
            System.out.println(Thread.currentThread().getName() + " wait END");
        }
        String cake = buffer[head++ % buffer.length];
        System.out.println(Thread.currentThread().getName() + " gets " + cake);
        notifyAll();
        return cake;
    }
    public synchronized void clear() {
        while (tail - head > 0) {
            String cake = buffer[head % buffer.length];
            System.out.println(Thread.currentThread().getName() + " clears " + cake);
            head++;
        }
        head = 0;
        tail = 0;
        notifyAll();  
    }
}

class LazyThread extends Thread {
    private final Table table;
    public LazyThread(String name, Table table) {
        super(name);
        this.table = table;
    }
    public void run() {
        while (true) {
            try {
                synchronized (table) {
                    table.wait();
                }
                System.out.println(Thread.currentThread().getName() + " is notified!");
            } catch (InterruptedException e) {
            }
        }
    }
}