package model;

import java.util.Date;

public class Caregiver extends AbsRelation {
	boolean isFamily;

	public Caregiver(){
		
	}
	public Caregiver(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
					 Date birthday, java.lang.String room, String picture, Contact contactInfo, java.lang.String relation, boolean isFamily) {
		super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, relation);
		setIsFamily(isFamily);
	}

	public boolean getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(boolean isFamily) {
		this.isFamily = isFamily;
	}
}
