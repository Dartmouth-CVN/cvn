package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class ContactElementWrapper {
    LongProperty elementIdProperty;
    StringProperty valueProperty;
    StringProperty typeProperty;
    ContactElement element;

    public ContactElementWrapper(ContactElement element) {
        setElementIdProperty(element.getElementId());
        setValueProperty(element.getValue());
        setTypeProperty(element.getType());
        this.element = element;
    }

    public LongProperty getElementIdProperty() {
        return elementIdProperty;
    }

    public void setElementIdProperty(long elementId) {
        elementIdProperty = new SimpleLongProperty((elementId));
    }

    public StringProperty getValueProperty() {
        return valueProperty;
    }

    public void setValueProperty(String value) {
        valueProperty = new SimpleStringProperty(value);
    }

    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(String type) {
        typeProperty = new SimpleStringProperty(type);
    }

    public void setElement(ContactElement element){
        this.element = element;
    }

    public ContactElement toContactElement(){
        return element;
    }

}
