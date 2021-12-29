package ch2_immutable.homework;

import java.util.List;

// 读线程 循环打印 list 中的元素
public class ReaderThread extends Thread{
    private final List<Integer> list;
    public ReaderThread(List<Integer> list) {
        super("ReaderThread");
        this.list = list;
    }
    public void run() {
        while (true) {
            // 同步
            // synchronized(list) {
                for (int n : list) {
                    System.out.println(n);
                }
            // }
        }
    }
}
