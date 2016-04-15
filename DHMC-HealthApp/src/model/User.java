package model;

import java.time.LocalDate;

public abstract class User implements IDisplayable{
	private String firstName;
	private String lastName;
	private String userID;
	private Contact contactInfo;
	private LocalDate birthday;
	// private Schedule schedule;
	// private Location room;
	//role is either "Patient", "MedicalStaff", "Administrator"
	public User(String firstName, String lastName, String id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactInfo = new Contact(id);
		this.setBirthday(LocalDate.now());
		//this.schedule = new Schedule(id);
		// this.room = null;
		this.userID =id;
	}
	
	public User(String firstName, String lastName, String id, Contact contactInfo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactInfo = contactInfo;
		this.setBirthday(LocalDate.now());
		//this.schedule = new Schedule(id);
		// this.room = null;
		this.userID =id;
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
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public void setUseriID(String userid) {
		this.userID = userid;
	}
	
//	public Location getRoom() {
//		return this.room;
//	}
//	
//	public void setRoom(Location room) {
//		this.room = room;
//	}
	
//	public Schedule getSchedule() {
//		return this.schedule;
//	}
}
