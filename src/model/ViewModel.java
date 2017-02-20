package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel {

    public StringProperty text = new SimpleStringProperty("Initial text...");

    public StringProperty textProperty() {return text;}

}
