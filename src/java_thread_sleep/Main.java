package java_thread_sleep;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.print("Good!");
            try {
                // 暂停线程 1 s, 模拟耗时操作
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                // 忽略异常
            }
        }
    }
}
