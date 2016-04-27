package model;

/**
 * Created by mrampiah on 4/27/16.
 */
public class Address extends ContactElement {

    public Address(long elementId, String value, String type) {
        super(elementId, value, type);
    }

    public static String getContactType(){
        return "ADDRESS";
    }
}
