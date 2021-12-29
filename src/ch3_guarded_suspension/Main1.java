package ch3_guarded_suspension;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main1 {
    public static void main(String[] args) {
        // RequestQueue requestQueue = new RequestQueue();
        // new ClientThread(requestQueue, "Alice1", 3141592L).start();
        // new ServerThread(requestQueue, "Boddy", 6535897L).start();

        // RequestQueue input = new RequestQueue();
        // RequestQueue output = new RequestQueue();
        // input.putRequest(new Request("Hello"));
        // new TalkThread(input, output, "Alice").start();
        // new TalkThread(output, input, "Boddy").start();

        // 启动线程
        RequestQueue requestQueue = new RequestQueue();
        Thread alice = new ClientThread(requestQueue, "Alice", 314159L);
        Thread bobby = new ClientThread(requestQueue, "Bobby", 265358L);
        alice.start();
        bobby.start();

        // 等待约 10 s
        // try {
        //     Thread.sleep(10000);
        // } catch (InterruptedException e) {}

        // // 调用 interrupt 方法
        // System.out.println("***** calling interupt *****");
        // alice.interrupt();
        // bobby.interrupt();
    }
}

// 表示一个请求的类
class Request {
    private final String name;
    public Request(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return "[ Request " + name + " ]";
    }
}

// 依次存放请求的类
class RequestQueue {
    private final long TIMEOUT = 5000l;
    private final Queue<Request> queue = new LinkedList<>();
    public synchronized Request getRequest() throws InterruptedException {
        long start = System.currentTimeMillis();
        while (queue.peek() == null) {
            long now = System.currentTimeMillis();
            long rest = TIMEOUT - (now - start);
            if (rest <= 0) {
                throw new LivenessException("throw by " + Thread.currentThread().getName());
            }
            try {
                wait(rest);
            } catch (InterruptedException e) {
            }
        }
        return queue.remove();
    }
    public synchronized void putRequest(Request request) {
        queue.offer(request);
        notifyAll();
    }
}

// 发送请求的类
class ClientThread extends Thread{
    // random 用于错开发送请求 
    private final Random random;
    private final RequestQueue requestQueue;
    public ClientThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
        this.random = new Random(seed);
    }
    public void run() {
        // 遇到中断异常 跳出循环
        try {
            for (int i = 0; i < 10000; i++) {
                Request request = new Request("No." + i);
                System.out.println(Thread.currentThread().getName() + " requests " + request);
                requestQueue.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {}
    }
}

// 接受请求的类
class ServerThread extends Thread {
    private Random random;
    private final RequestQueue requestQueue;
    public ServerThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
        this.random = new Random(seed);
    }
    public void run() {
        try {
            for (int i = 0; i < 10000; i++) {
                Request request = requestQueue.getRequest();
                System.out.println(Thread.currentThread().getName() + " handles " + request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {}
    }
}

class TalkThread extends Thread {
    private final RequestQueue input;
    private final RequestQueue output;
    public TalkThread(RequestQueue input, RequestQueue output, String name) {
        super(name);
        this.input = input;
        this.output = output;
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":BEGIN" );
        try {
            for (int i = 0; i < 20; i++) {
                // 接受请求
                Request request1 = input.getRequest();
                System.out.println(Thread.currentThread().getName() + " gets " + request1);
    
                // 加上一个感叹号再返给对方
                Request request2 = new Request(request1.getName() + "!");
                System.out.println(Thread.currentThread().getName() + " puts " + request2);
                output.putRequest(request2);
            }
        } catch (InterruptedException e) {}
        System.out.println(Thread.currentThread().getName() + ":END");
    }
}

class LivenessException extends RuntimeException {
    public LivenessException(String msg) {
        super(msg);
    }
}




