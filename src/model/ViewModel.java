package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;

public class ViewModel {

    private StringProperty text = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    public void setText(String text) {
        this.text.setValue(text);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setUpChatListener(TextArea textArea) {
        text.addListener((textProperty, oldValue, newValue) -> textArea.appendText("\n" + newValue));
    }
}
