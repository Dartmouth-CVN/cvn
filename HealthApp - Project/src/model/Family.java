package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class Family extends AbsRelation {
    boolean isCaregiver;

    BooleanProperty isCaregiverProperty;

    public Family(){

    }

    public Family(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                  Date birthday, java.lang.String room, String picture, Contact contactInfo, java.lang.String relation, boolean isCaregiver) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation);
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
