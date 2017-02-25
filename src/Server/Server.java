package Server;

import NotificationHandlers.ExceptionHandler;
import controller.Controller;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private List<PrintWriter> clientsConnected;
    private ServerSocket serverSocket;
    private ExceptionHandler exceptionHandler;

    public Server(String portNumber) {
        exceptionHandler = new ExceptionHandler();
        try {
            int port = Integer.parseInt(portNumber);
            this.serverSocket = new ServerSocket(port);
            this.clientsConnected = new ArrayList<>();
        } catch (Exception ex) {
            exceptionHandler.throwExceptionNotification(ex.getMessage());
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
            exceptionHandler.throwExceptionNotification(ex.getMessage());
            Controller.changeRunningStatus(false);
        }
    }
}
