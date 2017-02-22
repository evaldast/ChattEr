package controller;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Client;
import model.Server;
import model.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private VBox sideBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawer.setSidePane(sideBar);

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, mouseClick ->{
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if(drawer.isShown()) drawer.close();
            else drawer.open();
        });
    }

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

