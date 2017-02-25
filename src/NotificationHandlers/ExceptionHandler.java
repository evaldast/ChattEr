package NotificationHandlers;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ExceptionHandler {

    private Notifications builder;

    public void throwExceptionNotification(String exception) {
        builder = Notifications.create()
                .text(exception)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        builder.darkStyle();
        builder.showError();
    }
}
