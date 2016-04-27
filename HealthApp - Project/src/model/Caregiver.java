package model;

import java.time.LocalDate;

public class Caregiver extends AbsRelation {

    public Caregiver() {

    }

    @Override
    public String getUserId() {
        return "CA" + userIdValue;
    }

    public Caregiver(long userIdValue, String firstName, String lastName, String username, String password,
                     LocalDate birthday, String room, String picture, Contact contactInfo, String relation, boolean isFamily) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation, isFamily, true);
    }

    public Caregiver(long userIdValue, String firstName, String lastName, String username, String password,
                     LocalDate birthday, String room, String picture, String relation, boolean isFamily) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, new Contact(), relation, isFamily, true);
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
}
