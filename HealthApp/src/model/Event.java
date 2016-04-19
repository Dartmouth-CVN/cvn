package model;


import java.time.LocalDateTime;
import java.util.LinkedList;

public class Event {
	
	int eventID;
	private LocalDateTime date;
	private String location;
	private LinkedList<AbsUser> attendees;
	private String notes;
	
	public Event (LocalDateTime date, String location, LinkedList<AbsUser> attendees, String notes) {
		this.date = date;
		this.location = location;
		this.attendees = attendees;
		this.notes = notes;
	}
	
	
	//The setters
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void addAttendees(AbsUser user) {
		if(!this.attendees.contains(user)) {
			this.attendees.add(user);
			user.addEvent(this);
		}
	}
	
	public void removeAttendees(AbsUser user) {
		if(this.attendees.contains(user)) {
			this.attendees.remove(user);
			user.removeEvent(this);
		}
		
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	//The getters
	public String getDate() {
		return this.date.toString();
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public LinkedList<AbsUser> getAttendees() {
		return this.attendees;
	}
	
	public String getNotes() {
		return this.notes;
	}
}
