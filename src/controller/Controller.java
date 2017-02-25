package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Client;
import model.Server;
import model.ViewModel;
import org.controlsfx.control.Notifications;

public class Controller {

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

    @FXML
    void joinServer(ActionEvent event) {
        if(!validator()) {
            notify("Check if Fields are entered correctly");
            return;
        }

        if (isHosting) startServer();

        client = new Client(model, portField.getText(), ipField.getText());
        setUp();
        /*startButton.setText("Disconnect");
        startButton.setStyle("-fx-background-color: #d9534f;");*/
        notify("Connected to Server");
        isRunning = true;
    }

    @FXML
    public void sendMessageEnter(KeyEvent event) {
        if (isRunning) {
            if (event.getCode() == KeyCode.ENTER) {
                client.sendMessage(nameField.getText() + ":  " + messageField.getText());
                messageField.clear();
            }
        } else {
            notify("Connect to Server first");
        }
}

    @FXML
    void sendMessage(ActionEvent event) {
        if(isRunning) {
            client.sendMessage(nameField.getText() + ":  " + messageField.getText());
            messageField.clear();
        } else {
            notify("Connect to Server first");
        }
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
        hostToggle.setDisable(true);
        startButton.setDisable(true);
    }

    private void notify(String text) {
        Notifications builder = Notifications.create()
                .text(text)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        builder.darkStyle();
        builder.showInformation();
    }

    private boolean validator() {
        return !(nameField.getText().isEmpty() || nameField.getText() == null) &&
                ipField.getText().matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b") &&
                portField.getText().matches("^[0-9]+$");
    }

    /*private void showHideUsersConnected(boolean isShown) {
        usersConnectedPane.setVisible(isShown);
        usersConnectedText.setVisible(isShown);
        usersConnectedTextArea.setVisible(isShown);
    }*/
}

