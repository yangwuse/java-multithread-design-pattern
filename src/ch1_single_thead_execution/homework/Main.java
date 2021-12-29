package ch1_single_thead_execution.homework;

public class Main {
    public static void main(String[] args) {
        Tool spoon = new Tool("Spoon");
        Tool fork = new Tool("Fork");
        new EaterThread("Alice", spoon, fork).start();
        new EaterThread("Boddy", fork, spoon).start();
    }
}
