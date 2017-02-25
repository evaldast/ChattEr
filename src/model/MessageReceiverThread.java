package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class MessageReceiverThread implements Runnable {

    private BufferedReader reader;
    private ViewModel model;
    private ExceptionHandler exceptionHandler;

    MessageReceiverThread(Socket socket, ViewModel model) throws IOException {
        exceptionHandler = new ExceptionHandler();
        this.model = model;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String messageReceived;
            while((messageReceived = this.reader.readLine()) != null) {
                this.model.setText(messageReceived);
            }
        } catch (Exception ex) {
            exceptionHandler.throwExceptionNotification(ex.getMessage());
        }
    }
}
