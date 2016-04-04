package model;

abstract class User {
	private String firstName;
	private String lastName;
	private String userid;
	private String username;
	private String password;
	private Contact contactInfo;
	// private Location room;
	// private Schedule schedule;
	
	public User(String firstName, String lastName, String userid, String username, String password, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.contactInfo = new Contact(id);
		// this.schedule = new Schedule();
		// TODO: Determine value of room when patient is not in the hospital
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
	
	public String getPassword() {
		return this.password;
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
	
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	public Location getRoom() {
		return this.room;
	}
	
	public void setRoom(Location room) {
		this.room = room;
	}
	
	public Schedule getSchedule() {
		return this.schedule;
	}
	*/
	public void login() {
		// Database access
	}
	
	public void update() {
		// Database access
	}

}
