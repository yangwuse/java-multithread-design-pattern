package ch6_read_write_lock;

// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReadWriteLock;
// import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Data {
    private final char[] buffer;
    private ReadWriteLock lock = new ReadWriteLock();
    // private final ReadWriteLock lock2 = new ReentrantReadWriteLock(true);
    // private final Lock readLock = lock2.readLock();
    // private final Lock writeLock = lock2.writeLock();
    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) 
            buffer[i] = '*';
    }
    public char[] read() throws InterruptedException {
        lock.readLock();
        try {
            return doRead();
        } finally {
            lock.readUnlock();
        }
    }
    public void write(char c) throws InterruptedException {
        lock.writeLock();
        try {
            doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }
    private char[] doRead() {
        char[] newbuf = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++)
            newbuf[i] = buffer[i];
        slowly();
        return newbuf;
    }
    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }
    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }
}
