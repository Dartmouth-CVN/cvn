package model;

import java.time.LocalDate;

/**
 * Created by mrampiah on 4/23/16.
 */
public abstract class AbsRelation extends AbsUser {
    String relationship;
    boolean isFamily;
    boolean isCaregiver;

    public AbsRelation() {
    }

    public AbsRelation(long userIdValue, String firstName, String lastName, String username, String password,
                       LocalDate birthday, String room, String picture, Contact contactInfo, String relationship,
                       boolean isFamily, boolean isCaregiver) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo);
        this.relationship = relationship;
        this.isFamily = isFamily;
        this.isCaregiver = isCaregiver;
    }

    public AbsRelation(long userIdValue, String firstName, String lastName, String username, String password,
                       LocalDate birthday, String room, String picture, String relationship,
                       boolean isFamily, boolean isCaregiver) {
        this(userIdValue, firstName, lastName, username, password, birthday, room, picture,
                new Contact(), relationship, isFamily, isCaregiver);
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public boolean isFamily() {
        return isFamily;
    }

    public void setFamily(boolean family) {
        isFamily = family;
    }

    public boolean isCaregiver() {
        return isCaregiver;
    }

    public void setCaregiver(boolean caregiver) {
        isCaregiver = caregiver;
    }

    public static String getRole(){
        return "RELATION";
    }
}
