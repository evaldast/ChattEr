package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.Server;
import model.ViewModel;

public class Controller {

    private boolean isHosting;
    private Client client;
    private ViewModel model;

    public Controller() {
        this.model = new ViewModel();
    }

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton startButton;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField ipField;

    @FXML
    private JFXTextField portField;

    @FXML
    private JFXCheckBox hostToggle;

    @FXML
    private JFXTextField messageField;

    @FXML
    public JFXTextArea textArea;

    @FXML
    private JFXButton sendButton;

    @FXML
    void joinServer(ActionEvent event) {
        if (isHosting) {
            Thread serverThread = new Thread(new Server(portField.getText()));
            ipField.setText("127.0.0.1");
            serverThread.start();
        }
        client = new Client(model, portField.getText(), ipField.getText());
        model.setName(nameField.getText());
        model.setUpChatListener(textArea);
    }

    @FXML
    private void startServer(ActionEvent event) {
        isHosting = !isHosting;
    }

    @FXML
    void sendMessage(ActionEvent event) {
        client.sendMessage(nameField.getText() + ": " + messageField.getText());
        messageField.clear();
    }
}

