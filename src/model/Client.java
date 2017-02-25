package model;

import controller.Controller;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private PrintWriter writer;
    private ExceptionHandler exceptionHandler;

    public Client(ViewModel model, String portNumber, String ipAddress) {
        exceptionHandler = new ExceptionHandler();
        try {
            Socket socket = new Socket(ipAddress, Integer.parseInt(portNumber));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            Thread receiverThread = new Thread(new MessageReceiverThread(socket, model));
            receiverThread.start();
        } catch (Exception ex) {
            exceptionHandler.throwExceptionNotification(ex.getMessage());
            Controller.changeRunningStatus(false);
        }
    }

    public void sendMessage(String message) {
        try {
            this.writer.println(message);
        } catch (Exception ex) {
            exceptionHandler.throwExceptionNotification(ex.getMessage());
        }
    }
}
