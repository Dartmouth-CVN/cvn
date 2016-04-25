package model;

import java.util.Date;

public class Caregiver extends AbsRelation {
	boolean isFamily;

	public Caregiver(){
		
	}
	public Caregiver(String userId, String firstName, String lastName, String username, String password,
					 Date birthday, String room, String picture, Contact contactInfo, String relation, boolean isFamily) {
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
