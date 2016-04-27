package model;

import utils.RandomGenerator;

import java.io.Serializable;

public class ContactElement{
    long elementId;
    String value;
    String type;

    public ContactElement() {
        this(0, "Enter contact info...", "Personal");
    }

    public ContactElement(long elementId, String value, String type) {
        setElementId(elementId);
        setValue(value);
        setType(type);
    }

    public ContactElement(String value, String type) {
        this(RandomGenerator.getRandomId(), value, type);
    }

    public long getElementId() {
        return elementId;
    }

    public void setElementId(long elementId) {
        this.elementId = elementId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return value.hashCode() * type.hashCode();
    }

    public static String getContactType(){
        return "CONTACT ELEMENT";
    }
}
