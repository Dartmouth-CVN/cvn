package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

public class Caregiver extends AbsRelation {
	boolean isFamily;

	BooleanProperty isFamilyProperty;

	public Caregiver(){
		
	}
	public Caregiver(String userId, String firstName, String lastName, String username, String password,
					 Date birthday, String room, Contact contactInfo, String relation, boolean isFamily) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, relation);
		setIsFamily(isFamily);
	}

	public boolean getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(boolean isFamily) {
		this.isFamily = isFamily;
		setIsFamilyProperty(isFamily);
	}

	public void setIsFamilyProperty(boolean isFamily){
		isFamilyProperty = new SimpleBooleanProperty(isFamily);
	}

	public BooleanProperty getIsFamilyProperty() {
		return isFamilyProperty;
	}

}
