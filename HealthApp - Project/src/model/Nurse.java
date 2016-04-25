package model;

import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Nurse extends MedicalStaff {

    public Nurse(){

    }
    public Nurse(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                 Date birthday, java.lang.String room, String picture, Contact contactInfo) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo);
    }

    @Override
    public java.lang.String getRole(){
        return "Nurse";
    }
}
