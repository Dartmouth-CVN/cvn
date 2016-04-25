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

    public ContactElementWrapper(ContactElement element){
        setElementIdProperty(element.getElementId());
        setValueProperty(element.getValue());
        setTypeProperty(element.getType());
    }



    public void setElementIdProperty(long elementId){
        elementIdProperty = new SimpleLongProperty((elementId));
    }

    public LongProperty getElementIdProperty(){
        return elementIdProperty;
    }

    public void setValueProperty(String value){
        valueProperty = new SimpleStringProperty(value);
    }

    public StringProperty getValueProperty(){
        return valueProperty;
    }

    public void setTypeProperty(String type){
        typeProperty = new SimpleStringProperty(type);
    }

    public StringProperty getTypeProperty(){
        return typeProperty;
    }


}
