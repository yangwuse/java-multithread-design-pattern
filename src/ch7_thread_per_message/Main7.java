package ch7_thread_per_message;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

// 习题 7-5
public class Main7 {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame implements ActionListener {
    public MyFrame() {
        super("MyFrame");
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(new JLabel("Thread-Per-Message Sample"));
        JButton button = new JButton("Execute");
        getContentPane().add(button);
        button.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Service.service();
    }

}

class Service {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void service() {
        System.out.println("service");
        executorService.execute(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(".");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });
        System.out.println("done.");
    }
}
