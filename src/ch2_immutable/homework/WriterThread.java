package ch2_immutable.homework;

import java.util.List;

// 读线程 循环调用 add(i) remove(0)
public class WriterThread extends Thread{
    private final List<Integer> list;
    public WriterThread(List<Integer> list) {
        super("WriterThread");
        this.list = list;
    }
    public void run() {
        for (int i = 0; true; i++) {
            list.add(i);
            list.remove(0);
        }
    }
}
