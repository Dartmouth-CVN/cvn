package model;

import utils.RandomGenerator;

public class ContactElement{
    long elementId;
    String value;
    String type;//TYPES: PHONE, EMAIL, ADDRESS
    String contactLabel;
    public static enum contactLabels {WORK, HOME, OFFICE}

    public ContactElement() {
        this(0, "ENTER INFO", contactLabels.HOME.name(), Contact.contactTypes.PHONE.name());
    }

    public ContactElement(long elementId, String value, String contactLabel, String type) {
        setElementId(elementId);
        setValue(value);
        setContactLabel(contactLabel);
        setType(type);
    }

    public ContactElement( String value, String contactLabel, String type) {
        this(0L, value, type, contactLabel);
    }

    public ContactElement(String value, String type) {
        this(RandomGenerator.getRandomId(), value, contactLabels.HOME.name(), type);
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

    public String getContactLabel(){
        return contactLabel;
    }

    public void setContactLabel(String contactLabel){
        this.contactLabel = contactLabel;
    }
    @Override
    public String toString(){
        return String.format("%s, %s, %s", value, type, contactLabel);
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
