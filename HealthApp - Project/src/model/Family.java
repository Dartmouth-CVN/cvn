package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Family extends AbsRelation {

    public Family() {

    }

    public Family(String userId, String firstName, String lastName, String username, String password,
                  LocalDate birthday, String room, String picture, Contact contactInfo, String relation, boolean isCaregiver) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation, true, isCaregiver);
    }

    public Family(String userId, String firstName, String lastName, String username, String password,
                  LocalDate birthday, String room, String picture, String relation, boolean isCaregiver) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, new Contact(), relation, true, isCaregiver);
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
