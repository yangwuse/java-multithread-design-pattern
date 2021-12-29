package ch9_future;

// 9-1
public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();
        Data data1 = host.request(10, 'A');
        Data data2 = host.request(20, 'B');
        Data data3 = host.request(30, 'C');
        System.out.println("main otherJob BEGIN");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("main otherJob END");
        System.out.println("data1 = " + data1.getContent());
        System.out.println("data2 = " + data2.getContent());
        System.out.println("data3 = " + data3.getContent());
        System.out.println("main END");
    }
}

class Host {
    public Data request(int count, char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");
        final FutureData future = new FutureData();
        new Thread(
            ()-> {
                RealData realData = new RealData(count, c);
                future.setRealData(realData);
            } 
        ).start();
        System.out.println("    request(" + count + ", " + c + ") END");
        return future;
    }
}

interface Data {
    String getContent();
}

class FutureData implements Data {
    private RealData realData;
    private boolean ready;
    public synchronized void setRealData(RealData realData) {
        if (ready)
            return;
        this.realData = realData;
        ready = true;
        notifyAll();
    }
    public synchronized String getContent() {
        while (!ready) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        return realData.getContent();
    }
}

class RealData implements Data {
    private StringBuilder content = new StringBuilder();
    public RealData(int count, char c) {
        for (int i = 0; i < count; i++)
            content.append(c);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
    }
    public String getContent() {
        return content.toString();
    }
}