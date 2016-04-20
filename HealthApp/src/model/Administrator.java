package model;

public class Administrator extends AbsUser {
	private int adminId;
	
	public Administrator(int userId, String firstName, String lastName, String username, String password,
			String birthday, String room, Contact contactInfo, Schedule schedule) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
	}
	
	public Administrator(int userId, String firstName, String lastName, String username, String password,
			String birthday, String room, Contact contactInfo, Schedule schedule, int adminId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.adminId = adminId;
	}

	public int getAdminID() {
		return adminId;
	}

	public void setAdminID(int adminId) {
		this.adminId = adminId;
	}
}
