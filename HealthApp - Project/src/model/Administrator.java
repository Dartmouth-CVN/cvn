package model;

import java.util.Date;

public class Administrator extends AbsUser {

	public Administrator(){
		
	}
	
	public Administrator(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
						 Date birthday, java.lang.String room, String picture, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo);
	}

}
