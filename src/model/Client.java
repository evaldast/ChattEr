package model;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private PrintWriter writer;

    public Client(ViewModel model, String portNumber, String ipAddress) {
        try {
            Socket socket = new Socket(ipAddress, Integer.parseInt(portNumber));
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
