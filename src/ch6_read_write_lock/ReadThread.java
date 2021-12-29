package ch6_read_write_lock;

public class ReadThread extends Thread {
    private final Data data;
    public ReadThread(Data data) {
        this.data = data;
    }
    public void run() {
        try {
            while (true) {
                char[] readbuf = data.read();
                System.out.println(Thread.currentThread().getName() + " reads " +
                String.valueOf(readbuf));
            }            
        } catch (InterruptedException e ) {
        }
    }
    
}
