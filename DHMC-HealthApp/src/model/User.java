package model;

public abstract class User {
	private String firstName;
	private String lastName;
	private String userid;
	private String username;
	private Contact contactInfo;
	private Schedule schedule;
	private Location room;
	
	public User(String firstName, String lastName, String userid, String username, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userid = userid;
		this.username = username;
		this.contactInfo = new Contact(id);
		this.schedule = new Schedule(id);
		this.room = null;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getUserid() {
		return this.userid;
	}
	
	public String getUsername() {
		return this.username;
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
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public void setUsername(String username) {
		this.username = username;
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

}
