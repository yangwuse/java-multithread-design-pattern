package ch8_worker_thread;

import java.util.Random;
import java.util.stream.Stream;

// 8-1
public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);  // 工人线程个数
        channel.startWorkers();
        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();
    }    
}

class ClientThread extends Thread {
    private final Channel channel;
    private static final Random random = new Random();
    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }
    public void run() {
        try {
            for (int i  = 0; true; i++) {
                Request request = new Request(Thread.currentThread().getName(), i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}

class Request {
    private final String name;
    private final int number;
    private static final Random random = new Random();
    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }
    public void execute() {
        System.out.println(Thread.currentThread().getName() + " execute " + this);
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }
    public String toString() {
        return "[ Request from " + name + " No." + number + " ]";
    }
}

class Channel {
    private static final int MAX_REQUEST = 100;
    private final Request[] requestQueue;
    private int tail;   // 下次 putRequest 的位置
    private int head;   // 下次 takeRequest 的位置
    private final WorkerThread[] threadPool;
    public Channel(int threads) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.threadPool = new WorkerThread[threads];
        for (int i = 0; i < threads; i++) {
            threadPool[i] = new WorkerThread("Worker-" + i, this);
        }
    }
    public void startWorkers() {
        Stream.of(threadPool).forEach(e -> e.start());
    }
    public synchronized void putRequest(Request request) {
        while (tail - head >= MAX_REQUEST) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        requestQueue[tail++ % MAX_REQUEST] = request;
        notifyAll();
    }
    public synchronized Request takeRequest() {
        while (tail - head <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
        return requestQueue[head++ % MAX_REQUEST];
    }
    
}

class WorkerThread extends Thread {
    private final Channel channel;
    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }
    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}