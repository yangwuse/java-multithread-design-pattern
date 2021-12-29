package java_synchronized;

// synchronized 同步方法
// 一次只允许一个线程运行实例中的 synchronized 方法
public class Bank {
    private String name;
    private int money;

    public Bank(String name, int money) {
        this.name = name;
        this.money = money;
    }
    
    // 存款
    public void deposite(int m) {
        money += m;
    }

    // 取款
    public boolean withdraw(int m) {
        if (money >= m) {
            money -= m;
            check();
            return true;
        } 
        return false;
    }

    private void check() {
        if (money < 0) {
            System.out.println("可用余额为负数! money = " + money);
        }
    }

    public String getName() {
        return name;
    }
}
