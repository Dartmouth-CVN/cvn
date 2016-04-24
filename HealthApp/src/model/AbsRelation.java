package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class AbsRelation extends AbsUser {
    String relationship;

    StringProperty relationProperty;

    Patient patientKey;

    public AbsRelation(){}

    public AbsRelation (String userId, String firstName, String lastName, String username, String password,
                        Date birthday, String room, Contact contactInfo, String relationship){
        super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
        setRelationship(relationship);
    }

    public void setRelationship(String relationship){
        relationProperty = new SimpleStringProperty(relationship);
    }

    public StringProperty getRelationshipProperty(){
        return relationProperty;
    }

    public String getRelationship(){
        return relationship;
    }

    public Patient getPatientKey(){
        return patientKey;
    }

    public void setPatientKey(Patient p){
        patientKey = p;
    }
}
