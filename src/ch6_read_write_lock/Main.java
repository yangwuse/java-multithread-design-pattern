package ch6_read_write_lock;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(10);
        IntStream.range(0, 7).forEach((e) -> new ReadThread(data).start());
        new WriteThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
        new WriteThread(data, "abcdefghijklmnopqrstuvwxyz").start();
    }
}
