package java_thread_start;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

// 主线程启动两个子线程后就结束运行
// 程序并未结束 等到所有子线程结束后 程序结束
public class Main {
    public static void main(String[] args) {
        // Thread 子类启动线程
        new PrintThread("Good!").start();
        new PrintThread("Nice!").start();

        // Runnable 实现类启动线程
        new Thread(new Printer("Good!")).start();
        new Thread(new Printer("Nice!")).start();

        // ThreadFactory 创建线程
        ThreadFactory factory = Executors.defaultThreadFactory();
        factory.newThread(new Printer("Nice!")).start();
        for (int i = 0; i < 1000; i++) {
            System.out.print("Good!");
        }
    }
}
