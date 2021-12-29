package java_thread_start;

public class Printer implements Runnable {
    private String msg;
    public Printer(String msg) {
        this.msg = msg;
    }
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.print(msg);
        }
    }
}
