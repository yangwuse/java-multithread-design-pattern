package java_synchronized;

// 反复取出、存取 1000 元
public class ClientThread extends Thread{
    private Bank bank;
    public ClientThread(Bank bank) {
        this.bank = bank;
    }
    public void run() {
        while (true) {
            boolean ok = bank.withdraw(1000);
            if (ok) {
                bank.deposite(1000);
            }
        }
    }
}
