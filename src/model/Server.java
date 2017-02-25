package model;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private List<PrintWriter> clientsConnected;
    private ServerSocket serverSocket;

    public Server(String portNumber) {
        try {
            int port = Integer.parseInt(portNumber);
            this.serverSocket = new ServerSocket(port);
            this.clientsConnected = new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.receiveMessages();
    }

    private void receiveMessages() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                this.clientsConnected.add(writer);

                Thread senderThread = new Thread(new MessageSenderThread(clientSocket, this.clientsConnected));
                senderThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
