package model;

import utils.RandomGenerator;

import java.io.Serializable;

public class ContactElement{
    long elementId;
    String value;
    String type;
    String contactType;//TYPES: PHONE, EMAIL, ADDRESS

    public ContactElement() {
        this(0, "Enter contact info...", "Personal", "PHONE");
    }

    public ContactElement(long elementId, String value, String type, String contactType) {
        setElementId(elementId);
        setValue(value);
        setType(type);
        setContactType(contactType);
    }

    public ContactElement( String value, String type, String contactType) {
        this(0L, value, type, contactType);
    }

    public ContactElement(String value, String type) {
        this(RandomGenerator.getRandomId(), value, type, "CONTACT ELEMENT");
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

    public String getContactType(){
        return contactType;
    }

    public void setContactType(String contactType){
        this.contactType = contactType;
    }
}
