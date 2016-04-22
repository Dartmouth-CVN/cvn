package model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Event {

	int eventId;
	 LocalDateTime date;
	 String location;
	 List<AbsUser> attendees;
	 String notes;

	public Event(LocalDateTime date, String location, List<AbsUser> attendees, String notes) {
		this.date = date;
		this.location = location;
		this.attendees = attendees;
		this.notes = notes;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setAttendees(List<AbsUser> attendees) {
		this.attendees = attendees;
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

	public List<AbsUser> getAttendees() {
		return this.attendees;
	}

	public String getNotes() {
		return this.notes;
	}
}
