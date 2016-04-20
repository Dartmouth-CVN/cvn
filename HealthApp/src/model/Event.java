package model;


import java.time.LocalDateTime;
import java.util.LinkedList;

public class Event {
	
	int eventId;
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
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
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
