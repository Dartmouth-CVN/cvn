package model;

public abstract class User {
	private String firstName;
	private String lastName;
	private String userID;
	private String username;
	private Contact contactInfo;
	private Schedule schedule;
	private Location room;
	private String role;
	//role is either "Patient", "MedicalStaff", "Administrator"
	public User(String firstName, String lastName, String id, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactInfo = new Contact(id);
		//this.schedule = new Schedule(id);
		this.room = null;
		this.userID =id;
		this.setRole(role);
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public Contact getContactInfo() {
		return this.contactInfo;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setUseriID(String userid) {
		this.userID = userid;
	}
	
	public Location getRoom() {
		return this.room;
	}
	
	public void setRoom(Location room) {
		this.room = room;
	}
	
	public Schedule getSchedule() {
		return this.schedule;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
