package ch2_immutable;

public class Main {
    public static void main(String[] args) {
        Person alice = new Person("Alice", "A");
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
    }
}
