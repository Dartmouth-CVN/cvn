package model;

import java.util.Date;

public class Caregiver extends AbsRelation {

    public Caregiver() {

    }

    public Caregiver(String userId, String firstName, String lastName, String username, String password,
                     Date birthday, String room, String picture, Contact contactInfo, String relation, boolean isFamily) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation, isFamily, true);
    }
}
