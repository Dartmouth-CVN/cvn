package model;

import java.time.LocalDate;
import java.util.Date;

public class Administrator extends AbsUser {
	int adminId;
	
	public Administrator(){
		
	}
	
	public Administrator(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
	}
	
	public Administrator(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo, int adminId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.adminId = adminId;
	}

	public int getAdminID() {
		return adminId;
	}

	public void setAdminID(int adminId) {
		this.adminId = adminId;
	}
}
