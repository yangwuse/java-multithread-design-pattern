package ch5_producer_consumer;

public class Main3 {
    public static void main(String[] args) {
        Thread executor = new Thread(() -> {
            System.out.println("Host.execute BEGIN");
            try {
                Host.execute(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Host.execute END");
        });
        executor.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
        }
        System.out.println("***** inerrupt *****");
        executor.interrupt();
    }
}

// 表示执行繁重任务的类
class Host {
    public static void execute(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            doHeavyJob();
        }
    }
    private static void doHeavyJob() {
        System.out.println("doHeavyJob BEGIN");
        long start = System.currentTimeMillis();
        while (start + 10000 > System.currentTimeMillis()) {
        }
        System.out.println("doHeavyJob END");
    }
}
