package ch4_balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class Main1 {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread("ChangerThread", data).start();
        new SaverThread("SaverThread", data).start();
     }

}

// 表示当前数据的类
class Data {
    private final String filename;
    private String content;
    private boolean changed;


    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    // 修改数据内容
    public synchronized  void change(String newContent) {
        content = newContent;
        changed = true;
    }

    // 若数据更新过 则保存到文件
    public void save() throws IOException {
        if (!changed) {
            System.out.println(Thread.currentThread().getName() + " balking");
            return; 
        }
        doSave();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        changed = false;
    }

    // 将数据内容保存到文件中
    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls doSave, content = " + content);
        try (Writer writer = new FileWriter(filename)) {
            writer.write(content);
        }
    }
}

// 每隔 1s 保存数据的类
class SaverThread extends Thread {
    private final Data data;
    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }
    public void run() {
        try {
            while (true) {
                data.save();
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 修改并保存数据的类
class ChangerThread extends Thread {
    private final Data data;
    private final Random random = new Random();
    public ChangerThread(String name, Data data) {
        super(name);
        this.data = data;
    }
    public void run() {
        try {
            for (int i = 0; true; i++) {
                data.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                data.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}