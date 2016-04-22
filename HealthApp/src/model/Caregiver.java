package model;

import java.time.LocalDate;
import java.util.Date;

public class Caregiver extends AbsUser {
	String relation;
	boolean isFamily;
	int caregiverId;

	public Caregiver(){
		
	}
	public Caregiver(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
	}

	public Caregiver(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo, String relation, boolean isFamily,
			int caregiverId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
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

	public boolean getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(boolean isFamily) {
		this.isFamily = isFamily;
	}

	public int getCaregiverId() {
		return caregiverId;
	}

	public void setCaregiverId(int caregiverId) {
		this.caregiverId = caregiverId;
	}
}
