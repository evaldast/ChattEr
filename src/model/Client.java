package model;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private PrintWriter writer;

    public Client(ViewModel model) {
        try {
            int portNumber = 8082;
            System.out.println("Connecting to port: " + portNumber);
            String ipAddress = "127.0.0.1";
            Socket socket = new Socket(ipAddress, portNumber);
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            Thread receiverThread = new Thread(new MessageReceiverThread(socket, model));
            receiverThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            this.writer.println(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
