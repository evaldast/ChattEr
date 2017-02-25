package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

class MessageSenderThread implements Runnable {

    private BufferedReader reader;
    private List<PrintWriter> clientsConnected;

    MessageSenderThread(Socket clientSocket, List<PrintWriter> clientsConnected) {
        try {
            this.clientsConnected = clientsConnected;
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            this.reader = new BufferedReader(inputStreamReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String messageToSend;
            while ((messageToSend = this.reader.readLine()) != null) {
                this.clientsConnected.get(0).println(messageToSend);
                this.clientsConnected.get(0).flush();
                for (PrintWriter element : this.clientsConnected) {
                    element.println(messageToSend);
                    element.flush();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
