package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DisplayString {
    private StringProperty stringProperty;
    private String string;

    public DisplayString(String string) {
        this.string = string;
        this.stringProperty = new SimpleStringProperty(string);
    }

    public StringProperty getStringProperty() {
        return stringProperty;
    }

    public String getString() {
        return this.string;
    }

}
