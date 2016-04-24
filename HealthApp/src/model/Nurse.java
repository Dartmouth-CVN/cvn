package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Nurse extends MedicalStaff {

    public Nurse(){

    }
    public Nurse(String userId, String firstName, String lastName, String username, String password,
                 Date birthday, String room, Contact contactInfo) {
        super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
    }

    @Override
    public String getRole(){
        return "Nurse";
    }
}
