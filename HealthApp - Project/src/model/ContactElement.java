package model;

import utils.RandomGenerator;

import java.io.Serializable;

public class ContactElement{
    long elementId;
    String value;
    String type;
    String contactType;//TYPES: PHONE, EMAIL, ADDRESS
    public static enum contactLabel {WORK, HOME, OFFICE}

    public ContactElement() {
        this(0, "", "", "");
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
    @Override
    public String toString(){
        return String.format("%s, %s, %s", value, type, contactType);
    }

    public static ContactElement fromSVString(String line){
        String[] values = line.split(",");
        String value = "";
        String type = "";
        String contactType = "";
        if(values.length > 0) {
            value = values[0];
            type = values[1];
            contactType = values[2];
        }
        return new ContactElement(value, type, contactType);
    }
}
