package ch2_immutable.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// 非线程安全的 ArrayList 类
public class Main {
    public static void main(String[] args) {
        // final List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        // 每次写的时候复制
        final List<Integer> list = new CopyOnWriteArrayList<>();
        new WriterThread(list).start();
        new ReaderThread(list).start();
    }
}
