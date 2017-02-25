package controller;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Client;
import model.Server;
import model.ViewModel;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private boolean isRunning;
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
            if(drawer.isShown()) {
                drawer.close();
                /*Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> showHideUsersConnected(true)));
                fiveSecondsWonder.play();*/
            }
            else {
                drawer.open();
                //showHideUsersConnected(false);
            }
        });
    }

    @FXML
    void joinServer(ActionEvent event) {
        if (isHosting) startServer();

        client = new Client(model, portField.getText(), ipField.getText());
        setUp();
        /*startButton.setText("Disconnect");
        startButton.setStyle("-fx-background-color: #d9534f;");*/
        notify("Connected to Server");
    }

    @FXML
    public void sendMessageEnter(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        client.sendMessage(nameField.getText() + ":  " + messageField.getText());
        messageField.clear();
    }
}

    @FXML
    void sendMessage(ActionEvent event) {
        client.sendMessage(nameField.getText() + ":  " + messageField.getText());
        messageField.clear();
    }

    @FXML
    private void toggleHost(ActionEvent event) {
        isHosting = !isHosting;
        ipField.setText("127.0.0.1");
        ipField.setDisable(true);
    }

    private void startServer() {
        Thread serverThread = new Thread(new Server(portField.getText()));
        serverThread.start();
    }

    private void setUp() {
        model.setName(nameField.getText());
        model.setUpChatListener(textArea);
        portField.setDisable(true);
        ipField.setDisable(true);
        nameField.setDisable(true);
    }

    private void notify(String text) {
        Notifications builder = Notifications.create()
                .text("text")
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        builder.darkStyle();
        builder.showInformation();
    }

    /*private void showHideUsersConnected(boolean isShown) {
        usersConnectedPane.setVisible(isShown);
        usersConnectedText.setVisible(isShown);
        usersConnectedTextArea.setVisible(isShown);
    }*/
}

