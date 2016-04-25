package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class AbsRelationWrapper extends AbsUserWrapper {

    StringProperty relationshipProperty;
    StringProperty nameProperty;

    AbsRelation relation;

    public AbsRelationWrapper(AbsRelation relation){
        this.relation = relation;
        setRelationshipProperty(relation.getRelationship());
        setNameProperty(relation.getFirstName(), relation.getLastName());
    }

    public StringProperty getNameProperty(){
        return nameProperty;
    }

    public void setNameProperty(String firstName, String lastName){
        nameProperty = new SimpleStringProperty(firstName + " " + lastName);
    }

    public StringProperty getRelationshipProperty(){
        return relationshipProperty;
    }

    public void setRelationshipProperty(String relationship){
        relationshipProperty = new SimpleStringProperty(relationship);
    }

    public AbsRelation toAbsRelation(){
        return relation;
    }
    }
