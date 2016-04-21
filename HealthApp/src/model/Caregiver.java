package model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class Caregiver extends AbsUser {
	private String relation;
	private boolean isFamily;
	private int caregiverId;
	
	public Caregiver(int userId, String firstName, String lastName, String username, String password,
			LocalDate birthday, String room, Contact contactInfo, Schedule schedule) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
	}
	
	public Caregiver(int userId, String firstName, String lastName, String username, String password,
			LocalDate birthday, String room, Contact contactInfo, Schedule schedule, String relation,
			boolean isFamily, int caregiverId ) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.relation = relation;
		this.isFamily = isFamily;
		this.caregiverId = caregiverId;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public boolean isFamily() {
		return isFamily;
	}

	public void setFamily(boolean isFamily) {
		this.isFamily = isFamily;
	}

	public int getCaregiverId() {
		return caregiverId;
	}

	public void setCaregiverId(int caregiverId) {
		this.caregiverId = caregiverId;
	}

	public StringProperty getNameProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringProperty getBirthdayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringProperty getRelationProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public BooleanProperty getInFamilyProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCaregiverID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
