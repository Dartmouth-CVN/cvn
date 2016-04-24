package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

public class Administrator extends AbsUser {

	public Administrator(){
		
	}
	
	public Administrator(String userId, String firstName, String lastName, String username, String password,
						 Date birthday, String room, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
	}

}
