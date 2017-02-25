package Services;

import NotificationHandlers.NotificationHandler;

public class Validator {

    private NotificationHandler notificationHandler;

    public Validator() {
        this.notificationHandler = new NotificationHandler();
    }

    public boolean validateFields(String... field) {
        return validateName(field[0]) && validateIp(field[1]) && validatePort(field[2]);
    }

    private boolean validateName(String name) {
        if (!name.isEmpty()) return true;
        notificationHandler.throwNotification("Name Field is incorrect");
        return false;
    }

    private boolean validateIp(String ip) {
        if (ip.matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b")) return true;
        notificationHandler.throwNotification("IP Field is incorrect");
        return false;
    }

    private boolean validatePort(String port) {
        if (port.matches("^[0-9]+$")) return true;
        notificationHandler.throwNotification("Port Field is incorrect");
        return false;
    }
}
