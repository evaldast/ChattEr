import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/layout.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("view/bubble.css").toExternalForm());
        primaryStage.setTitle("ChattEr");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
