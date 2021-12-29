package ch5_producer_consumer;

public class Main4 {
    public static void main(String[] args) {
        System.out.println("BEGIN");
        try {
            Something.method(3000);;
        } catch (InterruptedException e) {
        }
        System.out.println("END");
    }
}

// 暂停 xms
class Something {
    public static void method(long x) throws InterruptedException {
        if (x != 0) {
            Object obj = new Object();
            synchronized(obj) {
                obj.wait(x);
            }
        }
    }
}
