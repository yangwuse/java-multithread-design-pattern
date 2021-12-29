package ch2_immutable.homework;

public class Main2 {
    private static final long CALL_COUNT = 1000000000L;
    public static void main(String[] args) {
        trail("NotSynch", CALL_COUNT, new NotSynch());
        trail("Synch", CALL_COUNT, new Synch());
    }

    private static void trail(String msg, long count, Object obj) {
        System.out.println(msg + ": BEGIN");
        long start_time = System.currentTimeMillis();
        for (long i = 0; i < CALL_COUNT; i++) {
            obj.toString();
        }
        System.out.println(msg + ": END");
        System.out.println("Elapsed time = " + (System.currentTimeMillis() - start_time) + "msec.");
    }
}

class NotSynch {
    private final String name = "NotSynch";
    public String toString() {
        return "[ " + name + " ]";
    }
}

class Synch {
    private final String name = "Synch";
    public synchronized String toString() {
        return "[ " + name + " ]";
    }
}


