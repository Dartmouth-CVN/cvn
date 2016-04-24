package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Family extends AbsRelation {
    boolean isCaregiver;

    BooleanProperty isCaregiverProperty;

    public Family(){

    }

    public Family(String userId, String firstName, String lastName, String username, String password,
                  Date birthday, String room, Contact contactInfo, String relation, boolean isCaregiver) {
        super(userId, firstName, lastName, username, password, birthday, room, contactInfo, relation);
        setIsCaregiver(isCaregiver);
    }

    public void setIsCaregiver(boolean isCaregiver){
        this.isCaregiver = isCaregiver;
        setIsCaregiverProperty(isCaregiver);
    }

    public boolean getIsCaregiver(){
        return isCaregiver;
    }

    public void setIsCaregiverProperty(boolean isCaregiver){
        isCaregiverProperty = new SimpleBooleanProperty(isCaregiver);
    }

    public BooleanProperty getIsCaregiverProperty(){
        return isCaregiverProperty;
    }
}
