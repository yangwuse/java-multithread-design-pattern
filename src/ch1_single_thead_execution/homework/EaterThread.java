package ch1_single_thead_execution.homework;

// 左手和右手分别拿起餐具 开始使用餐的类
public class EaterThread extends Thread {
    private String name;
    private final Tool leftHand;
    private final Tool rightHand;

    public EaterThread(String name, Tool leftHand, Tool rightHand) {
        this.name = name;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public void run() {
        while (true) 
            eat();
    }

    // 导致死锁的方法
    // public void eat() {
    //     // 左手拿起餐具
    //     synchronized(leftHand) {
    //         System.out.println(name + " takes up " + leftHand + " (left).");
    //         // 右手拿起餐具
    //         synchronized(rightHand) {
    //             System.out.println(name + " takes up " + rightHand + " (right).");
    //             System.out.println(name + " is eating now");
    //             System.out.println(name + " puts down " + rightHand + " (right).");
    //         }
    //         System.out.println(name + " puts donw " + leftHand + " (left).");
    //     }
    // }

    public synchronized void eat() {
        // 左手拿起餐具
        System.out.println(name + " takes up " + leftHand + " (left).");
        // 右手拿起餐具
        System.out.println(name + " takes up " + rightHand + " (right).");
        System.out.println(name + " is eating now");
        System.out.println(name + " puts down " + rightHand + " (right).");
        System.out.println(name + " puts donw " + leftHand + " (left).");
    }
}
