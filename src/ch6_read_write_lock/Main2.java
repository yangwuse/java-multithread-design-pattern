package ch6_read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main2 {
    public static void main(String[] args) {
        Database<String, String> database = new Database<>();
        new AssignThread(database, "Alice", "Alaska").start();
        new AssignThread(database, "Alice", "Australia").start();
        new AssignThread(database, "Bobby", "Brazil").start();
        new AssignThread(database, "Bobby", "Bulgaria").start();
        for (int i = 0; i < 100; i++) {
            new RetrieveThread(database, "Alice").start();
            new RetrieveThread(database, "Bobby").start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }
}

class Database<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock readlock = lock.readLock();
    private final Lock writelock = lock.writeLock();
    public void clear() {
        writelock.lock();
        try {
            verySlow();
            map.clear();
        } finally {
            writelock.unlock();
        }
    }
    public void assign(K key, V value) {
        writelock.lock();
        try {
            verySlow();
            map.put(key, value);
        } finally {
            writelock.unlock();
        }
    }
    public V retrieve(K key) {
        readlock.lock();
        try {
            slow();
            return map.get(key);
        } finally {
            readlock.unlock();
        }
    }
    private void slow() {
        try {
            Thread.sleep(50);;
        } catch (InterruptedException e) {
        }
    }
    private void verySlow() {
        try {
            Thread.sleep(500);;
        } catch (InterruptedException e) {
        }
    }
}

class RetrieveThread extends Thread {
    private final Database<String, String> database;
    private final String key;
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);
    public RetrieveThread(Database<String, String> database, String key) {
        this.database = database;
        this.key = key;
    }
    public void run() {
        while (true) {
            int counter = atomicCounter.incrementAndGet();
            String value = database.retrieve(key);
            System.out.println(counter + ":" + key + " => " + value);
        }        
        
    }
}

class AssignThread extends Thread {
    private final Database<String, String> database;
    private final String key, value;
    private static final Random random = new Random(314159);

    public AssignThread(Database<String,String> database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
    }
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":assign(" + key + ", " + value + ")");
            database.assign(key, value);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
            }
        }
    }
}


