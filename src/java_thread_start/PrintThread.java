package java_thread_start;

// 通过 Thread 子类启动线程
public class PrintThread extends Thread {
    private String msg;
    public PrintThread(String msg) {
        this.msg = msg;
    }
    // run() 定义线程执行的任务
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.print(msg);
        }
    }
}
