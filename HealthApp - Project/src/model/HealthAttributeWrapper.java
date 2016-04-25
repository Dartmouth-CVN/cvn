package model;

import javafx.beans.property.*;

/**
 * Created by mrampiah on 4/25/16.
 */
public class HealthAttributeWrapper<V> {


    LongProperty healthAttributeIdProperty;
    StringProperty nameProperty;
    ObjectProperty<V> valueProperty;
    StringProperty stringValueProperty;


    public LongProperty getHealthAttributeIdProperty() {
        return healthAttributeIdProperty;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public ObjectProperty<V> getValueProperty() {
        return valueProperty;
    }

    public StringProperty getStringValueProperty() {
        return stringValueProperty;
    }

    public void setHealthAttributeIdProperty(long healthAttributeId) {
        healthAttributeIdProperty = new SimpleLongProperty(healthAttributeId);
    }

    public void setNameProperty(String name) {
        nameProperty = new SimpleStringProperty(name);
    }

    public void setValueProperty(V value) {
        valueProperty = new SimpleObjectProperty<V>(value);
    }

    public void setStringValueProperty(String stringValue) {
        stringValueProperty = new SimpleStringProperty(stringValue);
    }
}
