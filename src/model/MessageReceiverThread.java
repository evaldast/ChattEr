package model;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class MessageReceiverThread implements Runnable {

    private BufferedReader reader;
    private ViewModel model;

    MessageReceiverThread(Socket socket, ViewModel model) throws IOException {
        this.model = model;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String messageReceived;
            while((messageReceived = this.reader.readLine()) != null) {
                System.out.println(messageReceived);
                this.model.setText(messageReceived);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
