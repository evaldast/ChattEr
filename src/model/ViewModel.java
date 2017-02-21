package model;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel {

    private StringProperty text = new SimpleStringProperty("Initial text...");
    private StringProperty name = new SimpleStringProperty("Name");

    public void setText(String text) {
        this.text.setValue(text);
    }
    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setUpChatListener(JFXTextArea textArea) {
        text.addListener((textProperty, oldValue, newValue) -> textArea.appendText("\n" + name.getValue() + ": " + newValue));
    }
}
