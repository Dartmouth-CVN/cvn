package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Nurse extends MedicalStaff {

    public Nurse() {

    }

    public Nurse(long userIdValue, String firstName, String lastName, String username, String password,
                 LocalDate birthday, String room, String picture, Contact contactInfo) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo);
    }

    public static String getRole(){
        return "NURSE";
    }
}
