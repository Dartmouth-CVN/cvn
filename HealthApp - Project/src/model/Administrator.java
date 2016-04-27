package model;

import java.time.LocalDate;

public class Administrator extends AbsUser {

    public Administrator() {

    }

    @Override
    public String getUserId() {
        return "AD" + userIdValue;
    }

    public Administrator(long userIdValue, String firstName, String lastName, String username, String password,
                         LocalDate birthday, String room, String picture) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture);
    }
    public Administrator(long userIdValue, String firstName, String lastName, String username, String password,
                         LocalDate birthday, String room, String picture, Contact contactInfo) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo);
    }

    public static String getUserType() {
        return "ADMIN";
    }

    @Override
    public AbsUser fromXMLString() {
        return null;
    }

    @Override
    public AbsUser fromCSVString() {
        return null;
    }

    @Override
    public AbsUser fromTSVString() {
        return null;
    }

    @Override
    public AbsUser fromSVString(String delimiter) {
        return null;
    }

    public static String getRole(){
        return "ADMIN";
    }
}
