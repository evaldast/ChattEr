package model;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private List<PrintWriter> clientsConnected;
    private int portNumber;
    private ServerSocket serverSocket;

    public Server(int portNumber) {
        try {
            this.portNumber = portNumber;
            this.serverSocket = new ServerSocket(this.portNumber);
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
                System.out.println("Waiting for connection. Listening on port: " + this.portNumber);
                Socket clientSocket = serverSocket.accept();
                System.out.println("Socket accepted");

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
