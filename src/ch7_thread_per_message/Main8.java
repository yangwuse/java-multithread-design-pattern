package ch7_thread_per_message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 习题 7-18
public class Main8 {
    public static void main(String[] args) {
        try {
            new MiniServer(8888).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MiniServer {
    private final int portnumber;
    public MiniServer(int portnumber) {
        this.portnumber = portnumber;
    }
    public void execute() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(portnumber)) {
            System.out.println("Listening on " + serverSocket);
            while (true) {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to " + clientSocket);
                // 分发每个请求到单独的线程
                new Thread(() -> {
                    try {
                        Service8.service(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            
        }
    }
}

class Service8 {
    private Service8() {}
    public static void service(Socket clientSocket) throws IOException {
        System.out.println(Thread.currentThread().getName() + ": Service.service(" + clientSocket + ") BEGIN");
        try {
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeBytes("HTTP/1.0 200 OK\r\n");
            out.writeBytes("Content-type: text/html\r\n");
            out.writeBytes("\r\n");
            out.writeBytes("<html><head><title>Countdown</title></head><body>");
            out.writeBytes("<h1>Countdown start!</h1>");
            for (int i = 10; i >= 0; i--) {
                System.out.println(Thread.currentThread().getName() + ": Countdown i = " + i);
                out.writeBytes("<h1>" + i + "</h1>");
                out.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            out.writeBytes("</body></html>");
        } finally {
            clientSocket.close();
        }
        System.out.println(Thread.currentThread().getName() + ": Service.service(" + clientSocket + ") END");
    }
}
