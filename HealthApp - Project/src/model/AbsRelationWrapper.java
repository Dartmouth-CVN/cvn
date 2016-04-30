package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class AbsRelationWrapper extends AbsUserWrapper {

    StringProperty relationshipProperty;
    StringProperty nameProperty;
    BooleanProperty isFamilyProperty;
    BooleanProperty isCaregiverProperty;

    AbsRelation relation;

    public AbsRelationWrapper(AbsRelation relation) {
        this.relation = relation;
        setRelationshipProperty(relation.getRelationship());
        setNameProperty(relation.getFirstName(), relation.getLastName());
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public void setNameProperty(String firstName, String lastName) {
        nameProperty = new SimpleStringProperty(firstName + " " + lastName);
    }

    public StringProperty getRelationshipProperty() {
        return relationshipProperty;
    }

    public void setRelationshipProperty(String relationship) {
        relationshipProperty = new SimpleStringProperty(relationship);
    }

    public BooleanProperty getIsFamilyProperty() {
        return isFamilyProperty;
    }

    public Boolean getIsFamilyBoolean(){
        return isFamilyProperty.getValue();
    }

    public void setIsFamilyProperty(boolean isFamily) {
        isFamilyProperty = new SimpleBooleanProperty(isFamily);
    }


    public BooleanProperty getIsCaregiverProperty() {
        return isCaregiverProperty;
    }

    public Boolean getIsCaregiverBoolean(){
        return isCaregiverProperty.getValue();
    }

    public void setIsCaregiverProperty(boolean isCaregiver) {
        isCaregiverProperty = new SimpleBooleanProperty(isCaregiver);
    }

    public AbsRelation toAbsRelation() {
        return relation;
    }

}
