package ch1_single_thead_execution;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing Gate");
        Gate gate = new Gate();
        new UserThread(gate, "Aaa", "AAA").start();
        new UserThread(gate, "Bbb", "BBB").start();
        new UserThread(gate, "Ccc", "CCC").start();

        P.x = 10;
    }
}

final class P {
    public static int x;
}
