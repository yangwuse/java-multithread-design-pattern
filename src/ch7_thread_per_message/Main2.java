package ch7_thread_per_message;

import java.util.concurrent.ThreadFactory;

// 控制线程的创建
public class Main2 {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        // 使用 ThreadFactory 创建线程  
        Host2 host = new Host2(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }});
        
        // 使用 Executors 获取 ThreadFactory
        // Host2 host = new Host2(Executors.defaultThreadFactory());
        host.request(10, 'A');
        host.request(20, 'B');
        host.request(30, 'C');
        System.out.println("main END");
    }
}

// 委托类
class Host2 {
    private final Helper helper = new Helper();
    private final ThreadFactory threadFactory;
    public Host2(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }
    public void request(int count, char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");
        threadFactory.newThread(() -> helper.handler(count, c)).start();
        System.out.println("    request(" + count + ", " + c + ") END");
    }
}

// 处理类
class Helper2 {
    public void handler(int count, char c) {
        System.out.println("    handle(" + count + ", " + c + ") BEGIN");
        for (int i = 0; i < count; i++) {
            slowly();
            System.out.print(c);
        }
        System.out.println("");
        System.out.println("    handle(" + count + ", " + c + ") END");
    }
    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException  e) {
        }
    }
}

