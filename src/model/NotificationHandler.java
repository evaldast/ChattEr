package model;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationHandler {

    private Notifications builder;

    public void throwNotification(String exception) {
        builder = Notifications.create()
                .text(exception)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        builder.darkStyle();
        builder.showInformation();
    }
}
