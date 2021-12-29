package ch2_immutable.homework;

public class Main3 {
    public static void main(String[] args) {
        MutablePerson mutable = new MutablePerson("start", "start");
        new CrackerThread(mutable).start();
        new CrackerThread(mutable).start();
        new CrackerThread(mutable).start();
        for (int i = 0; true; i++) {
            mutable.setPerson("" + i, "" + i);
        }

    }
}

class CrackerThread extends Thread {
    private final MutablePerson mutable;
    public CrackerThread(MutablePerson mutablePerson) {
        mutable = mutablePerson;
    }
    public void run() {
        while (true) {
            ImmutablePerson immutable = new ImmutablePerson(mutable);
            if (!immutable.getName().equals(immutable.getAddress())) {
                System.out.println(currentThread().getName() + " **** BROKEN ****" + immutable);
            }
        }
    }
}

final class MutablePerson {
    private String name;
    private String address;
    public MutablePerson(String name, String addres) {
        this.name = name;
        this.address = addres;
    }

    public MutablePerson(ImmutablePerson person) {
        this.name = person.getName();
        this.address = person.getAddress();
    }

    public synchronized void setPerson(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public synchronized ImmutablePerson getImmutablePerson() {
        return new ImmutablePerson(this);
    }

    String getName() {
        return name;
    }

    String getAddress() {
        return address;
    }

    public synchronized String toString() {
        return "[ MutablePerson: " + name + ", " + address + " ]";
    }
}

final class ImmutablePerson {
    private final String name;
    private final String address;

    public ImmutablePerson(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ImmutablePerson(MutablePerson person) {
        // 加锁
        synchronized(person) {
            this.name = person.getName();
            this.address = person.getAddress();
        }
    }

    public MutablePerson getMutablePerson() {
        return new MutablePerson(this);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String toString() {
        return "[ ImmutablePerson: " + name + ", " + address + " ]";
    }
}
