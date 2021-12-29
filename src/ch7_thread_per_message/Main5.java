package ch7_thread_per_message;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//使用 ScheduledExecutorService 推迟线程执行
public class Main5 {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        Host5 host = new Host5(scheduledExecutorService);
        try {
            host.request(10, 'A');
            host.request(20, 'B');
            host.request(30, 'C');
        } finally {
            scheduledExecutorService.shutdown();
            System.out.println("main END");
        }
    }
}


// 委托类
class Host5 {
    private final Helper helper = new Helper();
    private final ScheduledExecutorService scheduledExecutorService;
    public Host5(ScheduledExecutorService service) {
        scheduledExecutorService = service;
    }
    public void request(int count, char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");
        scheduledExecutorService.schedule(
            () -> helper.handler(count, c), 3, TimeUnit.SECONDS);
        System.out.println("    request(" + count + ", " + c + ") END");
    }
}