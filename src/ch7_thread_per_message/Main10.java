package ch7_thread_per_message;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

// 习题 7-23
public class Main10 {
    public static void main(String[] args) {
        Thread.currentThread().setName("MainThread");
        Log.println("main:BEGIN");
        new Executor() {
            public void execute(Runnable r) {
                Log.println("execute:BEGIN");
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Log.println("newThread:BEGIN");
                        Thread t = new Thread(r, "QuizThread");
                        Log.println("newThread:END");
                        return t;
                    }
                }.newThread(r).start();
                Log.println("execute:END");
            }
        }.execute(
            new Runnable() {
                public void run() {
                    Log.println("run:BEGIN");
                    Log.println("Hello!");
                    Log.println("run:END");
                }
            }
        );
        Log.println("main:END");
    }
}

class Log {
    public static void println(String s) {
        System.out.println(Thread.currentThread().getName() + ":" + s);
    }
}

/**
 * MainThread:main:BEGIN
 * MainThread:execute:BEGIN
 * MainThread:newThread:BEGIN
 * MainThread:newThread:END
 * MainThread:execute:END
 * MainThread:main:END
 * QuizThread:run:BEGIN
 * QuizThread:Hello!
 * QuizThread:run:END
 */
