package model;

/**
 * Created by mrampiah on 4/27/16.
 */
public class Email extends ContactElement {

    public Email(long elementId, String value, String type) {
        super(elementId, value, type);
    }

    public static String getContactType(){
        return "EMAIL";
    }
}
