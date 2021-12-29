package ch7_thread_per_message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 使用 ExecutorService 复用线程
public class Mian4 {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        ExecutorService executorService = Executors.newCachedThreadPool();
        Host3 host = new Host3(
            executorService
        );
        try {
            host.request(10, 'A');
            host.request(20, 'B');
            host.request(30, 'C');
        } finally {
            executorService.shutdown();
            System.out.println("main END");
        }
    }
}
