package controller;

import Client.Client;
import NotificationHandlers.NotificationHandler;
import Server.Server;
import Services.Validator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.ViewModel;

public class Controller {

    private static boolean isRunning;
    private boolean isHosting;
    private Client client;
    private ViewModel model;
    private NotificationHandler notificationHandler;
    private Validator validator;

    @FXML
    private TextArea textArea;

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

    public Controller() {
        model = new ViewModel();
        notificationHandler = new NotificationHandler();
        validator = new Validator();
    }

/*    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawer.setSidePane(sideBar);

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, mouseClick ->{
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if(drawer.isShown()) {
                drawer.close();
                Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> showHideUsersConnected(true)));
                fiveSecondsWonder.play();
            }
            else {
                drawer.open();
                showHideUsersConnected(false);
            }
        });
    }*/

    public static void changeRunningStatus(boolean status) {
        isRunning = status;
    }

    @FXML
    void joinServer() {
        if (!validator.validateFields(nameField.getText(), ipField.getText(), portField.getText())) {
            return;
        }

        if (isHosting) startServer();

        client = new Client(model, portField.getText(), ipField.getText());

        if (isRunning) {
            setUp();
        /*startButton.setText("Disconnect");
        startButton.setStyle("-fx-background-color: #d9534f;");*/
            notificationHandler.throwNotification("Connected to Server");
        }
    }

    @FXML
    public void sendMessageEnter(KeyEvent event) {
        if (isRunning) {
            if (event.getCode() == KeyCode.ENTER) {
                client.sendMessage(nameField.getText() + ":  " + messageField.getText());
                messageField.clear();
            }
        } else {
            notificationHandler.throwNotification("Connect to Server first");
        }
    }

    @FXML
    void sendMessage() {
        if (isRunning) {
            client.sendMessage(nameField.getText() + ":  " + messageField.getText());
            messageField.clear();
        } else {
            notificationHandler.throwNotification("Connect to Server first");
        }
    }

    @FXML
    private void toggleHost() {
        isHosting = !isHosting;
        ipField.setText("127.0.0.1");
        ipField.setDisable(true);
    }

    private void startServer() {
        Thread serverThread = new Thread(new Server(portField.getText()));
        serverThread.start();
    }

    private void setUp() {
        textArea.setWrapText(true);
        model.setName(nameField.getText());
        model.setUpChatListener(textArea);
        portField.setDisable(true);
        ipField.setDisable(true);
        nameField.setDisable(true);
        hostToggle.setDisable(true);
        startButton.setDisable(true);
    }

    /*private void showHideUsersConnected(boolean isShown) {
        usersConnectedPane.setVisible(isShown);
        usersConnectedText.setVisible(isShown);
        usersConnectedTextArea.setVisible(isShown);
    }*/
}

