package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Family extends AbsRelation {

    public Family() {

    }

    public Family(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                  Date birthday, java.lang.String room, String picture, Contact contactInfo, java.lang.String relation, boolean isCaregiver) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation, true, isCaregiver);
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
