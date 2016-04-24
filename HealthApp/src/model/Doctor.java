package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Doctor extends MedicalStaff {

    public Doctor(){

    }

    public Doctor(String userId, String firstName, String lastName, String username, String password,
                  Date birthday, String room, Contact contactInfo) {
        super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
    }

    @Override
    public String getRole(){
        return "Doctor";
    }
}
