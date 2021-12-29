package java_synchronized;

// 银行同步存取
public class Main {
    public static void main(String[] args) {
        // Bank bank = new Bank("myBank", 1000);
        // new ClientThread(bank).start();
        // new ClientThread(bank).start();

        new Thread(new Task("test1")).start();
        new Thread(new Task("test2")).start();
    }

    public static void test1() {
        int j = 0;
        for (int i = 0; i < 10000; i++) {
            j++;
        }
    }

    public static synchronized void test2() {
        int j = 0;
        for (int i = 0; i < 10000; i++) {
            j++;
        }
    }
}

class Task implements Runnable {
    private String method;
    public Task(String method) {
        this.method = method;
    } 
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            if ("test1".equals(method)) Main.test1();
            else if ("test2".equals(method)) Main.test2();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime));
    }
}
