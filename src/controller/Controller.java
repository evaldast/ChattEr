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

    private boolean launchServer;
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
        if (launchServer) {
            Thread serverThread = new Thread(new Server(Integer.parseInt(this.portField.getText())));
            this.ipField.setText("localhost");
            serverThread.start();
        }
        client = new Client(model);
        model.setUpChatListener(textArea);
        model.setName(nameField.getText());
    }

    @FXML
    void startServer(ActionEvent event) {
        launchServer = !launchServer;
        System.out.println(launchServer);
    }

    @FXML
    void sendMessage(ActionEvent event) {
        client.sendMessage(this.messageField.getText());
        messageField.clear();
    }

    /*private void setUpChatListener() {
        model.getText().addListener((textProperty, oldValue, newValue) -> {
             textArea.appendText("\n" + newValue);
             messageField.clear();
        });
    }*/
}

